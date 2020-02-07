package com.tiptoptips.xl.dialog;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.DragAdapter;
import com.tiptoptips.xl.listener.OnItemSelectedListener;
import com.tiptoptips.xl.model.CheckBoxListItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListDialog extends DialogFragment {

    @BindView(R.id.list_add_item)
    AppCompatEditText addItemText;
    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;

    private Context context;
    private DragAdapter adapter;

    private List<CheckBoxListItem> itemList;
    private OnItemSelectedListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_list, container, false);
        ButterKnife.bind(this, view);

        initialize();

        return view;
    }

    private void initialize() {

        if (itemList == null) {

            itemList = new ArrayList<>();
        }

        RecyclerViewDragDropManager manager = new RecyclerViewDragDropManager();
        adapter = new DragAdapter(context, itemList);

        manager.attachRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(manager.createWrappedAdapter(adapter));

        manager.setInitiateOnMove(false);
        manager.setInitiateOnLongPress(true);
    }

    @Override
    public void onResume() {

        super.onResume();

        Window window = Objects.requireNonNull(getDialog()).getWindow();
        Point point = new Point();
        Display display = Objects.requireNonNull(window).getWindowManager().getDefaultDisplay();

        display.getSize(point);
        int width = point.x;

        window.setLayout((int) (width * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    public void setListener(OnItemSelectedListener listener) {

        this.listener = listener;
    }

    @OnClick({R.id.confirm_list_button, R.id.add_item_button})
    public void onViewClicked(View view) {

        if (view.getId() == R.id.confirm_list_button) {

            Type type = new TypeToken<List<CheckBoxListItem>>() {}.getType();
            List<CheckBoxListItem> items = adapter.getAllItems();

            listener.onItemSelect(new Gson().toJson(items, type));
            dismiss();

        } else {

            if (addItemText.getText() == null) {

                addItemText.setError("Required");

            } else {

                itemList.add(new CheckBoxListItem(itemList.size(), addItemText.getText().toString(), false));
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void updateList(String data) {

        Type type = new TypeToken<List<CheckBoxListItem>>() {}.getType();

        if (itemList == null) {

            itemList = new ArrayList<>();
        }

        if (data != null && !TextUtils.isEmpty(data)) {

            itemList.clear();
            itemList.addAll(new Gson().fromJson(data, type));

            if (adapter != null) {

                adapter.notifyDataSetChanged();
            }
        }
    }
}
