package com.example.application.views.shared;

import com.example.application.models.LoadedItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./views/shared-views.css")
public class SharedViews {

    public static VerticalLayout getDetail(LoadedItem favorite, boolean favMode) {
        VerticalLayout detail = new VerticalLayout();
        detail.setSpacing(false);
        detail.setPadding(false);

        detail.addClassName("detail");
        Div tab = new Div();
        tab.addClassName("tab");

        Image image = new Image();

        image.setSrc(null == favorite.getLink() ? "https://picsum.photos/200/300" : favorite.getLink());
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addClassName("vertical-layout");


        Span title = getProperSpan(favorite.getTitle());
        title.addClassNames("text", "title");
        Span author = getProperSpan(favorite.getAuthorName());
        author.addClassName("text");
        Span desc = getProperSpan(favorite.getDescription());
        desc.addClassName("text");
        Span email = getProperSpan(favorite.getUserEmail());
        email.addClassName("text");
        Span year = getProperSpan(favorite.getYear());
        year.addClassName("text");

        if (favMode) {
            detail.addClassName("fav-mode");
            verticalLayout.add(title, author, year, email, desc);
        } else {
            verticalLayout.add(title, author, year, desc);
        }
        detail.add(tab, image, verticalLayout);

        return detail;
    }


    public static HorizontalLayout getCard(LoadedItem favorite, boolean favMode) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.setPadding(false);

        Div tab = new Div();
        tab.addClassName("tab");
        Image image = new Image();

        image.setSrc(null == favorite.getLink() ? "https://picsum.photos/200/300" : favorite.getLink());
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addClassName("vertical-layout");
        verticalLayout.setSpacing(false);
        verticalLayout.setPadding(false);

        Span title = getProperSpan(favorite.getTitle());
        title.addClassNames("text", "title");
        Span author = getProperSpan(favorite.getAuthorName());
        author.addClassName("text");
        Span desc = getProperSpan(favorite.getDescription());
        desc.addClassName("text");
        Span email = getProperSpan(favorite.getUserEmail());
        email.addClassName("text");
        Span year = getProperSpan(favorite.getYear());
        year.addClassName("text");


        if (favMode) {
            verticalLayout.add(title, author, year, email);
            card.add(tab, image, verticalLayout);
        } else {
            verticalLayout.add(title, author, year);
            card.add(image, verticalLayout);
        }

        return card;
    }

    private static Span getProperSpan(String str){
        return new Span(null == str ? "" : str);
    }


}
