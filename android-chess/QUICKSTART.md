# Quick Start Guide

## Option 1: Using Android Studio (Recommended)

1. **Install Android Studio** from https://developer.android.com/studio
2. **Open the project**:
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Navigate to and select the `android-chess` folder
   - Wait for Gradle sync to complete

3. **Run on your device**:
   - Enable Developer Options on your Android phone:
     - Go to Settings → About Phone
     - Tap "Build Number" 7 times
     - Go back to Settings → Developer Options
     - Enable "USB Debugging"
   - Connect your phone via USB
   - Click the green "Run" button in Android Studio
   - Select your device from the list

## Option 2: Build APK and Install Manually

1. **Build the APK**:
   ```bash
   cd android-chess
   ./gradlew assembleDebug
   ```

2. **Find the APK**:
   - The APK will be at: `app/build/outputs/apk/debug/app-debug.apk`

3. **Install on your phone**:
   - Transfer the APK to your phone
   - Open the APK file on your phone
   - Allow installation from unknown sources if prompted
   - Install the app

## Option 3: Install via ADB

1. **Build the APK**:
   ```bash
   cd android-chess
   ./gradlew assembleDebug
   ```

2. **Install with ADB**:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## Troubleshooting

### Gradle sync fails
- Make sure you have Java 8 or higher installed
- Check your internet connection (Gradle needs to download dependencies)

### Device not detected
- Make sure USB debugging is enabled
- Try a different USB cable
- Install your phone manufacturer's USB drivers

### Build errors
- Update Android Studio to the latest version
- File → Invalidate Caches and Restart
- Delete `.gradle` folder and sync again

## How to Play

Once installed:
1. Tap a piece to select it (valid moves show as green circles)
2. Tap a highlighted square to move
3. Take turns - the app shows whose turn it is at the top
4. Use "NEW GAME" button to restart

Enjoy your chess game!
