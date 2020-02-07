package com.tiptoptips.xl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

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
import butterknife.OnClick;

public class DragAdapter extends RecyclerView.Adapter<DragAdapter.ViewHolder> implements DraggableItemAdapter<DragAdapter.ViewHolder> {

    private Context context;
    private List<CheckBoxListItem> itemList;

    public DragAdapter(Context context, List<CheckBoxListItem> itemList) {

        setHasStableIds(true);

        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.model_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.modelText.setText(itemList.get(position).getName());

        if (itemList.get(position).isChecked()) {

            holder.checkBox.setChecked(true);

        } else {

            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {

        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public long getItemId(int position) {

        return itemList.get(position).getId();
    }

    @Override
    public boolean onCheckCanStartDrag(@NonNull ViewHolder holder, int position, int x, int y) {

        View dragHandle = holder.dragHandle;

        int handleWidth = dragHandle.getWidth();
        int handleHeight = dragHandle.getHeight();
        int handleLeft = dragHandle.getLeft();
        int handleTop = dragHandle.getTop();

        return (x >= handleLeft) && (x < handleLeft + handleWidth) &&
                (y >= handleTop) && (y < handleTop + handleHeight);
    }

    @Nullable
    @Override
    public ItemDraggableRange onGetItemDraggableRange(@NonNull ViewHolder holder, int position) {

        return null;
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {

        CheckBoxListItem item = itemList.remove(fromPosition);
        itemList.add(toPosition, item);
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

    public List<CheckBoxListItem> getAllItems() {

        return itemList;
    }

    class ViewHolder extends AbstractDraggableItemViewHolder implements CompoundButton.OnCheckedChangeListener {

        @BindView(R.id.drag_handle)
        View dragHandle;
        @BindView(R.id.model_check_box)
        CheckBox checkBox;
        @BindView(R.id.model_text)
        AppCompatTextView modelText;
        @BindView(R.id.hamburger_button)
        AppCompatImageButton hamburgerButton;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

            checkBox.setOnCheckedChangeListener(this);
        }

        @OnClick({R.id.model_text, R.id.hamburger_button})
        public void onViewClicked(View view) {

            switch (view.getId()) {

                case R.id.model_text:
                    checkBox.setChecked(!checkBox.isChecked());
                    itemList.get(getAdapterPosition()).setChecked(checkBox.isChecked());
                    break;

                case R.id.hamburger_button:
                    Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            itemList.get(getAdapterPosition()).setChecked(b);
        }
    }
}
