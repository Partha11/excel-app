package com.tiptoptips.xl.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
import com.tiptoptips.xl.viewholder.ColumnHeaderViewHolder;
import com.tiptoptips.xl.viewholder.GenderCellViewHolder;
import com.tiptoptips.xl.viewholder.ImageCellViewHolder;
import com.tiptoptips.xl.viewholder.RowHeaderViewHolder;
import com.tiptoptips.xl.viewmodel.TableViewModel;

import static com.tiptoptips.xl.utility.Constants.GENDER_CELL_TYPE;
import static com.tiptoptips.xl.utility.Constants.MOOD_CELL_TYPE;

public class TableAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    private Context context;
    private TableViewModel tableViewModel;

    public TableAdapter(Context context) {

        super(context);
        this.context = context;
    }

    public void setTableModel(TableViewModel tableViewModel) {

        this.tableViewModel = tableViewModel;
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

        if (position == Constants.IMAGE_COLUMN) {

            return Constants.IMAGE_COLUMN;
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
        }

        view = inflater.inflate(R.layout.table_view_cell_layout, parent, false);
        return new CellViewHolder(view);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {

        Cell cell = (Cell) cellItemModel;

        if (holder.getItemViewType() == Constants.IMAGE_COLUMN) {

            ImageCellViewHolder moodViewHolder = (ImageCellViewHolder) holder;
            moodViewHolder.cell_image.setImageResource(tableViewModel.getDrawable((int) cell.getData(), false));

        } else {

            CellViewHolder viewHolder = (CellViewHolder) holder;
            viewHolder.setCell(cell);
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

        rowHeaderViewHolder.row_header_textview.setText(String.valueOf(rowHeader.getData()));
    }

    @Override
    public View onCreateCornerView() {

        @SuppressLint("InflateParams")
        View corner = LayoutInflater.from(context).inflate(R.layout.table_view_corner_layout, null, false);
        return corner;
    }
}
