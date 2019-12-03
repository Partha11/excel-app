package com.tiptoptips.xl.view.editor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.evrencoskun.tableview.TableView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.adapter.TableAdapter;
import com.tiptoptips.xl.dialog.ColumnDialog;
import com.tiptoptips.xl.listener.DialogPositionListener;
import com.tiptoptips.xl.listener.TableViewListener;
import com.tiptoptips.xl.model.Cell;
import com.tiptoptips.xl.model.ColumnHeader;
import com.tiptoptips.xl.model.RowHeader;
import com.tiptoptips.xl.viewmodel.TableViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileEditActivity extends AppCompatActivity implements DialogPositionListener {

    @BindView(R.id.editor_fab)
    FloatingActionButton editorFab;
    @BindView(R.id.table_view)
    TableView tableView;

    private int rowCount;

    private List<Integer> columnTypeList = new ArrayList<>();

    private List<ColumnHeader> columnHeaders;
    private List<RowHeader> rowHeaders;
    private List<List<Cell>> cells;

    private TableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);
        ButterKnife.bind(this);

        TableViewModel viewModel = new TableViewModel();
        adapter = new TableAdapter(this);

        adapter.setTableModel(viewModel);
        tableView.setAdapter(adapter);
        tableView.setTableViewListener(new TableViewListener(tableView));

        cells = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            List<Cell> tempList = new ArrayList<>();

            tempList.add(new Cell("1", "name 1"));
            tempList.add(new Cell("2", "name 2"));
            tempList.add(new Cell("3", "name 3"));

            cells.add(tempList);
        }

        columnHeaders = new ArrayList<>();

        columnHeaders.add(new ColumnHeader("1", "Student"));
        columnHeaders.add(new ColumnHeader("2", "classteach"));
        columnHeaders.add(new ColumnHeader("3", "crs"));

        rowHeaders = new ArrayList<>();

        rowHeaders.add(new RowHeader("1", "1"));
        rowHeaders.add(new RowHeader("2", "2"));
        rowHeaders.add(new RowHeader("3", "3"));
        rowHeaders.add(new RowHeader("4", "4"));

        adapter.setAllItems(columnHeaders, rowHeaders, cells);
    }

    private int getNewColumnType() {

        return 0;
    }

    @OnClick(R.id.editor_fab)
    public void onViewClicked() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Select Type")
                .setItems(getResources().getStringArray(R.array.row_column_selection), (dialogInterface, i) -> {

                    if (i == 0) {

                        final NumberPicker numberPicker = new NumberPicker(this);
                        numberPicker.setMaxValue(100);
                        numberPicker.setMinValue(0);

                        AlertDialog.Builder builderTwo = new AlertDialog.Builder(this)
                                .setView(numberPicker)
                                .setTitle("Insert Multiple Rows")
                                .setPositiveButton("Create", (dialog, which) -> {

                                    int count = numberPicker.getValue();

                                    for (int k = 0; k < count; k++) {

                                        List<Cell> tempList = new ArrayList<>();

                                        for (int j = 0; j < columnHeaders.size(); j++) {

                                            tempList.add(new Cell(String.valueOf(cells.size() + j + 1), ""));
                                        }

                                        rowHeaders.add(new RowHeader(String.valueOf(cells.size() + 1),
                                                String.valueOf(cells.size() + 1)));
                                        cells.add(tempList);
                                        adapter.setAllItems(columnHeaders, rowHeaders, cells);

                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                        builderTwo.create().show();

                    } else if (i == 1) {

                        ColumnDialog fragment = new ColumnDialog();

                        fragment.setListener(this);
                        fragment.show(getSupportFragmentManager(), "column");
                    }
                });

        builder.create().show();
    }

    @Override
    public void onItemClicked(int position, String text) {

        columnHeaders.add(new ColumnHeader(String.valueOf(columnHeaders.size() + 1), text));
        columnTypeList.add(position);

        for (List<Cell> cell : cells) {

            cell.add(new Cell(String.valueOf(cells.size() + 1), ""));
        }

        adapter.setAllItems(columnHeaders, rowHeaders, cells);
    }
}
