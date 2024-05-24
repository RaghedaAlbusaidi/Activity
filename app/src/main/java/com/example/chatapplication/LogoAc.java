package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatapplication.model.UserModel;
import com.example.chatapplication.utils.AndroidUtil;
import com.example.chatapplication.utils.FirebaseUtil;

public class LogoAc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge display
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logo);

        // Check if the activity was launched from a notification
        if (getIntent().getExtras() != null) {
            // Extract userId from the intent's extras
            String userId = getIntent().getExtras().getString("userId");
            // Fetch the user details from Firestore
            FirebaseUtil.allUserCollectionReference().document(userId).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            UserModel model = task.getResult().toObject(UserModel.class);

                            // Launch the main activity without animation
                            Intent mainIntent = new Intent(this, MainActivity.class);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(mainIntent);

                            // Launch the chat activity with the user model data
                            Intent intent = new Intent(this, ChatAc.class);
                            AndroidUtil.passUserModelAsIntent(intent, model);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });

        } else {
            // If not launched from a notification, wait for 1 second and then decide the next activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Check if the user is logged in
                    if (FirebaseUtil.isLoggedIn()) {
                        // Start the main activity if the user is logged in
                        startActivity(new Intent(LogoAc.this, MainActivity.class));
                    } else {
                        // Start the phone activity if the user is not logged in
                        startActivity(new Intent(LogoAc.this, PhoneAc.class));
                    }
                    // Finish the current activity
                    finish();
                }
            }, 1000); // Delay of 1 second
        }
    }
}
