package im.decker.unknown.sources.link;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "UnknownSourcesLink";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Setup device info
        setupDeviceInfo();
        
        // Setup button
        Button openSettingsButton = findViewById(R.id.open_settings_button);
        openSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstallUnknownAppsSettings();
            }
        });
        
        // Make button focusable for TV remote
        openSettingsButton.requestFocus();
    }

    private void setupDeviceInfo() {
        TextView deviceInfoText = findViewById(R.id.device_info_text);
        
        StringBuilder deviceInfo = new StringBuilder();
        deviceInfo.append("Device Information:\n\n");
        
        // Android Version
        deviceInfo.append("Android Version: ").append(Build.VERSION.RELEASE).append("\n");
        deviceInfo.append("SDK Level: ").append(Build.VERSION.SDK_INT).append("\n");
        
        // Device Model
        deviceInfo.append("Device Model: ").append(Build.MODEL).append("\n");
        deviceInfo.append("Manufacturer: ").append(Build.MANUFACTURER).append("\n");
        deviceInfo.append("Brand: ").append(Build.BRAND).append("\n");
        
        // Device Info
        if (Build.DEVICE != null) {
            deviceInfo.append("Device: ").append(Build.DEVICE).append("\n");
        }
        if (Build.PRODUCT != null) {
            deviceInfo.append("Product: ").append(Build.PRODUCT).append("\n");
        }
        if (Build.HARDWARE != null) {
            deviceInfo.append("Hardware: ").append(Build.HARDWARE).append("\n");
        }
        
        // Build Info
        if (Build.ID != null) {
            deviceInfo.append("Build ID: ").append(Build.ID).append("\n");
        }
        if (Build.FINGERPRINT != null) {
            deviceInfo.append("Fingerprint: ").append(Build.FINGERPRINT).append("\n");
        }
        
        deviceInfoText.setText(deviceInfo.toString());
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
