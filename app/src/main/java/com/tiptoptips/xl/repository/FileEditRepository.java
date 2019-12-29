package com.tiptoptips.xl.repository;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FileEditRepository {

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