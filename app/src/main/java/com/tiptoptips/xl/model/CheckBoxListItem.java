package com.tiptoptips.xl.model;

public class CheckBoxListItem {

    private long id;
    private String name;
    private boolean isChecked;

    public CheckBoxListItem(long id, String name, boolean isChecked) {

        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
