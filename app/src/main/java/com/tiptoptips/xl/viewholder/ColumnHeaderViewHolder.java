package com.tiptoptips.xl.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evrencoskun.tableview.ITableView;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder;
import com.evrencoskun.tableview.sort.SortState;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.model.ColumnHeader;

import java.util.Objects;

public class ColumnHeaderViewHolder extends AbstractSorterViewHolder {

    private static final String LOG_TAG = ColumnHeaderViewHolder.class.getSimpleName();

    @NonNull
    private final LinearLayout column_header_container;
    @NonNull
    private final TextView column_header_textview;
    @NonNull
    private final ImageButton column_header_sortButton;
    @Nullable
    private final ITableView tableView;

    public ColumnHeaderViewHolder(@NonNull View itemView, @Nullable ITableView tableView) {

        super(itemView);
        this.tableView = tableView;

        column_header_textview = itemView.findViewById(R.id.column_header_textView);
        column_header_container = itemView.findViewById(R.id.column_header_container);
        column_header_sortButton = itemView.findViewById(R.id.column_header_sortButton);

        View.OnClickListener mSortButtonClickListener = view -> {

            if (tableView != null) {

                if (getSortState() == SortState.ASCENDING) {

                    tableView.sortColumn(getAdapterPosition(), SortState.DESCENDING);

                } else if (getSortState() == SortState.DESCENDING) {

                    tableView.sortColumn(getAdapterPosition(), SortState.ASCENDING);

                } else {

                    tableView.sortColumn(getAdapterPosition(), SortState.DESCENDING);
                }
            }

        };

        column_header_sortButton.setOnClickListener(mSortButtonClickListener);
    }

    public void setColumnHeader(@Nullable ColumnHeader columnHeader) {

        column_header_textview.setText(String.valueOf(Objects.requireNonNull(columnHeader).getData()));

//        column_header_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        column_header_textview.requestLayout();
    }

    @Override
    public void onSortingStatusChanged(@NonNull SortState sortState) {

        Log.e(LOG_TAG, " + onSortingStatusChanged : x:  " + getAdapterPosition() + " old state "
                + getSortState() + " current state : " + sortState + " visiblity: " +
                column_header_sortButton.getVisibility());

        super.onSortingStatusChanged(sortState);

        // It is necessary to remeasure itself.
        column_header_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;

        controlSortState(sortState);

        Log.e(LOG_TAG, " - onSortingStatusChanged : x:  " + getAdapterPosition() + " old state "
                + getSortState() + " current state : " + sortState + " visibility: " +
                column_header_sortButton.getVisibility());

        column_header_textview.requestLayout();
        column_header_sortButton.requestLayout();
        column_header_container.requestLayout();
        itemView.requestLayout();
    }

    private void controlSortState(@NonNull SortState sortState) {

        if (sortState == SortState.ASCENDING) {

            column_header_sortButton.setVisibility(View.VISIBLE);
            column_header_sortButton.setImageResource(R.drawable.ic_down);

        } else if (sortState == SortState.DESCENDING) {

            column_header_sortButton.setVisibility(View.VISIBLE);
            column_header_sortButton.setImageResource(R.drawable.ic_up);

        } else {

            column_header_sortButton.setVisibility(View.INVISIBLE);
        }
    }
}
