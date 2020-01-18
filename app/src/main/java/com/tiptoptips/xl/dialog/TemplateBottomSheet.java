package com.tiptoptips.xl.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.TemplateAdapter;
import com.tiptoptips.xl.listener.OnTemplateSelectedListener;
import com.tiptoptips.xl.model.Template;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplateBottomSheet extends BottomSheetDialogFragment implements OnTemplateSelectedListener {

    @BindView(R.id.bottom_template_recycler_view)
    RecyclerView bottomTemplateRecyclerView;

    private OnTemplateSelectedListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_bottom_template, container, false);
        ButterKnife.bind(this, view);

        List<Template> templateList = new ArrayList<>();
        Template template = new Template();

        template.setTemplateName("Default");
        template.setTemplateThumb(android.R.drawable.btn_plus);
        templateList.add(new Template("Default", android.R.drawable.btn_plus));
        templateList.add(new Template("Product Catalogue", android.R.drawable.btn_plus));

        for (int i = 2; i < 6; i++) {

            templateList.add(template);
        }

        TemplateAdapter adapter = new TemplateAdapter(getContext(), templateList, this);
        adapter.setListener(listener);

        bottomTemplateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        bottomTemplateRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bottomTemplateRecyclerView.setAdapter(adapter);

        return view;
    }

    public void setListener(OnTemplateSelectedListener listener) {

        this.listener = listener;
    }

    @Override
    public void onTemplateSelected(int template) {

        listener.onTemplateSelected(template);
    }
}
