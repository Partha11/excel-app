package com.tiptoptips.xl.viewholder;

import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.model.Cell;
import com.tiptoptips.xl.model.CheckBoxListItem;
import com.tiptoptips.xl.utility.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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

            if (textView != null) {

                if (cell.getData() != null) {

                    data = cell.getData().toString();
                    textView.setText(Html.fromHtml(data));

                } else {

                    textView.setText(data);
                }
            }

        } else if (viewType == Constants.LIST_COLUMN) {

            String data = Objects.requireNonNull(Objects.requireNonNull(cell).getData()).toString();

            if (!data.isEmpty()) {

                Type type = new TypeToken<List<CheckBoxListItem>>() {
                }.getType();
                List<CheckBoxListItem> items = new ArrayList<>(new Gson().fromJson(data, type));
                StringBuilder sb = new StringBuilder();

                sb.append("<ul>");

                for (CheckBoxListItem item : items) {

                    if (item.isChecked()) {

                        sb.append("<li>");
                        sb.append(item.getName());
                        sb.append("</li>");
                    }
                }

                sb.append("</ul>");

                textView.setText(Html.fromHtml(sb.toString()));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11.5f);
                textView.setGravity(Gravity.CENTER);
            }
        }

//        container.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        textView.requestLayout();
    }
}
