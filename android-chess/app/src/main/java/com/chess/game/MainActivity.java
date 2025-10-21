package com.chess.game;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ChessBoardView chessBoardView;
    private TextView turnIndicator;
    private Button newGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chessBoardView = findViewById(R.id.chessBoard);
        turnIndicator = findViewById(R.id.turnIndicator);
        newGameButton = findViewById(R.id.newGameButton);

        chessBoardView.setOnTurnChangeListener(new ChessBoardView.OnTurnChangeListener() {
            @Override
            public void onTurnChanged(ChessPiece.Color currentTurn) {
                updateTurnIndicator(currentTurn);
            }
        });

        newGameButton.setOnClickListener(v -> {
            chessBoardView.resetGame();
            updateTurnIndicator(ChessPiece.Color.WHITE);
        });

        updateTurnIndicator(ChessPiece.Color.WHITE);
    }

    private void updateTurnIndicator(ChessPiece.Color currentTurn) {
        if (currentTurn == ChessPiece.Color.WHITE) {
            turnIndicator.setText(R.string.white_turn);
        } else {
            turnIndicator.setText(R.string.black_turn);
        }
    }
}
