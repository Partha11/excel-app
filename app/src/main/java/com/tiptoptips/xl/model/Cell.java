package com.tiptoptips.xl.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evrencoskun.tableview.filter.IFilterableModel;
import com.evrencoskun.tableview.sort.ISortableModel;

public class Cell implements ISortableModel, IFilterableModel {

    @NonNull
    private String id;
    @Nullable
    private Object data;
    @NonNull
    private String filterKeyword;

    public Cell(@NonNull String id, @Nullable Object data) {

        this.id = id;
        this.data = data;
        this.filterKeyword = String.valueOf(data);
    }

    @NonNull
    @Override
    public String getId() {
        return id;
    }

    @Nullable
    @Override
    public Object getContent() {
        return data;
    }

    @Nullable
    public Object getData() {
        return data;
    }

    public void setData(@Nullable String data) {
        this.data = data;
    }

    @NonNull
    @Override
    public String getFilterableKeyword() {
        return filterKeyword;
    }
}
