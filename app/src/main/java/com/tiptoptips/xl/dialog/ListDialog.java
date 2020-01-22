package com.tiptoptips.xl.dialog;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
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

import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.DragAdapter;
import com.tiptoptips.xl.model.CheckBoxListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListDialog extends DialogFragment {

    @BindView(R.id.list_add_item)
    AppCompatEditText listAddItem;
    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_list, container, false);
        ButterKnife.bind(this, view);

        RecyclerViewDragDropManager manager = new RecyclerViewDragDropManager();
        List<CheckBoxListItem> list = new ArrayList<>();
        DragAdapter adapter = new DragAdapter(context, list);

        for (int i = 0; i < 6; i++) {

            list.add(new CheckBoxListItem(i, String.valueOf(i), false));
        }

        manager.attachRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(manager.createWrappedAdapter(adapter));

        manager.setInitiateOnMove(false);
        manager.setInitiateOnLongPress(true);

        return view;
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

    @OnClick(R.id.confirm_list_button)
    public void onViewClicked() {
    }
}
