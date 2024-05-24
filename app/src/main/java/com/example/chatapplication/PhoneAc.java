package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hbb20.CountryCodePicker;

public class PhoneAc extends AppCompatActivity {

    // Declare UI elements
    CountryCodePicker countryCodePicker;
    EditText phoneInput;
    Button sendOtpBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Set the content view to the appropriate layout
        setContentView(R.layout.activity_phone);

        // Initialize UI elements
        initializeUIElements();

        // Set initial visibility for the progress bar
        progressBar.setVisibility(View.GONE);

        // Register the EditText with the CountryCodePicker
        countryCodePicker.registerCarrierNumberEditText(phoneInput);

        // Set the click listener for the Send OTP button
        sendOtpBtn.setOnClickListener(this::onSendOtpClicked);

        // Handle window insets for edge-to-edge display
        handleWindowInsets();
    }

    // Method to initialize UI elements
    private void initializeUIElements() {
        countryCodePicker = findViewById(R.id.login_countrycode);
        phoneInput = findViewById(R.id.login_mobile_number);
        sendOtpBtn = findViewById(R.id.send_otp_btn);
        progressBar = findViewById(R.id.login_progress_bar);
    }

    // Method to handle Send OTP button click
    private void onSendOtpClicked(View view) {
        if (!countryCodePicker.isValidFullNumber()) {
            phoneInput.setError("Phone number not valid");
            return;
        }
        Intent intent = new Intent(PhoneAc.this, OTPnumberAc.class);
        intent.putExtra("phone", countryCodePicker.getFullNumberWithPlus());
        startActivity(intent);
    }

    // Method to handle window insets for edge-to-edge display
    private void handleWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
