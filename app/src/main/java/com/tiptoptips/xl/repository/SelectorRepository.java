package com.tiptoptips.xl.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptoptips.xl.model.UserFile;

public class SelectorRepository {

    public MutableLiveData<String> insertFile(String uid, UserFile file) {

        MutableLiveData<String> data = new MutableLiveData<>();
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference().child("users")
                .child(uid).child("userFiles");
        String key = FirebaseDatabase.getInstance().getReference().push().getKey();

        if (key != null) {

            file.setFileKey(key);
            reference.child(key).setValue(file).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    data.setValue(key);

                } else {

                    data.setValue(null);
                }
            });

        } else {

            data.setValue(null);
        }

        return data;
    }
}
