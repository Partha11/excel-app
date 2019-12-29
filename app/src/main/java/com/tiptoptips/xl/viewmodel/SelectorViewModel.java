package com.tiptoptips.xl.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tiptoptips.xl.model.DataFile;
import com.tiptoptips.xl.repository.SelectorRepository;

public class SelectorViewModel extends AndroidViewModel {

    private SelectorRepository repository;

    public SelectorViewModel(@NonNull Application application) {

        super(application);
        repository = new SelectorRepository();
    }

    public LiveData<String> insertFile(String uid, DataFile file) {

        return repository.insertFile(uid, file);
    }
}
