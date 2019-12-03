package com.tiptoptips.xl.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;

import com.tiptoptips.xl.R;
import com.tiptoptips.xl.listener.DialogPositionListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColumnDialog extends DialogFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.column_list)
    ListView columnList;

    private DialogPositionListener listener;
    private Context context;

    public ColumnDialog() {
        //Empty
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_column, container, false);
        ButterKnife.bind(this, view);

        String[] items = context.getResources().getStringArray(R.array.column_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items);

        columnList.setAdapter(adapter);
        columnList.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    public void setListener(DialogPositionListener listener) {

        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        final AppCompatEditText editText = new AppCompatEditText(context);
        editText.setHint("Column Name");
        this.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Set Column Title")
                .setView(editText)
                .setPositiveButton("Set", (dialogInterface, i1) -> {

                    listener.onItemClicked(i, Objects.requireNonNull(editText.getText()).toString());
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Cancel", (dialogInterface, i1) -> dialogInterface.dismiss());

        builder.create().show();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins((int) context.getResources().getDimension(R.dimen._15sdp),
                (int) context.getResources().getDimension(R.dimen._15sdp),
                (int) context.getResources().getDimension(R.dimen._15sdp), 0);
        editText.setLayoutParams(params);
    }
}
