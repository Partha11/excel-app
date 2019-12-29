package com.tiptoptips.xl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.tiptoptips.xl.R;
import com.tiptoptips.xl.dialog.TemplateBottomSheet;
import com.tiptoptips.xl.listener.OnTemplateSelectedListener;
import com.tiptoptips.xl.model.Template;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.ViewHolder> {

    private TemplateBottomSheet dialog;
    private Context context;
    private OnTemplateSelectedListener listener;
    private List<Template> templateList;

    public TemplateAdapter(Context context, List<Template> templateList, TemplateBottomSheet dialog) {

        this.context = context;
        this.templateList = templateList;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.model_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.templateTitle.setText(templateList.get(position).getTemplateName());
//        holder.templateThumb.setImageDrawable(context.getResources().getDrawable(templateList.get(position).getTemplateThumb(),
//                context.getTheme()));
    }

    @Override
    public int getItemCount() {

        return templateList == null ? 0 : templateList.size();
    }

    public void setListener(OnTemplateSelectedListener listener) {

        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.template_thumb)
        AppCompatImageView templateThumb;
        @BindView(R.id.template_title)
        AppCompatTextView templateTitle;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.template_root)
        public void onViewClicked() {

            dialog.dismiss();
            listener.onTemplateSelected(getAdapterPosition());
        }
    }
}
