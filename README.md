# Unknown Sources Link

A shortcut app for Android TV OS that opens the "Special App Access" -> "Install unknown apps" settings page.

## Problem

On some Android TV devices (e.g., 3rd Gen MI Box), the "Install unknown apps" option is missing from the default settings menu. However, this setting is crucial for security, as:

- Other apps can enable this option
- Users might accidentally grant permissions to an app
- It may be impossible to revoke permissions without access to this setting

## Solution

This app creates a shortcut on the Android TV home screen that directly opens the "Install unknown apps" settings page, allowing users to manage permissions for installing unknown applications.

## Building

### Requirements

- Android Studio (Arctic Fox or newer)
- Android SDK (API 23+)
- Gradle 8.0+

### Instructions

1. Open the project in Android Studio
2. Wait for Gradle to sync dependencies
3. Connect an Android TV device or create an Android TV emulator
4. To build the application, run:
   ```bash
   ./gradlew build
   ```
5. To install on a connected device, run:
   ```bash
   ./gradlew installDebug
   ```

## Installation

After building, the APK file will be located at `app/build/outputs/apk/debug/app-debug.apk`

To install on Android TV:

1. Copy the APK to a USB drive
2. Connect the USB to Android TV
3. Use a file manager to install the APK
4. Or use ADB:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## Usage

After installation, the app will appear in the app list on Android TV. When launched, it displays a main screen with:
- App description and purpose
- Device information
- A button to open "Install unknown apps" settings
- Links to Reddit discussion and developer GitHub

The app provides a convenient way to access the hidden "Install unknown apps" settings that are not easily accessible on some Android TV devices.

## Technical Details

The app uses Intent `Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES` to open the settings. If this Intent is unavailable, the app attempts to open alternative settings pages using multiple fallback strategies.

## License

This app was created to solve the problem of accessing hidden security settings on Android TV devices.
