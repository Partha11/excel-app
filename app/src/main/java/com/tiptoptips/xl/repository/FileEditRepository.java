package com.tiptoptips.xl.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptoptips.xl.database.FileDao;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.utility.Converter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FileEditRepository {

    private FileDao fileDao;

    public FileEditRepository(FileDao fileDao) {

        this.fileDao = fileDao;
    }

    public MutableLiveData<String> getFileFromId(String id) {

        MutableLiveData<String> file = new MutableLiveData<>();

        try {

            file.setValue(new GetFile(fileDao).execute(id).get());

        } catch (Exception e) {

            e.printStackTrace();
        }

        return file;
    }

    public MutableLiveData<String> getJson(String fileUrl) {

        MutableLiveData<String> json = new MutableLiveData<>();

        try {

            json.setValue(new JsonTask().execute(fileUrl).get());

        } catch (Exception e) {

            e.printStackTrace();
        }

        return json;
    }

    public void update(List<HashMap<String, String>> fileData, String key, String uid) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("userFiles").child(key).child("fileData");
        reference.setValue(fileData).addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {

                Log.d("Failed", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getLocalizedMessage()));
            }
        });
    }
}

class GetFile extends AsyncTask<String, Void, String> {

    private FileDao dao;

    GetFile(FileDao dao) {

        this.dao = dao;
    }

    @Override
    protected String doInBackground(String... strings) {

        UserFile file = dao.getFileFromId(strings[0]);

        if (file != null) {

            return Converter.fromListToString(file.getConvertedFileBody());
        }

        return "";
    }
}

class JsonTask extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                builder.append(line);
                builder.append("\n");
            }

            return builder.toString();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (connection != null) {

                connection.disconnect();
            }

            try {

                if (reader != null) {

                    reader.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return null;
    }
}