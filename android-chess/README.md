# Android Chess Game

A simple 2D chess game for Android with a clean, intuitive interface.

## Features

- Full chess board with all standard pieces
- Valid move highlighting
- Turn-based gameplay
- Chess piece movement rules for:
  - Pawns (including double move from starting position)
  - Rooks (straight line movement)
  - Knights (L-shaped movement)
  - Bishops (diagonal movement)
  - Queens (combination of rook and bishop)
  - Kings (one square in any direction)
- Visual feedback for selected pieces
- New game button to reset the board

## Requirements

- Android Studio (Arctic Fox or newer)
- Android SDK API Level 24 or higher
- Gradle 8.1.0 or higher
- Java 8 or higher

## How to Build

### Using Android Studio:

1. Open Android Studio
2. Click on "File" → "Open"
3. Navigate to the `android-chess` directory
4. Click "OK" and wait for Gradle to sync
5. Click "Build" → "Make Project"
6. Connect your Android device or start an emulator
7. Click "Run" → "Run 'app'"

### Using Command Line:

```bash
cd android-chess

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Or build and install in one step
./gradlew build
adb install app/build/outputs/apk/debug/app-debug.apk
```

Note: On Windows, use `gradlew.bat` instead of `./gradlew`

## How to Play

1. Launch the app on your Android device
2. The game starts with White's turn
3. Tap on a piece to select it (valid moves will be highlighted with green circles)
4. Tap on a highlighted square to move the piece
5. The turn automatically switches to the other player
6. Use the "NEW GAME" button to reset the board

## Game Controls

- **Tap a piece**: Select the piece (shows valid moves)
- **Tap a valid square**: Move the selected piece
- **Tap another piece**: Deselect current and select the new piece
- **NEW GAME button**: Reset the board to starting position

## Project Structure

```
android-chess/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/chess/game/
│   │       │   ├── MainActivity.java       # Main activity
│   │       │   ├── ChessBoardView.java    # Custom view for chess board
│   │       │   ├── ChessBoard.java        # Game logic and state
│   │       │   ├── ChessPiece.java        # Chess piece model
│   │       │   └── Position.java          # Board position helper
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml  # Main layout
│   │       │   ├── values/
│   │       │   │   ├── strings.xml        # String resources
│   │       │   │   └── colors.xml         # Color definitions
│   │       │   └── drawable/              # Icon resources
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Technical Details

- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Language**: Java
- **UI**: Custom View with Canvas drawing
- **Architecture**: Model-View pattern

## Known Limitations

This is a simple implementation with the following limitations:

- No check/checkmate detection
- No castling
- No en passant
- No pawn promotion
- No move history
- No AI opponent (2-player only on same device)
- No game save/load functionality

## Future Enhancements

Potential improvements for future versions:

- Add check and checkmate detection
- Implement special moves (castling, en passant, pawn promotion)
- Add move history and undo functionality
- Implement AI opponent
- Add different difficulty levels
- Include game timer
- Add sound effects
- Support for game save/load
- Online multiplayer

## License

This is a simple educational project created for learning Android development.

## Screenshots

The game features:
- Classic chess board with alternating light and dark squares
- Unicode chess piece symbols for clear visualization
- Yellow highlighting for selected pieces
- Green circles indicating valid move positions
- Turn indicator showing current player
- Clean, dark-themed interface

Enjoy playing chess on your Android device!
