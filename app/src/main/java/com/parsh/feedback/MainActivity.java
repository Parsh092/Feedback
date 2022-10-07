package com.parsh.feedback;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.parsh.feedback.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ActivityMainBinding binding;
    Uri selectedImage;
    ImageView imageView;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        selectedImage = data.getData();
//                        imageView.;
                        imageView.setImageURI(selectedImage);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Firebase firebase;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        Firebase.setAndroidContext(this);
        String UniqueId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        firebase = new Firebase("https://console.firebase.google.com/u/0/project/feedback-a7243/database/feedback-a7243-default-rtdb/data/~2F" + UniqueId);
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnDetails.setEnabled(true);
                final String name = binding.namedata.getText().toString();
                final String email = binding.emaildata.getText().toString();
                final String message = binding.messagedata.getText().toString();


                Firebase child_name = firebase.child("Name");
                child_name.setValue(name);

                if (name.isEmpty()) {
                    binding.namedata.setError("This is required");
                    binding.send.setEnabled(false);
                } else {
                    binding.namedata.setError(null);
                    binding.send.setEnabled(true);
                }

                Firebase child_email = firebase.child("Email");
                child_email.setValue(email);

                if (email.isEmpty()) {
                    binding.emaildata.setError("This is required");
                    binding.send.setEnabled(false);
                } else {
                    binding.emaildata.setError(null);
                    binding.send.setEnabled(true);
                }

                Firebase child_message = firebase.child("Message");
                child_message.setValue(message);

                if (message.isEmpty()) {
                    binding.messagedata.setError("This is required");
                    binding.send.setEnabled(false);
                } else {
                    binding.messagedata.setError(null);
                    binding.send.setEnabled(true);
                }
                Toast.makeText(MainActivity.this, "Thanks For Your Feedback", Toast.LENGTH_SHORT).show();
                binding.btnDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(MainActivity.this).setTitle("Sended Details").
                                setMessage("Name - " + name + "\n\nEmail - " + email + "\n\nFeedback - " + message).show();
                    }
                });

            }
        });
    }
}