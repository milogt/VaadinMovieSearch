package com.example.application.models;


import java.io.Serializable;
import java.util.List;

public class LoadedItem implements Serializable {

    private String id;
    private String userEmail;
    private String title;
    private String year;
    private String description;
    private String authorName;
    private String link;
    private String preview;

    //factory method
    public static LoadedItem fromResult(Result result, String  userEmail){
        LoadedItem loadedItem = new LoadedItem();
        loadedItem.setUserEmail(userEmail);

        loadedItem.setAuthorName(result.getArtistName());

        loadedItem.setDescription(null == result.getLongDescription() ? "": result.getLongDescription());
        loadedItem.setId(null == result.getTrackId() ? "": result.getTrackId().toString());
        if( null != result.getArtworkUrl100() && null != result.getArtworkUrl100()){
            loadedItem.setLink(result.getArtworkUrl100());
        } else {
            loadedItem.setLink("https://picsum.photos/100/300");
        }

        loadedItem.setTitle(null == result.getTrackName() ? "" : result.getTrackName());
        loadedItem.setYear(null == result.getPrimaryGenreName() ? "" : result.getPrimaryGenreName());
        loadedItem.setPreview(null == result.getPreviewUrl() ? "" : result.getPreviewUrl());
        return loadedItem;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}

