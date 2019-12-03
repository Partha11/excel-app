package com.tiptoptips.xl.repository;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiptoptips.xl.database.FileDao;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.utility.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class DashboardRepository {

    private FileDao fileDao;
    private SharedPrefs prefs;

    public DashboardRepository(FileDao fileDao, SharedPrefs prefs) {

        this.fileDao = fileDao;
        this.prefs = prefs;
    }

    public MutableLiveData<List<UserFile>> getFileList(String uid) {

        MutableLiveData<List<UserFile>> fileList = new MutableLiveData<>();
        List<UserFile> files = new ArrayList<>();

        if (prefs.getFirstLaunch()) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users")
                    .child(uid);

            reference.child("userFiles").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        UserFile file = d.getValue(UserFile.class);

                        if (file != null) {

                            files.add(file);
                        }
                    }

                    UserFile[] filesArray = new UserFile[files.size()];

                    for (int i = 0; i < files.size(); i++) {

                        filesArray[i] = files.get(i);
                    }

                    new InsertFiles(fileDao).execute(filesArray);
                    prefs.setFirstLaunch(false);
                    fileList.setValue(files);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    prefs.setFirstLaunch(false);
                    fileList.setValue(new ArrayList<>());
                }
            });

        } else {

            try {

                fileList.setValue(new LoadFiles(fileDao).execute().get());

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return fileList;
    }
}

class InsertFiles extends AsyncTask<UserFile, Void, Void> {

    private FileDao fileDao;

    InsertFiles(FileDao fileDao) {

        this.fileDao = fileDao;
    }

    @Override
    protected Void doInBackground(UserFile... userFiles) {

        fileDao.insertFiles(userFiles);
        return null;
    }
}

class LoadFiles extends AsyncTask<Void, Void, List<UserFile>> {

    private FileDao fileDao;

    LoadFiles(FileDao fileDao) {

        this.fileDao = fileDao;
    }


    @Override
    protected List<UserFile> doInBackground(Void... voids) {

        return fileDao.getUserFiles();
    }
}
