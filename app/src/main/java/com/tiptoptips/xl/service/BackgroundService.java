package com.tiptoptips.xl.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tiptoptips.xl.database.FileDao;
import com.tiptoptips.xl.database.FileDatabase;
import com.tiptoptips.xl.model.SingleFile;
import com.tiptoptips.xl.model.UserFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BackgroundService extends Service {

    private FileDao dao;
    public static boolean isRunning = false;

    public BackgroundService() {

        this.dao = FileDatabase.getInstance(getBaseContext()).getFileDao();
    }

    private void downloadJson() {

        isRunning = true;

        Log.d("Service", "Running");

        try {

            List<SingleFile> notProcessedUrl = new ArrayList<>(new GetFileUrl(dao).execute().get());
            SingleFile[] files = new SingleFile[notProcessedUrl.size()];
            int i = 0;

            for (SingleFile file : notProcessedUrl) {

                String body = getJsonFromUrl(file.getFileUrl());
                file.setFileBody(body);

                files[i++] = file;
            }

            new InsertFileBody(dao).execute(files);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private String getJsonFromUrl(String data) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(data);
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

    @Override
    public void onCreate() {

        super.onCreate();

        if (!isRunning) {

            downloadJson();
        }
    }

    @Override
    public void onDestroy() {

        isRunning = false;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        downloadJson();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}

class GetFileUrl extends AsyncTask<Void, Void, List<SingleFile>> {

    private FileDao dao;

    GetFileUrl(FileDao dao) {

        this.dao = dao;
    }

    @Override
    protected List<SingleFile> doInBackground(Void... voids) {

        List<UserFile> userFiles = dao.getUserFiles();
        List<String> savedFileUrl = dao.getSavedFileUrl();
        List<SingleFile> notProcessedList = new ArrayList<>();

        for (UserFile f : userFiles) {

            if (!savedFileUrl.contains(f.getFileUrl())) {

                SingleFile file = new SingleFile();

                file.setFileName(f.getId());
                file.setFileUrl(f.getFileUrl());

                notProcessedList.add(file);
            }
        }

        return notProcessedList;
    }
}

class InsertFileBody extends AsyncTask<SingleFile, Void, Void> {

    private FileDao dao;

    InsertFileBody(FileDao dao) {

        this.dao = dao;
    }

    @Override
    protected Void doInBackground(SingleFile... singleFiles) {

        dao.insertFilesInfo(singleFiles);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);
        BackgroundService.isRunning = false;
    }
}
