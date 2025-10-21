package com.chess.game;

public class ChessPiece {
    public enum Type {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
    }

    public enum Color {
        WHITE, BLACK
    }

    private Type type;
    private Color color;
    private boolean hasMoved;

    public ChessPiece(Type type, Color color) {
        this.type = type;
        this.color = color;
        this.hasMoved = false;
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }

    public String getSymbol() {
        String symbol = "";
        switch (type) {
            case KING:   symbol = "♔♚"; break;
            case QUEEN:  symbol = "♕♛"; break;
            case ROOK:   symbol = "♖♜"; break;
            case BISHOP: symbol = "♗♝"; break;
            case KNIGHT: symbol = "♘♞"; break;
            case PAWN:   symbol = "♙♟"; break;
        }
        return color == Color.WHITE ? symbol.substring(0, 1) : symbol.substring(1, 2);
    }
}
