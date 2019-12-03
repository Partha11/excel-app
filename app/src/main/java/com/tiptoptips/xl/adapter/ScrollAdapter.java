package com.tiptoptips.xl.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiptoptips.xl.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.ViewHolder> {

    private List<List<View>> dataList;

    public ScrollAdapter(List<List<View>> dataList) {

        this.dataList = dataList;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_cell, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        for (int i = 0; i < dataList.get(position).size(); i++) {

            if (dataList.get(position).get(i) instanceof TextView) {

                Log.d("Added", "Textview");

                TextView textView = new TextView(holder.modelRootLayout.getContext());
                textView.setText("AAA");
                textView.setTextSize(16);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(Color.BLUE);
                textView.setPadding(10, 10, 10, 10);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                holder.modelRootLayout.addView(textView);
            }
        }
    }

    @Override
    public int getItemCount() {

        return dataList == null ? 0 : dataList.size();
    }

    public void addRow(int count) {

        for (int i = 0; i < count; i++) {

            List<View> list = new ArrayList<>(dataList.get(0).size());
            dataList.add(list);
        }

        Log.d("Size", String.valueOf(dataList.size()));
        notifyDataSetChanged();
    }

    public void addColumn(View view) {

        for (int i = 0; i < dataList.size(); i++) {

            dataList.get(i).add(view);
        }

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.model_root_layout)
        LinearLayout modelRootLayout;

        ViewHolder(View view) {

            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
