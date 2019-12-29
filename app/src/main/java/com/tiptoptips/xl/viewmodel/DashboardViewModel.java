package com.tiptoptips.xl.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptoptips.xl.repository.DashboardRepository;
import com.tiptoptips.xl.repository.FirebaseQueryLiveData;

public class DashboardViewModel extends AndroidViewModel {

    private DashboardRepository repository;
    private FirebaseQueryLiveData liveData;

    public DashboardViewModel(@NonNull Application application) {

        super(application);
        repository = new DashboardRepository();
    }

    public void setLiveData(String uid) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("userFiles");
        liveData = new FirebaseQueryLiveData(reference);
    }

    public LiveData<DataSnapshot> getUserFiles() {

        return liveData;
    }
}
