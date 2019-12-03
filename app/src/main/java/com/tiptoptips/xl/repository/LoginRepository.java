package com.tiptoptips.xl.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptoptips.xl.model.Status;
import com.tiptoptips.xl.utility.Constants;

import java.util.HashMap;
import java.util.Map;

public class LoginRepository {

    public MutableLiveData<Status> loginUser(String email, String password) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        MutableLiveData<Status> isSuccess = new MutableLiveData<>();
        Status status = new Status();

        status.setSuccess(false);
        status.setUserId("");
        status.setErrorMessage("Connection error");

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        FirebaseUser user = auth.getCurrentUser();

                        if (user != null) {

                            status.setSuccess(true);
                            status.setUserId(user.getUid());
                            status.setErrorMessage("");

                            isSuccess.setValue(status);

                        } else {

                            status.setUserId("");
                            status.setSuccess(false);
                            status.setErrorMessage("Something went wrong");
                            isSuccess.setValue(status);
                        }
                    }

                }).addOnFailureListener(e -> {

            status.setUserId("");
            status.setSuccess(false);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {

                status.setErrorMessage("Invalid email or password");

            } else if (e instanceof FirebaseAuthInvalidUserException) {

                if (((FirebaseAuthInvalidUserException) e).getErrorCode().equals(Constants.USER_NOT_FOUND)) {

                    status.setErrorMessage("No matching account found");

                } else if (((FirebaseAuthInvalidUserException) e).getErrorCode().equals(Constants.USER_DISABLED)) {

                    status.setErrorMessage("User account has been disabled");

                } else {

                    status.setErrorMessage(e.getLocalizedMessage());                            }

            } else {

                status.setErrorMessage(e.getLocalizedMessage());
            }

            isSuccess.setValue(status);
        });

        return isSuccess;
    }

    public MutableLiveData<Status> createUser(String email, String password) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        MutableLiveData<Status> isSuccess = new MutableLiveData<>();
        Status status = new Status();

        status.setSuccess(false);
        status.setUserId("");
        status.setErrorMessage("Connection error");

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        FirebaseUser user = auth.getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                        status.setSuccess(true);
                        status.setErrorMessage("");

                        if (user != null) {

                            Map<String, String> data = new HashMap<>();

                            data.put(Constants.USER_EMAIL, email);

                            status.setUserId(user.getUid());
                            reference.child(user.getUid()).setValue(data);
                        }

                    } else {

                        status.setSuccess(false);
                        status.setUserId("");
                        status.setErrorMessage("Something went wrong");
                    }

                    isSuccess.setValue(status);

                }).addOnFailureListener(e -> {

            status.setUserId("");
            status.setSuccess(false);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {

                status.setErrorMessage("Invalid email or password");

            } else if (e instanceof FirebaseAuthInvalidUserException) {

                if (((FirebaseAuthInvalidUserException) e).getErrorCode().equals(Constants.USER_NOT_FOUND)) {

                    status.setErrorMessage("No matching account found");

                } else if (((FirebaseAuthInvalidUserException) e).getErrorCode().equals(Constants.USER_DISABLED)) {

                    status.setErrorMessage("User account has been disabled");

                } else {

                    status.setErrorMessage(e.getLocalizedMessage());
                }

            } else {

                status.setErrorMessage(e.getLocalizedMessage());
            }

            isSuccess.setValue(status);
        });

        return isSuccess;
    }
}
