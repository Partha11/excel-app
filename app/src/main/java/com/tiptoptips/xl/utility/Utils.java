package com.tiptoptips.xl.utility;

import android.text.TextUtils;

import com.tiptoptips.xl.model.Cell;
import com.tiptoptips.xl.model.ColumnHeader;
import com.tiptoptips.xl.model.RowHeader;
import com.tiptoptips.xl.model.TableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static boolean isValidEmail(String email) {

        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static TableModel parseJson(String json) {

        TableModel model = new TableModel();
        HashMap<String, HashMap<Long, HashMap<String, String>>> data = Converter.fromStringToMap(json);
        List<ColumnHeader> columnList = new ArrayList<>();
        List<RowHeader> rowList = new ArrayList<>();
        List<List<Cell>> cellList = new ArrayList<>();
        List<Integer> columnTypeList = new ArrayList<>();

        int c = 0;

        for (Map.Entry<String, HashMap<Long, HashMap<String, String>>> d : data.entrySet()) {

            for (Map.Entry<Long, HashMap<String, String>> e : d.getValue().entrySet()) {

                List<Cell> cells = new ArrayList<>();
                int j = 0;

                for (Map.Entry<String, String> f : e.getValue().entrySet()) {

                    if (c == 0) {

                        columnList.add(new ColumnHeader(String.valueOf(j + 1), String.valueOf(f.getKey())));

                        if (f.getValue().contains("http://") || f.getValue().contains("https://")) {

                            columnTypeList.add(Constants.IMAGE_COLUMN);

                        } else {

                            columnTypeList.add(Constants.TEXT_COLUMN);
                        }
                    }

                    if (j == 0) {

                        rowList.add(new RowHeader(String.valueOf(c + 1), String.valueOf(c + 1)));
                    }


                    cells.add(new Cell(String.valueOf(j + 1), f.getValue()));
                    j++;
                }

                cellList.add(cells);
                c++;
            }
        }

        model.setCellList(cellList);
        model.setRowList(rowList);
        model.setColumnList(columnList);
        model.setColumnTypeList(columnTypeList);

        return model;
    }

    public static List<Map<String, Object>> getStandardFileFormat() {

        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        map.put("Name", "");
        map.put("Phone Number", "");
        map.put("Address", "");
        map.put("Date", "");

        list.add(map);

        return list;
    }
}
