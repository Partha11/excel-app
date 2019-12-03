package com.tiptoptips.xl.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tiptoptips.xl.database.FileDao;
import com.tiptoptips.xl.database.FileDatabase;
import com.tiptoptips.xl.model.UserFile;
import com.tiptoptips.xl.repository.DashboardRepository;
import com.tiptoptips.xl.utility.SharedPrefs;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private DashboardRepository repository;

    public DashboardViewModel(@NonNull Application application) {

        super(application);

        FileDao fileDao = FileDatabase.getInstance(application).getFileDao();
        SharedPrefs prefs = new SharedPrefs(application);

        repository = new DashboardRepository(fileDao, prefs);
    }

    public LiveData<List<UserFile>> getUserFiles(String uid) {

        return repository.getFileList(uid);
    }
}
