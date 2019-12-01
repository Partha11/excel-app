package com.tiptoptips.xl.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tiptoptips.xl.model.Status;
import com.tiptoptips.xl.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository repository;

    public LoginViewModel(@NonNull Application application) {

        super(application);
        this.repository = new LoginRepository();
    }

    public LiveData<Status> loginUser(String email, String password) {

        return repository.loginUser(email, password);
    }

    public LiveData<Status> registerUser(String email, String password) {

        return repository.createUser(email, password);
    }
}
