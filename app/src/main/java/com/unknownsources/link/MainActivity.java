package com.unknownsources.link;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "UnknownSourcesLink";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        openInstallUnknownAppsSettings();
        
        // Close the app after opening settings
        finish();
    }

    private void openInstallUnknownAppsSettings() {
        Intent intent = null;
        boolean success = false;
        
        // Strategy 1: Try to open Special App Access settings directly (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                // Try opening via Intent URI for Special App Access
                // This should open: Settings > Apps & notifications > Advanced > Special access > Install unknown apps
                intent = new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                success = true;
                Log.d(TAG, "Successfully opened via MANAGE_UNKNOWN_APP_SOURCES action");
            } catch (Exception e) {
                Log.w(TAG, "Failed to open via MANAGE_UNKNOWN_APP_SOURCES action", e);
            }
        }
        
        // Strategy 2: Try Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES (requires package, but try anyway)
        if (!success && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                success = true;
                Log.d(TAG, "Successfully opened via Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES");
            } catch (Exception e) {
                Log.w(TAG, "Failed to open via Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES", e);
            }
        }
        
        // Strategy 3: Open Application Settings (user can navigate to Special App Access)
        if (!success) {
            try {
                intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                success = true;
                Log.d(TAG, "Successfully opened Application Settings");
            } catch (Exception e) {
                Log.w(TAG, "Failed to open Application Settings", e);
            }
        }
        
        // Strategy 4: For older Android versions, try Security Settings
        if (!success && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            try {
                intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                success = true;
                Log.d(TAG, "Successfully opened Security Settings");
            } catch (Exception e) {
                Log.w(TAG, "Failed to open Security Settings", e);
            }
        }
        
        // Last resort: Open general Settings
        if (!success) {
            try {
                intent = new Intent(Settings.ACTION_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Log.d(TAG, "Opened general Settings as fallback");
            } catch (Exception e) {
                Log.e(TAG, "Failed to open any settings", e);
            }
        }
    }
}

