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
    String FIELD_FILE_ID = "file_id";
    String FIELD_FILENAME = "file_name";
    String FIELD_CREATION_DATE = "creation_date";
    String FIELD_FILE_URL = "file_url";
    String FIELD_SHARED_WITH = "shared_with";

    String SELECTED_ITEM_ID = "selected_id";

    int MOOD_CELL_TYPE = 1;
    int GENDER_CELL_TYPE = 2;

    int TEXT_COLUMN = 0;
    int IMAGE_COLUMN = 1;
    int NUMBER_COLUMN = 2;
    int PHONE_COLUMN = 3;
    int DATE_COLUMN = 4;
    int LOCATION_COLUMN = 5;
    int AMOUNT_COLUMN = 6;
}
