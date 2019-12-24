package com.tiptoptips.xl.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptoptips.xl.database.FileDatabase;
import com.tiptoptips.xl.repository.FileEditRepository;
import com.tiptoptips.xl.repository.FirebaseQueryLiveData;

import java.util.HashMap;
import java.util.List;

public class FileEditViewModel extends AndroidViewModel {

    private FileEditRepository repository;
    private FirebaseQueryLiveData liveData;

    public FileEditViewModel(@NonNull Application application) {

        super(application);
        repository = new FileEditRepository(FileDatabase.getInstance(application).getFileDao());
    }

    public void setLiveData(String uid, String key) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("userFiles").child(key);
        liveData = new FirebaseQueryLiveData(reference);
    }

    public LiveData<DataSnapshot> getFile() {

        return liveData;
    }

    public LiveData<String> getFileFromId(String id) {

        return repository.getFileFromId(id);
    }

    public LiveData<String> getJson(String fileUrl) {

        return repository.getJson(fileUrl);
    }

    public void update(List<HashMap<String, String>> fileData, String key, String uid) {

        repository.update(fileData, key, uid);
    }
}
