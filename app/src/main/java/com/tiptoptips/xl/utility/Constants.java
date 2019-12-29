package com.tiptoptips.xl.utility;

public interface Constants {

    String PREF_NAME = "user_data";
    String PREF_INSTALLED = "installed";
    String PREF_UID = "uid";
    String PREF_USER_EMAIL = "user_email";
    String PREF_FIRST_LAUNCH = "first_launch";

    String USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
    String USER_DISABLED = "ERROR_USER_DISABLED";

    String USER_EMAIL = "userEmail";

    String DATABASE_NAME = "user_files";
    int DATABASE_VERSION = 1;
    String FILES_TABLE = "files";
    String FILE_INFO = "file_info";
    String FIELD_FILE_ID = "file_id";
    String FIELD_FILE_NAME = "file_name";
    String FIELD_CREATION_DATE = "creation_date";
    String FIELD_FILE_URL = "file_url";
    String FIELD_SHARED_WITH = "shared_with";
    String FIELD_FILE_BODY = "file_body";

    String SELECTED_ITEM = "selected_item";

    int MOOD_CELL_TYPE = 1;
    int GENDER_CELL_TYPE = 2;

    int TEXT_COLUMN = 0;
    int IMAGE_COLUMN = 1;
    int NUMBER_COLUMN = 2;
    int PHONE_COLUMN = 3;
    int DATE_COLUMN = 4;
    int LOCATION_COLUMN = 5;
    int AMOUNT_COLUMN = 6;

    String TEMPLATE_ID = "template_id";
    int TEMPLATE_STANDARD = 0;
    int TEMPLATE_TO_DO = 1;
    int TEMPLATE_EXPENSE = 2;
    int TEMPLATE_CATALOGUE = 3;
    int TEMPLATE_CONTACTS = 4;
    int TEMPLATE_MEDICAL = 5;
    int TEMPLATE_GROCERY = 6;
}
