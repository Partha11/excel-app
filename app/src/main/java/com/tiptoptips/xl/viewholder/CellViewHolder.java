package com.tiptoptips.xl.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.model.Cell;

import java.util.Objects;

public class CellViewHolder extends AbstractViewHolder {
    @NonNull
    private final TextView textView;
    @NonNull
    private final LinearLayout container;

    public CellViewHolder(@NonNull View itemView) {

        super(itemView);
        textView = itemView.findViewById(R.id.cell_data);
        container = itemView.findViewById(R.id.cell_container);
    }

    public void setCell(@Nullable Cell cell) {

        textView.setText(String.valueOf(Objects.requireNonNull(cell).getData()));

//        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        cell_textview.requestLayout();
    }
}
