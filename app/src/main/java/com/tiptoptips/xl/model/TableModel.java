package com.tiptoptips.xl.model;

import java.util.List;

public class TableModel {

    private List<List<Cell>> cellList;
    private List<RowHeader> rowList;
    private List<ColumnHeader> columnList;
    private List<Integer> columnTypeList;

    public List<List<Cell>> getCellList() {
        return cellList;
    }

    public void setCellList(List<List<Cell>> cellList) {
        this.cellList = cellList;
    }

    public List<RowHeader> getRowList() {
        return rowList;
    }

    public void setRowList(List<RowHeader> rowList) {
        this.rowList = rowList;
    }

    public List<ColumnHeader> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnHeader> columnList) {
        this.columnList = columnList;
    }

    public List<Integer> getColumnTypeList() {
        return columnTypeList;
    }

    public void setColumnTypeList(List<Integer> columnTypeList) {
        this.columnTypeList = columnTypeList;
    }
}
