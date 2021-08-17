package com.example.application.views.details;


import com.example.application.cache.Cache;
import com.example.application.models.LoadedItem;
import com.example.application.service.FavoritesService;
import com.example.application.views.MainLayout;
import com.example.application.views.shared.SharedViews;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

@Route(value = "detail-view", layout = MainLayout.class)
@PageTitle("Movie Detail")
@CssImport("./views/detail.css")
public class DetailView extends Div {

    private Button previewAction = new Button();
    private Button favoriteAction = new Button();
    private Button goBack = new Button();
    private Button addTag = new Button();
    private TextField tags = new TextField("Tags");
    private FavoritesService favoritesService;

    private Notification noticeAdd = new Notification("Favorite ADDED", 1000, Notification.Position.BOTTOM_CENTER);
    private Notification noticeDeleted = new Notification("Favorite DELETED", 1000,
            Notification.Position.BOTTOM_CENTER);
    private Notification noticeUpdate = new Notification("Tags UPDATED", 1000, Notification.Position.BOTTOM_CENTER);



    public DetailView(FavoritesService favoritesService) {

        this.favoritesService = favoritesService;

        addClassName("detail-wrap");
        add(SharedViews.getDetail(Cache.getInstance().getDetailItem(), Cache.getInstance().isFavMode()));
        add(createButtonLayout(),createFormLayout());

        previewAction.setText("Watch the trailer");
        previewAction.setClassName("green-button");
        previewAction.addClickListener(e -> previewAction.getUI().ifPresent(ui -> {
                        openPreview(Cache.getInstance().getDetailItem());
                }
        ));

        addTag.setText("Edit tag");
        addTag.setClassName("green-button");
        addTag.getStyle().set("color","white");
        addTag.addClickListener(e -> addTag.getUI().ifPresent(ui -> {
            if (tags.isEmpty()) {
                openWarning("Fields cannot be blank");
            } else {
                editTags(Cache.getInstance().getDetailItem(), tags.getValue());
            }
        }));


        goBack.setText("Return to Movie Search");
        goBack.addClickListener(
                e -> goBack.getUI().ifPresent(ui -> { ui.navigate("movie-search"); }
                ));

        favoriteAction.setText(Cache.getInstance().isFavMode() ? "DELETE Favorite" : "ADD to Favorites");
        favoriteAction.setClassName(Cache.getInstance().isFavMode() ? "red-button" : "green-button");
        favoriteAction.getStyle().set("color","white");
        favoriteAction.addClickListener(e -> favoriteAction.getUI().ifPresent(ui -> {
                    if (Cache.getInstance().isFavMode())
                        deleteFavorite(Cache.getInstance().getDetailItem().getId());
                    else
                        addFavorite(Cache.getInstance().getDetailItem());
                }
        ));
    }

    public void openPreview(LoadedItem item) {
        if (item.getPreview() != null)
            getUI().get().getPage().open(item.getPreview());
    }

    public void openWarning(String errorMsg) {
        Notification notification = new Notification(
                errorMsg, 3000, Notification.Position.BOTTOM_CENTER);
        notification.open();
    }


    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        previewAction.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(favoriteAction);
        buttonLayout.add(previewAction);
        buttonLayout.add(goBack);
        if (Cache.getInstance().isFavMode()) {
            buttonLayout.add(addTag);
        }
        return buttonLayout;
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep(StringUtils.EMPTY, 1));
        if (Cache.getInstance().isFavMode()) {
            formLayout.add(tags);
        }
        return formLayout;
    }

    public void addFavorite(LoadedItem favorite) {
        favoritesService.addFavorite(UI.getCurrent(), favoriteAdd -> {
            getUI().get().access(() -> {
                noticeAdd.open();
                getUI().get().navigate("favorites");
            });
        }, favorite);

    }

    public void deleteFavorite(String id) {
        favoritesService.deleteFavoriteById(UI.getCurrent(), favoriteDelete -> {
            getUI().get().access(() -> {
                noticeDeleted.open();
                getUI().get().navigate("favorites");
            });
        }, id);

    }

    public void editTags(LoadedItem favorite, String newTags) {
        favorite.setUserEmail(newTags);
        favoritesService.editTags(UI.getCurrent(), favoriteEdit -> {
            getUI().get().access(() -> {
                noticeUpdate.open();
                getUI().get().navigate("favorites");
            });
        }, favorite);
    }

}

