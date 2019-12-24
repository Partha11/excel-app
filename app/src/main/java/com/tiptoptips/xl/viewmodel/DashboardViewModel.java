package com.tiptoptips.xl.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptoptips.xl.database.FileDao;
import com.tiptoptips.xl.database.FileDatabase;
import com.tiptoptips.xl.model.DataFile;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.repository.DashboardRepository;
import com.tiptoptips.xl.repository.FirebaseQueryLiveData;
import com.tiptoptips.xl.utility.SharedPrefs;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private DashboardRepository repository;
    private FirebaseQueryLiveData liveData;

    public DashboardViewModel(@NonNull Application application) {

        super(application);

        FileDao fileDao = FileDatabase.getInstance(application).getFileDao();
        SharedPrefs prefs = new SharedPrefs(application);

        repository = new DashboardRepository(fileDao, prefs);
    }

    public void setLiveData(String uid) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("userFiles");
        liveData = new FirebaseQueryLiveData(reference);
    }

    public LiveData<List<UserFile>> getUserFiles(String uid) {

        return repository.getFileList(uid);
    }

    public LiveData<DataSnapshot> getUserFiles() {

        return liveData;
    }

    public LiveData<String> insertFile(String uid, DataFile file) {

        return repository.insertFile(uid, file);
    }
}
