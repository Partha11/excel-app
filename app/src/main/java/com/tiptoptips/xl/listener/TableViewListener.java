/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.tiptoptips.xl.listener;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;

public class TableViewListener implements ITableViewListener {

    @NonNull
    private Context context;
    @NonNull
    private TableView tableView;

    public TableViewListener(@NonNull TableView tableView) {

        this.context = tableView.getContext();
        this.tableView = tableView;
    }

    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

        showToast("Cell " + column + " " + row + " has been clicked.");
    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, final int column, int row) {

        showToast("Cell " + column + " " + row + " has been long pressed.");
    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

        showToast("Column header  " + column + " has been clicked.");
    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

//        if (columnHeaderView instanceof ColumnHeaderViewHolder) {
//
//            ColumnHeaderLongPressPopup popup = new ColumnHeaderLongPressPopup((ColumnHeaderViewHolder) columnHeaderView, tableView);
//            popup.show();
//        }
    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

        showToast("Row header " + row + " has been clicked.");
    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

//        RowHeaderLongPressPopup popup = new RowHeaderLongPressPopup(rowHeaderView, tableView);
//        popup.show();
    }


    private void showToast(String message) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
