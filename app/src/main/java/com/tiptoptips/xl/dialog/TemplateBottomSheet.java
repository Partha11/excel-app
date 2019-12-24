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
import com.tiptoptips.xl.model.Template;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplateBottomSheet extends BottomSheetDialogFragment {

    @BindView(R.id.bottom_template_recycler_view)
    RecyclerView bottomTemplateRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_bottom_template, container, false);
        ButterKnife.bind(this, view);

        List<Template> templateList = new ArrayList<>();
        Template template = new Template();

        template.setTemplateName("Default");
        template.setTemplateThumb(android.R.drawable.btn_plus);

        for (int i = 0; i < 6; i++) {

            templateList.add(template);
        }

        TemplateAdapter adapter = new TemplateAdapter(getContext(), templateList);

        bottomTemplateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        bottomTemplateRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bottomTemplateRecyclerView.setAdapter(adapter);

        return view;
    }
}
