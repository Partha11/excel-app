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
            case Constants.TEMPLATE_CATALOGUE:
                return getCatalogueFormat();
            default:
                return new Template();
        }
    }

    private static Template getCatalogueFormat() {

        Template template = new Template();
        HashMap<String, String> map = new HashMap<>();
        List<String> columnNames = new ArrayList<>();
        List<Integer> columnTypes = new ArrayList<>();
        List<HashMap<String, String>> cells = new ArrayList<>();

        map.put("aa__Product Name", "");
        map.put("ab__Product Description", "");
        map.put("ac__Product Image", "");
        map.put("ad__Product Size", "");
        map.put("ae__Product Color", "");
        map.put("af__Product Code", "");
        map.put("ag__Product Category", "");
        map.put("ah__Product MRP", "");
        map.put("ai__Offer Price", "");
        map.put("aj__Product Availability", "");
        map.put("ak__Delivery Date", "");

        cells.add(map);
        columnTypes.add(Constants.TEXT_COLUMN);
        columnTypes.add(Constants.TEXT_COLUMN);
        columnTypes.add(Constants.IMAGE_COLUMN);
        columnTypes.add(Constants.LIST_COLUMN);
        columnTypes.add(Constants.LIST_COLUMN);
        columnTypes.add(Constants.NUMBER_COLUMN);
        columnTypes.add(Constants.LIST_COLUMN);
        columnTypes.add(Constants.PRICE_COLUMN);
        columnTypes.add(Constants.PRICE_COLUMN);
        columnTypes.add(Constants.CHECKBOX_COLUMN);
        columnTypes.add(Constants.DATE_COLUMN);

        columnNames.add("aa__Product Name");
        columnNames.add("ab__Product Description");
        columnNames.add("ac__Product Image");
        columnNames.add("ad__Product Size");
        columnNames.add("ae__Product Color");
        columnNames.add("af__Product Code");
        columnNames.add("ag__Product Category");
        columnNames.add("ah__Product MRP");
        columnNames.add("ai__Offer Price");
        columnNames.add("aj__Product Availability");
        columnNames.add("ak__Delivery Date");

        template.setCells(cells);
        template.setColumnNames(columnNames);
        template.setColumnTypes(columnTypes);

        return template;
    }

    private static Template getStandardFileFormat() {

        Template template = new Template();
        HashMap<String, String> map = new HashMap<>();
        List<String> columnNames = new ArrayList<>();
        List<Integer> columnTypes = new ArrayList<>();
        List<HashMap<String, String>> cells = new ArrayList<>();

        map.put("aa__Field", "");

        cells.add(map);
        columnTypes.add(Constants.TEXT_COLUMN);
        columnNames.add("aa__Field");

        template.setCells(cells);
        template.setColumnNames(columnNames);
        template.setColumnTypes(columnTypes);

        return template;
    }
}
