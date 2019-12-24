package com.tiptoptips.xl.view.editor;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.TableAdapter;
import com.tiptoptips.xl.dialog.ColumnDialog;
import com.tiptoptips.xl.listener.DialogPositionListener;
import com.tiptoptips.xl.model.Cell;
import com.tiptoptips.xl.model.ColumnHeader;
import com.tiptoptips.xl.model.DataFile;
import com.tiptoptips.xl.model.RowHeader;
import com.tiptoptips.xl.utility.Constants;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.viewmodel.FileEditViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileEditActivity extends AppCompatActivity implements DialogPositionListener, ITableViewListener {

    @BindView(R.id.editor_fab)
    FloatingActionButton editorFab;
    @BindView(R.id.table_view)
    TableView tableView;

    private List<Integer> columnTypeList;

    private List<ColumnHeader> columnList;
    private List<RowHeader> rowList;
    List<List<Cell>> twoDimensionalCell;

    private DataFile file;
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
        fileViewModel = ViewModelProviders.of(this).get(FileEditViewModel.class);

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

        testJson();
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

    private void testJson() {

        if (getIntent().hasExtra(Constants.SELECTED_ITEM)) {

            SharedPrefs prefs = new SharedPrefs(this);
            String key = getIntent().getStringExtra(Constants.SELECTED_ITEM);

            if (key != null) {

                this.key = key;

                fileViewModel.setLiveData(prefs.getUid(), key);
                fileViewModel.getFile().observe(this, dataSnapshot -> {

                    if (dataSnapshot.hasChildren()) {

                        columnList = new ArrayList<>();
                        rowList = new ArrayList<>();
                        twoDimensionalCell = new ArrayList<>();
                        columnTypeList = new ArrayList<>();

                        file = dataSnapshot.getValue(DataFile.class);

                        if (file != null) {

                            rowCount = file.getFileData().size();

                            for (int i = 0; i < rowCount; i++) {

                                List<Cell> cellList = new ArrayList<>();

                                for (Map.Entry<String, String> entry : file.getFileData().get(i).entrySet()) {

                                    if (i == 0) {

                                        columnCount = file.getFileData().get(i).size();
                                        columnList.add(new ColumnHeader(String.valueOf(i), entry.getKey()));
                                    }

                                    cellList.add(new Cell(String.valueOf(i), entry.getValue()));
                                    columnTypeList.add(Constants.TEXT_COLUMN);
                                }

                                rowList.add(new RowHeader(String.valueOf(i + 1), String.valueOf(i + 1)));
                                twoDimensionalCell.add(cellList);

                                adapter.setColumnType(columnTypeList);
                                adapter.setAllItems(columnList, rowList, twoDimensionalCell);
                            }
                        }
                    }
                });
            }
        }
    }

    @OnClick(R.id.editor_fab)
    public void onViewClicked() {

        AlertDialog.Builder typeAlertBuilder = new AlertDialog.Builder(this)
                .setTitle("Select Type")
                .setItems(getResources().getStringArray(R.array.row_column_selection), (dialogInterface, i) -> {

                    if (i == 0) {

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

                    } else if (i == 1) {

                        ColumnDialog fragment = new ColumnDialog();

                        fragment.setListener(this);
                        fragment.show(getSupportFragmentManager(), "column");
                    }
                });

        typeAlertBuilder.create().show();
    }

    @Override
    public void onItemClicked(int position, String text) {

        columnList.add(new ColumnHeader(String.valueOf(++columnCount), text));
        columnTypeList.add(position);

        for (int i = 0; i < twoDimensionalCell.size(); i++) {

            twoDimensionalCell.get(i).add(new Cell(String.valueOf(twoDimensionalCell.size() + 1), ""));
            file.getFileData().get(i).put(text, "");
        }

        fileViewModel.update(file.getFileData(), file.getFileKey(), prefs.getUid());
        adapter.setColumnType(columnTypeList);
        adapter.setAllItems(columnList, rowList, twoDimensionalCell);
    }

    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder viewHolder, int column, int row) {

        if (file != null) {

            switch (columnTypeList.get(column)) {

                case Constants.TEXT_COLUMN:

                    AppCompatEditText editText = new AppCompatEditText(this);
                    editText.setHint("Text");

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
                    break;
            }

            Log.d("Column Type", String.valueOf(columnTypeList.get(column)));
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
