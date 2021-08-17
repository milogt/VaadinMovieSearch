package com.example.application.cache;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import com.example.application.models.LoadedItem;

public class Cache {

    private String keyword = "";
    private List<LoadedItem> items = new ArrayList<>();
    private LoadedItem detailItem;
    private int offset;
    private boolean favMode;
    private String email = "gtian@gmail.com";
    private static Cache cache;

    private Cache() {
    }

    public static Cache getInstance() {
        if (null == cache) {
            cache = new Cache();
            return cache;
        } else {
            return cache;
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;

    }

    public Stream<LoadedItem> streamItems() {
        return items.stream();
    }

    public void addItems(List<LoadedItem> items) {
        this.items.addAll(items);
    }

    public void clearItems() {
        this.items.clear();
    }

    public int itemsSize() {
        return items.size();
    }

    public LoadedItem getDetailItem() {
        return detailItem;
    }

    public void setDetailItem(LoadedItem detailItem) {
        this.detailItem = detailItem;
    }

    public boolean isFavMode() {
        return favMode;
    }

    public void setFavMode(boolean favMode) {
        this.favMode = favMode;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
