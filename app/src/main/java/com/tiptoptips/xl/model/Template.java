package com.tiptoptips.xl.model;

import java.util.HashMap;
import java.util.List;

public class Template {

    private String templateName;
    private int templateThumb;
    private List<String> columnNames;
    private List<Integer> columnTypes;
    private List<HashMap<String, String>> cells;

    public Template() {
    }

    public Template(String templateName, int templateThumb) {

        this.templateName = templateName;
        this.templateThumb = templateThumb;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public int getTemplateThumb() {
        return templateThumb;
    }

    public void setTemplateThumb(int templateThumb) {
        this.templateThumb = templateThumb;
    }

    public List<Integer> getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(List<Integer> columnTypes) {
        this.columnTypes = columnTypes;
    }

    public List<HashMap<String, String>> getCells() {
        return cells;
    }

    public void setCells(List<HashMap<String, String>> cells) {
        this.cells = cells;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
}
