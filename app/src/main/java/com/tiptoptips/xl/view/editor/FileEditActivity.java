package com.tiptoptips.xl.view.editor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.TableAdapter;
import com.tiptoptips.xl.dialog.ColumnDialog;
import com.tiptoptips.xl.dialog.ListDialog;
import com.tiptoptips.xl.listener.DialogPositionListener;
import com.tiptoptips.xl.model.Cell;
import com.tiptoptips.xl.model.ColumnHeader;
import com.tiptoptips.xl.model.RowHeader;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.utility.Constants;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.viewmodel.FileEditViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileEditActivity extends AppCompatActivity implements DialogPositionListener, ITableViewListener {

    @BindView(R.id.editor_fab)
    FloatingActionButton editorFab;
    @BindView(R.id.table_view)
    TableView tableView;

    private List<ColumnHeader> columnList;
    private List<RowHeader> rowList;
    private List<String> columnNames;
    private List<List<Cell>> twoDimensionalCell;

    private UserFile file;
    private SharedPrefs prefs;
    private TableAdapter adapter;
    private FileEditViewModel fileViewModel;

    private static int rowCount = 0;
    private static int columnCount = 0;

    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);
        ButterKnife.bind(this);

        adapter = new TableAdapter(this);
        prefs = new SharedPrefs(this);
        fileViewModel = new ViewModelProvider(this).get(FileEditViewModel.class);

        tableView.setAdapter(adapter);
        tableView.setTableViewListener(this);

/*        cells = new ArrayList<>();
        columnTypeList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            List<Cell> tempList = new ArrayList<>();

            tempList.add(new Cell("1", "name 1"));
            tempList.add(new Cell("2", "name 2"));
            tempList.add(new Cell("3", "name 3"));

            cells.add(tempList);
        }

        columnTypeList.add(Constants.TEXT_COLUMN);
        columnTypeList.add(Constants.TEXT_COLUMN);
        columnTypeList.add(Constants.TEXT_COLUMN);

        columnHeaders = new ArrayList<>();

        columnHeaders.add(new ColumnHeader("1", "Student"));
        columnHeaders.add(new ColumnHeader("2", "classteach"));
        columnHeaders.add(new ColumnHeader("3", "crs"));

        rowList = new ArrayList<>();

        rowList.add(new RowHeader("1", "1"));
        rowList.add(new RowHeader("2", "2"));
        rowList.add(new RowHeader("3", "3"));
        rowList.add(new RowHeader("4", "4"));

        adapter.setColumnType(columnTypeList);
        adapter.setAllItems(columnHeaders, rowList, cells);*/

        parseJson();
    }

