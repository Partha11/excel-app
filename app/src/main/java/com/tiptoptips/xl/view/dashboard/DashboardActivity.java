package com.tiptoptips.xl.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.FilesAdapter;
import com.tiptoptips.xl.listener.OnItemClickedListener;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.utility.Constants;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.view.editor.FileEditActivity;
import com.tiptoptips.xl.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity implements OnItemClickedListener {

    @BindView(R.id.files_recycler_view)
    RecyclerView filesRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.dashboard_fab)
    FloatingActionButton dashboardFab;

    private FilesAdapter adapter;
    private List<UserFile> userFileList;

    private boolean doubleBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        initialize();
    }

    private void initialize() {

        userFileList = new ArrayList<>();
        adapter = new FilesAdapter(this, userFileList);
        SharedPrefs prefs = new SharedPrefs(this);
        DashboardViewModel viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        progressBar.setVisibility(View.VISIBLE);
        filesRecyclerView.setAdapter(adapter);
        filesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        filesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        viewModel.getUserFiles(prefs.getUid())
                .observe(this, userFiles -> {

                    if (userFiles != null && userFiles.size() > 0) {

                        if (userFileList != null) {

                            userFileList.clear();
                            userFileList.addAll(userFiles);

                        } else {

                            userFileList = new ArrayList<>(userFiles);
                        }

                        progressBar.setVisibility(View.GONE);
                        adapter.setFileList(userFileList);
                        adapter.setListener(this);

                    } else {

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onBackPressed() {

        if (doubleBackPressed) {

            super.onBackPressed();
            return;
        }

        this.doubleBackPressed = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackPressed = false, 2000);
    }

    @Override
    public void onItemClick(int id) {

        if (id > 0) {

            Intent intent = new Intent(this, FileEditActivity.class);
            intent.putExtra(Constants.SELECTED_ITEM_ID, id);

            startActivity(intent);
        }
    }

    @OnClick(R.id.dashboard_fab)
    public void onViewClicked() {

        Toast.makeText(this, "Fab", Toast.LENGTH_SHORT).show();
    }
}
