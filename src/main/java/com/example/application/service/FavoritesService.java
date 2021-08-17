package com.example.application.service;


import com.example.application.models.LoadedItem;
import com.example.application.repository.FavoritesRepository;
import com.vaadin.flow.component.UI;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Service
public class FavoritesService extends ResponseEntityExceptionHandler {
    private FavoritesRepository favoritesRepository;



    public FavoritesService(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }


    public void getFavoritesPaged(ResponseCallback<List<LoadedItem>> callback,
                                  int page) {
        favoritesRepository.getFavoritesPaged(callback, page);
    }



    public void addFavorite(UI ui, ResponseCallback<LoadedItem> callback,
                            LoadedItem favorite)  {
        favoritesRepository.addFavorite(ui, callback, favorite);
    }

    public void deleteFavoriteById(UI ui, ResponseCallback<LoadedItem> callback,
                                   String id) {
        favoritesRepository.deleteFavoriteById(ui, callback, id);
    }

    public void editTags(UI ui, ResponseCallback<LoadedItem> callback,
                            LoadedItem favorite)  {
        favoritesRepository.editTags(ui, callback, favorite);
    }


}

