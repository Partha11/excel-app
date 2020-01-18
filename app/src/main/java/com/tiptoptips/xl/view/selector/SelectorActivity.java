package com.tiptoptips.xl.view.selector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.SelectorAdapter;
import com.tiptoptips.xl.listener.OnFileModifiedListener;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.model.Template;
import com.tiptoptips.xl.utility.Constants;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.utility.Utils;
import com.tiptoptips.xl.view.editor.FileEditActivity;
import com.tiptoptips.xl.viewmodel.SelectorViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectorActivity extends AppCompatActivity implements ScrollableNumberPickerListener, OnFileModifiedListener {

    @BindView(R.id.selector_file_name)
    AppCompatEditText selectorFileName;
    @BindView(R.id.row_picker)
    ScrollableNumberPicker rowPicker;
    @BindView(R.id.column_picker)
    ScrollableNumberPicker columnPicker;
    @BindView(R.id.column_item_recycler_view)
    RecyclerView columnItemRecyclerView;

    private Template template;
    private SelectorAdapter adapter;
    private SelectorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Define Columns");

        retrieveData();

        rowPicker.setMinValue(1);
        rowPicker.setValue(1);
        columnPicker.setMinValue(1);
        columnPicker.setListener(this);
    }

    private void retrieveData() {

        viewModel = ViewModelProviders.of(this).get(SelectorViewModel.class);

        if (getIntent().hasExtra(Constants.TEMPLATE_ID)) {

            int type = getIntent().getIntExtra(Constants.TEMPLATE_ID, 0);
            template = Utils.getTemplate(type);

            adapter = new SelectorAdapter(this, template.getColumnNames(), template.getColumnTypes());
            adapter.setListener(this);
            columnPicker.setValue(template.getColumnNames().size());

            columnItemRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            columnItemRecyclerView.setItemAnimator(new DefaultItemAnimator());
            columnItemRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.confirm_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.confirm) {

            if (selectorFileName.getText() == null || selectorFileName.getText().toString().isEmpty()) {

                selectorFileName.setError(getResources().getString(R.string.email_error));

            } else {

                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Creating File");
                dialog.setMessage("Please Wait...");
                dialog.show();

                new Thread(() -> {

                    List<HashMap<String, String>> obj = new ArrayList<>();
                    int rowCount = rowPicker.getValue();

                    for (int i = 0; i < rowCount; i++) {

                        HashMap<String, String> temp = new HashMap<>();

                        for (int j = 0; j < template.getColumnNames().size(); j++) {

                            temp.put(template.getColumnNames().get(j), "");
                        }

                        obj.add(temp);
                    }

                    UserFile file = new UserFile();
                    SharedPrefs prefs = new SharedPrefs(this);
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    String formattedDate = df.format(date);

                    file.setFileName(selectorFileName.getText().toString());
                    file.setCreationDate(formattedDate);
                    file.setFileData(obj);
                    file.setSharedWith(new ArrayList<>());
                    file.setColumnTypes(template.getColumnTypes());

                    runOnUiThread(() -> viewModel.insertFile(prefs.getUid(), file)
                            .observe(this, s -> {

                        if (s != null) {

                            dialog.dismiss();

                            Intent intent = new Intent(SelectorActivity.this, FileEditActivity.class);
                            intent.putExtra(Constants.SELECTED_ITEM, s);
                            finish();
                            startActivity(intent);
                        }
                    }));

                }).start();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNumberPicked(int value) {

        List<String> columnNames = template.getColumnNames();
        List<Integer> columnTypes = template.getColumnTypes();

        if (value > columnNames.size()) {

            int diff = value - columnNames.size();

            for (int i = 0; i < diff; i++) {

                columnNames.add("Field");
                columnTypes.add(Constants.TEXT_COLUMN);
            }

        } else {

            for (int i = columnNames.size() - 1; i >= value; i--) {

                columnNames.remove(i);
                columnTypes.remove(i);
            }
        }

        adapter.setColumns(columnNames, columnTypes);
    }

    @Override
    public void onFileNameChanged(String text) {

        Log.d("Name", template.getColumnNames().get(template.getColumnNames().size() - 1));
    }

    @Override
    public void onFileTypeChanged(int type) {

    }
}
