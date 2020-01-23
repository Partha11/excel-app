package com.tiptoptips.xl.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.FilesAdapter;
import com.tiptoptips.xl.dialog.TemplateBottomSheet;
import com.tiptoptips.xl.listener.OnItemClickedListener;
import com.tiptoptips.xl.listener.OnTemplateSelectedListener;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.utility.Constants;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.view.editor.FileEditActivity;
import com.tiptoptips.xl.view.selector.SelectorActivity;
import com.tiptoptips.xl.view.settings.SettingsActivity;
import com.tiptoptips.xl.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity implements OnItemClickedListener, OnTemplateSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.files_recycler_view)
    RecyclerView filesRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.dashboard_fab)
    FloatingActionButton dashboardFab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;


    private FilesAdapter adapter;
    private List<UserFile> fileList;
    private DashboardViewModel viewModel;
    private SharedPrefs prefs;

    private boolean doubleBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (FirebaseApp.getApps(this).isEmpty()) {

            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        initialize();
        setNavigationView();
    }

    private void initialize() {

        fileList = new ArrayList<>();
        adapter = new FilesAdapter(this, fileList);
        prefs = new SharedPrefs(this);

        filesRecyclerView.setAdapter(adapter);
        filesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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

                            UserFile file = d.getValue(UserFile.class);
                            adapter.addFile(file);
                            adapter.setListener(this);
                        }
                    }
                });
    }

    //DSFSAF

    public void setNavigationView() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        navigationView.setNavigationItemSelectedListener(this);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.navDrawer_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;

            case R.id.navDrawer_Buy:
                break;

            case R.id.navDrawer_rateUs:
                break;

            case R.id.navDrawer_exit:
                Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
                finishAndRemoveTask();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
            return;

        } else if (doubleBackPressed) {

            super.onBackPressed();
            return;
        }

        this.doubleBackPressed = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackPressed = false, 2000);
    }

    @Override
    public void onItemClick(UserFile file) {

        if (file != null) {

            Intent intent = new Intent(this, FileEditActivity.class);
            intent.putExtra(Constants.SELECTED_ITEM, file.getFileKey());

            startActivity(intent);
        }
    }

    @OnClick(R.id.dashboard_fab)
    public void onViewClicked() {

        TemplateBottomSheet bottomSheet = new TemplateBottomSheet();

        bottomSheet.setListener(this);
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
            UserFile file = new UserFile();
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

    @Override
    public void onTemplateSelected(int template) {

        Intent intent = new Intent(DashboardActivity.this, SelectorActivity.class);

        intent.putExtra(Constants.TEMPLATE_ID, template);
        startActivity(intent);

/*        switch (template) {

            case Constants.TEMPLATE_STANDARD:

                Template t = Utils.getStandardFileFormat();

                if (t != null) {

                    UserFile file = new UserFile();
                    file.setFileData(t.getCells());
                }

                break;
            case Constants.TEMPLATE_TO_DO:
                break;
            case Constants.TEMPLATE_EXPENSE:
                break;
            case Constants.TEMPLATE_CATALOGUE:
                break;
            case Constants.TEMPLATE_CONTACTS:
                break;
            case Constants.TEMPLATE_MEDICAL:
                break;
            case Constants.TEMPLATE_GROCERY:
                break;
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.option_button) {

            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return true;
    }

}