/*                            if (file != null) {

        if (file.startsWith("http://") || file.startsWith("https://")) {

            fileViewModel.getJson(file)
                    .observe(this, s -> {

                        if (!TextUtils.isEmpty(s)) {

                            new Thread(() -> {

                                TableModel model = Utils.parseJson(s);

                                if (model != null) {

                                    Log.d("Size", String.valueOf(model.getColumnList().size()));

                                    runOnUiThread(() -> {

                                        adapter.setColumnType(model.getColumnTypeList());
                                        adapter.setAllItems(model.getColumnList(), model.getRowList(), model.getCellList());
                                    });
                                }

                            }).start();
                        }
                    });
        } else {

            new Thread(() -> {

                TableModel model = Utils.parseJson(file);

                if (model != null) {

                    Log.d("Size", String.valueOf(model.getColumnList().size()));

                    runOnUiThread(() -> {

                        adapter.setColumnType(model.getColumnTypeList());
                        adapter.setAllItems(model.getColumnList(), model.getRowList(), model.getCellList());
                    });
                }

            }).start();
        }

    } else {

        Log.d("Null", String.valueOf(getIntent().getIntExtra(Constants.SELECTED_ITEM, -1)));
    }*/

    private void parseJson() {

        if (getIntent().hasExtra(Constants.SELECTED_ITEM)) {

            SharedPrefs prefs = new SharedPrefs(this);
            String key = getIntent().getStringExtra(Constants.SELECTED_ITEM);

            if (key != null) {

                this.key = key;

                fileViewModel.setLiveData(prefs.getUid(), key);
                fileViewModel.getFile().observe(this, dataSnapshot -> {

                    if (dataSnapshot.hasChildren()) {

                        columnList = new ArrayList<>();
                        columnNames = new ArrayList<>();
                        rowList = new ArrayList<>();
                        twoDimensionalCell = new ArrayList<>();

                        file = dataSnapshot.getValue(UserFile.class);

                        if (file != null) {

                            rowCount = file.getFileData().size();

                            for (int i = 0; i < rowCount; i++) {

                                List<Cell> cellList = new ArrayList<>();
                                TreeMap<String, String> map = new TreeMap<>(file.getFileData().get(i));

                                for (Map.Entry<String, String> entry : map.entrySet()) {

                                    if (i == 0) {

                                        columnCount = map.size();
                                        columnList.add(new ColumnHeader(String.valueOf(i), entry.getKey()));
                                        columnNames.add(entry.getKey());
                                    }

                                    cellList.add(new Cell(String.valueOf(i), entry.getValue()));
                                }

                                rowList.add(new RowHeader(String.valueOf(i + 1), String.valueOf(i + 1)));
                                twoDimensionalCell.add(cellList);

                                adapter.setColumnType(file.getColumnTypes());
                                adapter.setAllItems(columnList, rowList, twoDimensionalCell);
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {

            case R.id.add_row:

                addNewRow();
                return true;

            case R.id.add_column:

                ColumnDialog fragment = new ColumnDialog();

                fragment.setListener(this);
                fragment.show(getSupportFragmentManager(), "column");
                return true;

            case R.id.export_excel:
                //create xls from json
                return true;
            case R.id.add_people:
                //add people dialog
                return true;
            default:
                return false;

        }
    }

    private void addNewRow() {

        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(1);

        AlertDialog.Builder rowAlertBuilder = new AlertDialog.Builder(this)
                .setView(numberPicker)
                .setTitle("Insert Multiple Rows")
                .setPositiveButton("Create", (dialog, which) -> {

                    int count = numberPicker.getValue();

                    Log.d("Rows:", String.valueOf(rowCount));
                    Log.d("Columns:", String.valueOf(columnCount));

                    for (int k = 0; k < count; k++) {

                        List<Cell> tempList = new ArrayList<>();
                        HashMap<String, String> newRow = new HashMap<>();

                        for (int j = 0; j < columnCount; j++) {

                            tempList.add(new Cell(String.valueOf(columnCount + j + 1), ""));
                            newRow.put(String.valueOf(columnList.get(j).getData()), "");
                        }

                        rowList.add(new RowHeader(String.valueOf(++rowCount),
                                String.valueOf(rowCount)));
                        twoDimensionalCell.add(tempList);
                        adapter.setAllItems(columnList, rowList, twoDimensionalCell);

                        if (file != null && !TextUtils.isEmpty(key)) {

                            file.getFileData().add(newRow);
                            fileViewModel.update(file.getFileData(), key, prefs.getUid());
                        }

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        rowAlertBuilder.create().show();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void checkPermissions() {

        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {

                    if (granted) {

                        requestGallery();

                    } else {

                        Toast.makeText(this, "Permission needed for this action", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void requestGallery() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] types = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
        startActivityForResult(intent, Constants.GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClicked(int position, String text) {

        columnList.add(new ColumnHeader(String.valueOf(++columnCount), text));
        columnNames.add(text);
        file.getColumnTypes().add(position);

        for (int i = 0; i < twoDimensionalCell.size(); i++) {

            twoDimensionalCell.get(i).add(new Cell(String.valueOf(twoDimensionalCell.size() + 1), ""));
            file.getFileData().get(i).put(text, "");
        }

        fileViewModel.update(file.getFileData(), file.getFileKey(), prefs.getUid());
        adapter.setColumnType(file.getColumnTypes());
        adapter.setAllItems(columnList, rowList, twoDimensionalCell);
    }

    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder viewHolder, int column, int row) {

        if (file != null) {

            switch (file.getColumnTypes().get(column)) {

                case Constants.TEXT_COLUMN:

                    AppCompatEditText editText = new AppCompatEditText(this);
                    String[] array = columnNames.get(column).split("__");
                    StringBuilder hint = new StringBuilder();

                    for (int i = 1; i < array.length; i++) {

                        hint.append(array[i]);
                    }

                    editText.setHint(hint.toString());

                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Edit Field")
                            .setView(editText)
                            .setPositiveButton("Set", (dialogInterface, i) -> {

                                if (editText.getText() != null && !TextUtils.isEmpty(editText.getText().toString())) {

                                    String text = editText.getText().toString();
                                    twoDimensionalCell.get(row).get(column).setData(text);

                                    file.getFileData().get(row).put(String.valueOf(columnList.get(column).getData()), text.trim());
                                    fileViewModel.update(file.getFileData(), key, prefs.getUid());

                                    adapter.getCellItem(column, row).setData(text);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

                    builder.create().show();

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                    );

                    params.setMargins((int) getResources().getDimension(R.dimen._15sdp),
                            (int) getResources().getDimension(R.dimen._15sdp),
                            (int) getResources().getDimension(R.dimen._15sdp), 0);
                    editText.setLayoutParams(params);

                    break;

                case Constants.IMAGE_COLUMN:

                    checkPermissions();
                    break;

                case Constants.CHECKBOX_COLUMN:

                    String text = Objects.requireNonNull(twoDimensionalCell.get(row).get(column).getData()).toString();
                    String value = text.equals("true") ? "false" : "true";

                    twoDimensionalCell.get(row).get(column).setData(value);

                    file.getFileData().get(row).put(String.valueOf(columnList.get(column).getData()), value);
                    fileViewModel.update(file.getFileData(), key, prefs.getUid());

                    adapter.getCellItem(column, row).setData(value);
                    adapter.notifyDataSetChanged();
                    break;

                case Constants.LIST_COLUMN:

                    ListDialog dialog = new ListDialog();

                    Log.d("Data", Objects.requireNonNull(twoDimensionalCell.get(row).get(column).getData()).toString());

                    dialog.updateList(Objects.requireNonNull(twoDimensionalCell.get(row).get(column).getData()).toString());
                    dialog.show(getSupportFragmentManager(), "list");
                    dialog.setListener(data -> {

                        twoDimensionalCell.get(row).get(column).setData(data);

                        file.getFileData().get(row).put(String.valueOf(columnList.get(column).getData()), data);
                        fileViewModel.update(file.getFileData(), key, prefs.getUid());

                        adapter.getCellItem(column, row).setData(data);
                        adapter.notifyDataSetChanged();
                    });

                    break;
            }
        }
    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder viewHolder, int column, int row) {

    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }
}
