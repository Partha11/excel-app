package com.tiptoptips.xl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.model.CheckBoxListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DragAdapter extends RecyclerView.Adapter<DragAdapter.ViewHolder> implements DraggableItemAdapter<DragAdapter.ViewHolder> {

    private Context context;
    private List<CheckBoxListItem> list;

    public DragAdapter(Context context, List<CheckBoxListItem> list) {

        setHasStableIds(true);

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.model_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.modelText.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }

    @Override
    public long getItemId(int position) {

        return list.get(position).getId();
    }

    @Override
    public boolean onCheckCanStartDrag(@NonNull ViewHolder holder, int position, int x, int y) {

        return true;
    }

    @Nullable
    @Override
    public ItemDraggableRange onGetItemDraggableRange(@NonNull ViewHolder holder, int position) {

        return null;
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {

        CheckBoxListItem item = list.remove(fromPosition);
        list.add(toPosition, item);
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {

        return true;
    }

    @Override
    public void onItemDragStarted(int position) {

        notifyDataSetChanged();
    }

    @Override
    public void onItemDragFinished(int fromPosition, int toPosition, boolean result) {

        notifyDataSetChanged();
    }

    class ViewHolder extends AbstractDraggableItemViewHolder {

        @BindView(R.id.drag_handle)
        View dragHandle;
        @BindView(R.id.model_check_box)
        CheckBox modelCheckBox;
        @BindView(R.id.model_text)
        AppCompatTextView modelText;
        @BindView(R.id.hamburger_button)
        AppCompatImageButton hamburgerButton;

        TextView textView;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
