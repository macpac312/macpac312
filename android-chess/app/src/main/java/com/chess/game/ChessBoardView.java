package com.chess.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

public class ChessBoardView extends View {
    private ChessBoard chessBoard;
    private Paint paint;
    private Paint textPaint;
    private float squareSize;
    private OnTurnChangeListener turnChangeListener;

    public interface OnTurnChangeListener {
        void onTurnChanged(ChessPiece.Color currentTurn);
    }

    public ChessBoardView(Context context) {
        super(context);
        init();
    }

    public ChessBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        chessBoard = new ChessBoard();
        paint = new Paint();
        paint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setOnTurnChangeListener(OnTurnChangeListener listener) {
        this.turnChangeListener = listener;
    }

    public void resetGame() {
        chessBoard.reset();
        if (turnChangeListener != null) {
            turnChangeListener.onTurnChanged(chessBoard.getCurrentTurn());
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        squareSize = getWidth() / 8f;

        // Draw board squares
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                drawSquare(canvas, row, col);
            }
        }

        // Draw pieces
        textPaint.setTextSize(squareSize * 0.8f);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = chessBoard.getPiece(row, col);
                if (piece != null) {
                    drawPiece(canvas, piece, row, col);
                }
            }
        }

        // Draw valid moves
        for (Position pos : chessBoard.getValidMoves()) {
            drawValidMoveIndicator(canvas, pos.row, pos.col);
        }
    }

    private void drawSquare(Canvas canvas, int row, int col) {
        boolean isLight = (row + col) % 2 == 0;
        Position selected = chessBoard.getSelectedPiece();

        if (selected != null && selected.row == row && selected.col == col) {
            paint.setColor(ContextCompat.getColor(getContext(), R.color.selected_square));
        } else {
            paint.setColor(ContextCompat.getColor(getContext(),
                isLight ? R.color.light_square : R.color.dark_square));
        }

        float left = col * squareSize;
        float top = row * squareSize;
        canvas.drawRect(left, top, left + squareSize, top + squareSize, paint);
    }

    private void drawPiece(Canvas canvas, ChessPiece piece, int row, int col) {
        String symbol = piece.getSymbol();

        textPaint.setColor(piece.getColor() == ChessPiece.Color.WHITE ?
            ContextCompat.getColor(getContext(), R.color.white) :
            ContextCompat.getColor(getContext(), R.color.black));

        // Add shadow for white pieces
        if (piece.getColor() == ChessPiece.Color.WHITE) {
            textPaint.setShadowLayer(3, 0, 0, ContextCompat.getColor(getContext(), R.color.black));
        } else {
            textPaint.clearShadowLayer();
        }

        float x = col * squareSize + squareSize / 2;
        float y = row * squareSize + squareSize / 2 + textPaint.getTextSize() / 3;

        canvas.drawText(symbol, x, y, textPaint);
    }

    private void drawValidMoveIndicator(Canvas canvas, int row, int col) {
        paint.setColor(ContextCompat.getColor(getContext(), R.color.valid_move));

        float centerX = col * squareSize + squareSize / 2;
        float centerY = row * squareSize + squareSize / 2;
        float radius = squareSize / 6;

        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int col = (int) (event.getX() / squareSize);
            int row = (int) (event.getY() / squareSize);

            if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                handleSquareTouch(row, col);
            }

            return true;
        }
        return super.onTouchEvent(event);
    }

    private void handleSquareTouch(int row, int col) {
        Position selected = chessBoard.getSelectedPiece();

        if (selected == null) {
            // Select a piece
            chessBoard.selectPiece(row, col);
        } else {
            // Try to move the selected piece
            boolean moved = chessBoard.movePiece(row, col);

            if (!moved) {
                // If move failed, try selecting a new piece
                chessBoard.selectPiece(row, col);
            } else if (turnChangeListener != null) {
                turnChangeListener.onTurnChanged(chessBoard.getCurrentTurn());
            }
        }

        invalidate();
    }
}
