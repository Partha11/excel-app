package com.tiptoptips.xl.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.FilesAdapter;
import com.tiptoptips.xl.dialog.TemplateBottomSheet;
import com.tiptoptips.xl.listener.OnItemClickedListener;
import com.tiptoptips.xl.model.DataFile;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.utility.Constants;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.utility.Utils;
import com.tiptoptips.xl.view.editor.FileEditActivity;
import com.tiptoptips.xl.viewmodel.DashboardViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    private List<DataFile> fileList;
    private DashboardViewModel viewModel;
    private SharedPrefs prefs;

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
        fileList = new ArrayList<>();
        adapter = new FilesAdapter(this, fileList);
        prefs = new SharedPrefs(this);
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        filesRecyclerView.setAdapter(adapter);
        filesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        filesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        viewModel.setLiveData(prefs.getUid());
        viewModel.getUserFiles()
                .observe(this, dataSnapshot -> {

                    if (dataSnapshot.hasChildren()) {

                        if (fileList != null) {

                            fileList.clear();

                        } else {

                            fileList = new ArrayList<>();
                        }

                        for (DataSnapshot d : dataSnapshot.getChildren()) {

                            DataFile file = d.getValue(DataFile.class);
                            adapter.addFile(file);
                            adapter.setListener(this);
                        }
                    }
                });

//        viewModel.getUserFiles(prefs.getUid())
//                .observe(this, userFiles -> {
//
//                    if (userFiles != null && userFiles.size() > 0) {
//
//                        if (userFileList != null) {
//
//                            userFileList.clear();
//                            userFileList.addAll(userFiles);
//
//                        } else {
//
//                            userFileList = new ArrayList<>(userFiles);
//                        }
//                    }
//
//                    progressBar.setVisibility(View.GONE);
//                    adapter.setFileList(userFileList);
//                    adapter.setListener(this);
//                });
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
    public void onItemClick(DataFile file) {

        if (file != null) {

            Intent intent = new Intent(this, FileEditActivity.class);
            intent.putExtra(Constants.SELECTED_ITEM, file.getFileKey());

            startActivity(intent);
        }
    }

    @OnClick(R.id.dashboard_fab)
    public void onViewClicked() {

        TemplateBottomSheet bottomSheet = new TemplateBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), "template");

/*        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(prefs.getUid()).child("userFiles");
        String fileKey = reference.push().getKey();

        if (fileKey != null) {

            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> template = Utils.getStandardFileFormat();
            List<HashMap<String, String>> objectList = new ArrayList<>();
            Date date = Calendar.getInstance().getTime();

            for (Map<String, Object> object : template) {

                HashMap<String, String> tmp = new HashMap<>();

                for (Map.Entry<String, Object> entry : object.entrySet()) {

                    tmp.put(entry.getKey(), String.valueOf(entry.getValue()));
                }

                objectList.add(tmp);
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            String formattedDate = df.format(date);
            DataFile file = new DataFile();
            HashMap<String, String> model = new HashMap<>();
            List<HashMap<String, String>> object = new ArrayList<>();

            model.put("name", "Test File " + Math.random());
            model.put("address", "A Random Address");
            model.put("date", formattedDate);
            model.put("phoneNumber", "01711111111");

            object.add(model);

            file.setFileName("Test File v2");
            file.setFileData(object);
            file.setCreationDate(formattedDate);
            file.setSharedWith(new ArrayList<>());

            viewModel.insertFile(prefs.getUid(), file).observe(this, key -> {

                if (key != null) {

                    Intent intent = new Intent(DashboardActivity.this, FileEditActivity.class);
                    intent.putExtra(Constants.SELECTED_ITEM, key);
                    startActivity(intent);

                } else {

                    Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }*/
    }
}
