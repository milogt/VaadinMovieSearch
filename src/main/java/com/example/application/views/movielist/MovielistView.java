package com.example.application.views.movielist;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.example.application.views.MainLayout;
import com.example.application.service.MovieService;
import com.example.application.cache.Cache;
import com.example.application.models.LoadedItem;
import com.example.application.views.shared.SharedViews;
import java.util.stream.Collectors;

@PageTitle("Movie list")
@Route(value = "movie-search", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@CssImport("./views/generic-list.css")
@PreserveOnRefresh
public class MovielistView extends Div implements AfterNavigationObserver {

    public static int MAX_RESULTS = 20;
    private MovieService movieService;
    private Grid<LoadedItem> grid = new Grid<>();
    private boolean isLoading = false;
    private TextField keyWord;
    private Notification loading = new Notification("Loading...", 1000, Notification.Position.BOTTOM_CENTER);


    public MovielistView(MovieService movieService) {
        this.movieService = movieService;

        keyWord = new TextField();
        keyWord.setLabel("Search Term");
        keyWord.setPlaceholder("type search-term, then press [ENTER]");
        keyWord.setAutofocus(true);
        keyWord.addKeyDownListener(keyDownEvent -> {
                    String keyStroke = keyDownEvent.getKey().getKeys().toString();
                    if (keyStroke.equals("[Enter]") && !isLoading && !keyWord.getValue().equals("")) {
                        Cache.getInstance().setKeyword(keyWord.getValue());
                        executeSearch(Cache.getInstance().getKeyword());
                    }
                }
        );

        addClassName("generic-list");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(favoriteItem -> SharedViews.getCard(favoriteItem, false));
        grid.addItemClickListener(
                event -> grid.getUI().ifPresent(ui -> {

                            Cache.getInstance().setDetailItem(event.getItem());
                            Cache.getInstance().setFavMode(false);
                            ui.navigate("detail-view");

                        }
                ));

        add(keyWord, withClientsideScrollListener(grid));
    }

    public void executeSearch(String searchFor) {

        Cache.getInstance().setKeyword(searchFor);
        Cache.getInstance().setOffset(0);
        Cache.getInstance().clearItems();
        getPlaces(searchFor);
    }

    private void getPlaces(String searchTerm) {

        if (null == searchTerm || searchTerm.equals("")) return;

        isLoading = true;
        movieService.getMoviesPaged(volResp -> {
            getUI().get().access(() -> {

                Cache.getInstance().addItems(volResp.getResults()
                        .stream()
                        .map( item -> LoadedItem.fromResult(item, Cache.getInstance().getEmail()))
                        .collect(Collectors.toList())
                );
                grid.setItems(Cache.getInstance().streamItems());
                Cache.getInstance().setOffset(Cache.getInstance().getOffset() + MAX_RESULTS);
                isLoading = false;
                getUI().get().push();

            });
        }, searchTerm, MAX_RESULTS, Cache.getInstance().getOffset());
    }

    private Grid<LoadedItem> withClientsideScrollListener(Grid<LoadedItem> grid) {
        grid.getElement().executeJs(
                "this.$.scroller.addEventListener('scroll', (scrollEvent) => " +
                        "{requestAnimationFrame(" +
                        "() => {if(this.$.table.scrollTop / (this.$.table.scrollHeight - this.$.table.clientHeight) " +
                        ">= 0.95){"+
                        " $0.$server.onGridEnd()}}" +
                        ")},true)",
                getElement());
        return grid;
    }

    @ClientCallable
    public void onGridEnd() {

        if (!isLoading) {
            System.out.println("Paging...");
            loading.open();
            getPlaces(Cache.getInstance().getKeyword());
        }

    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        // Set some data when this view is displayed.
        if (Cache.getInstance().itemsSize() > 0)
            grid.setItems(Cache.getInstance().streamItems());

        keyWord.setValue(Cache.getInstance().getKeyword());
    }




}
