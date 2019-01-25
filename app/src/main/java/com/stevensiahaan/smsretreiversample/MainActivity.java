package com.stevensiahaan.smsretreiversample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editTextOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextOTP = findViewById(R.id.editTextOTP);

        requestPermission();

        SMSReceiver.bindListener(new CommonListener() {
            @Override
            public void onOTPReceived(String otp) {
                editTextOTP.setText(getOTPNumber(otp));
            }
        });

    }

    public String getOTPNumber(String input) {
        return input.replaceAll("[^0-9]", "");
    }

    @Override
    protected void onDestroy() {
        SMSReceiver.unbindListener();
        super.onDestroy();
    }

    private void requestPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS};
        if (!hasPermission(MainActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public boolean hasPermission(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null & permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
