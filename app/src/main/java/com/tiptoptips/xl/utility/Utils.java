package com.tiptoptips.xl.utility;

import android.content.Context;
import android.text.TextUtils;

import com.tiptoptips.xl.R;
import com.tiptoptips.xl.model.Template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Utils {

    private List<String> columnTypes;

    public Utils(Context context) {

        columnTypes = Arrays.asList(context.getResources().getStringArray(R.array.column_options));
    }

    public String getColumnName(int position) {

        return columnTypes.get(position);
    }

    public static boolean isValidEmail(String email) {

        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static Template getTemplate(int type) {

        switch (type) {

            case Constants.TEMPLATE_STANDARD:
                return getStandardFileFormat();
            default:
                return getStandardFileFormat();
        }
    }

    private static Template getStandardFileFormat() {

        Template template = new Template();
        HashMap<String, String> map = new HashMap<>();
        List<String> columnNames = new ArrayList<>();
        List<Integer> columnTypes = new ArrayList<>();
        List<HashMap<String, String>> cells = new ArrayList<>();

        map.put("Field", "");

        cells.add(map);
        columnTypes.add(Constants.TEXT_COLUMN);
        columnNames.add("Field");

        template.setCells(cells);
        template.setColumnNames(columnNames);
        template.setColumnTypes(columnTypes);

        return template;
    }
}
