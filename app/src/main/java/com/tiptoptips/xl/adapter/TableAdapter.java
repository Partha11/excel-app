package com.tiptoptips.xl.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.model.Cell;
import com.tiptoptips.xl.model.ColumnHeader;
import com.tiptoptips.xl.model.RowHeader;
import com.tiptoptips.xl.utility.Constants;
import com.tiptoptips.xl.viewholder.CellViewHolder;
import com.tiptoptips.xl.viewholder.CheckboxViewHolder;
import com.tiptoptips.xl.viewholder.ColumnHeaderViewHolder;
import com.tiptoptips.xl.viewholder.ImageCellViewHolder;
import com.tiptoptips.xl.viewholder.RowHeaderViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TableAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    private Context context;
    private List<Integer> columnTypeList;

    public TableAdapter(Context context) {

        super(context);
        this.context = context;
    }

    public void setColumnType(List<Integer> columnTypeList) {

        if (this.columnTypeList != null) {

            this.columnTypeList.clear();
        }

        this.columnTypeList = new ArrayList<>(columnTypeList);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {

        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {

        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {

        if (columnTypeList.get(position) == Constants.IMAGE_COLUMN) {

            return Constants.IMAGE_COLUMN;

        } else if (columnTypeList.get(position) == Constants.CHECKBOX_COLUMN) {

            return Constants.CHECKBOX_COLUMN;
        }

        return Constants.TEXT_COLUMN;
    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == Constants.IMAGE_COLUMN) {

            view = inflater.inflate(R.layout.table_view_image_cell_layout, parent, false);
            return new ImageCellViewHolder(view);

        } else if (viewType == Constants.CHECKBOX_COLUMN) {

            view = inflater.inflate(R.layout.table_view_checkbox_cell_layout, parent, false);
            return new CheckboxViewHolder(view);
        }

        view = inflater.inflate(R.layout.table_view_cell_layout, parent, false);
        return new CellViewHolder(view);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {

        Cell cell = (Cell) cellItemModel;

        if (holder.getItemViewType() == Constants.IMAGE_COLUMN) {

            ImageCellViewHolder viewHolder = (ImageCellViewHolder) holder;
            viewHolder.setData(cell.getData());
//            moodViewHolder.cell_image.setImageResource(tableViewModel.getDrawable((int) cell.getData(), false));

        } else if (holder.getItemViewType() == Constants.CHECKBOX_COLUMN) {

            CheckboxViewHolder viewHolder = (CheckboxViewHolder) holder;
            viewHolder.setData(cell);

        } else {

            CellViewHolder viewHolder = (CellViewHolder) holder;
            viewHolder.setCell(cell, holder.getItemViewType());
        }
    }

    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_view_column_header_layout, parent, false);
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int columnPosition) {

        ColumnHeader columnHeader = (ColumnHeader) columnHeaderItemModel;

        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeader(columnHeader);
    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_view_row_header_layout, parent, false);
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int rowPosition) {

        RowHeader rowHeader = (RowHeader) rowHeaderItemModel;
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;

        rowHeaderViewHolder.row_header_textview.setText(Objects.requireNonNull(rowHeader.getData()).toString());
    }

    @Override
    public View onCreateCornerView() {

        @SuppressLint("InflateParams")
        View corner = LayoutInflater.from(context).inflate(R.layout.table_view_corner_layout, null, false);
        return corner;
    }
}
