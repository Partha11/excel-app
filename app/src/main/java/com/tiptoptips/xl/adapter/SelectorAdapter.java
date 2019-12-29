package com.tiptoptips.xl.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.tiptoptips.xl.R;
import com.tiptoptips.xl.listener.OnFileModifiedListener;
import com.tiptoptips.xl.utility.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.ViewHolder> {

    private Context context;
    private Utils utils;

    private List<String> columnNames;
    private List<Integer> columnTypes;
    private OnFileModifiedListener listener;

    public SelectorAdapter(Context context, List<String> columnNames, List<Integer> columnTypes) {

        this.context = context;
        this.columnNames = columnNames;
        this.columnTypes = columnTypes;
        this.utils = new Utils(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.model_column_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.columnName.setText(columnNames.get(position));
        holder.columnType.setText(utils.getColumnName(columnTypes.get(position)));
    }

    @Override
    public int getItemCount() {

        return columnNames == null ? 0 : columnNames.size();
    }

    public void setColumns(List<String> columnNames, List<Integer> columnTypes) {

        this.columnNames = columnNames;
        this.columnTypes = columnTypes;

        notifyDataSetChanged();
    }

    public void setListener(OnFileModifiedListener listener) {

        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.column_name)
        AppCompatTextView columnName;
        @BindView(R.id.column_type_thumb)
        AppCompatImageView columnTypeThumb;
        @BindView(R.id.column_type)
        AppCompatTextView columnType;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.edit_column_name, R.id.type_selector_layout})
        public void onViewClicked(View view) {

            if (view.getId() == R.id.edit_column_name) {

                AppCompatEditText editText = new AppCompatEditText(context);
                editText.setHint("Name");
                editText.setText(columnNames.get(getAdapterPosition()));

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Edit Field Name")
                        .setView(editText)
                        .setPositiveButton("Set", (dialogInterface, i) -> {

                            if (editText.getText() != null && !TextUtils.isEmpty(editText.getText().toString())) {

                                String text = editText.getText().toString();

                                columnNames.set(getAdapterPosition(), text.trim());
                                listener.onFileNameChanged(text);
                                notifyItemChanged(getAdapterPosition());
                            }
                        })
                        .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

                builder.create().show();

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );

                params.setMargins((int) context.getResources().getDimension(R.dimen._15sdp),
                        (int) context.getResources().getDimension(R.dimen._15sdp),
                        (int) context.getResources().getDimension(R.dimen._15sdp), 0);
                editText.setLayoutParams(params);

            } else {

                String[] items = context.getResources().getStringArray(R.array.column_options);
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Select Type")
                        .setItems(items, (dialogInterface, i) -> {

                            dialogInterface.dismiss();
                            columnTypes.set(getAdapterPosition(), i);
                            notifyItemChanged(getAdapterPosition());
                        });

                builder.create().show();
            }
        }
    }
}
