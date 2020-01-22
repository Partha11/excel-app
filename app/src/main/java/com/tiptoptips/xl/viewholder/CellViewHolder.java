package com.tiptoptips.xl.viewholder;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.model.Cell;
import com.tiptoptips.xl.utility.Constants;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CellViewHolder extends AbstractViewHolder {

    @BindView(R.id.cell_data)
    AppCompatTextView textView;
    @BindView(R.id.cell_container)
    LinearLayout container;

    public CellViewHolder(@NonNull View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setCell(@NonNull Cell cell, int viewType) {

        if (viewType == Constants.TEXT_COLUMN) {

            String data = "";

            if (cell.getData() != null) {

                data = cell.getData().toString();
            }

            textView.setText(Html.fromHtml(data));

        } else if (viewType == Constants.LIST_COLUMN) {

            String data = Objects.requireNonNull(Objects.requireNonNull(cell).getData()).toString();
            String[] items = data.split("\n");
            StringBuilder builder = new StringBuilder();

            builder.append("<ul>");

            for (String item : items) {

                builder.append("<li>");
                builder.append(item);
                builder.append("</li>");
            }

            builder.append("</ul>");
            textView.setText(Html.fromHtml(builder.toString()));
        }

//        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        cell_textview.requestLayout();
    }
}
