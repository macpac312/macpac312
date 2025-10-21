package com.chess.game;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private ChessPiece[][] board;
    private ChessPiece.Color currentTurn;
    private Position selectedPiece;
    private List<Position> validMoves;

    public ChessBoard() {
        board = new ChessPiece[8][8];
        currentTurn = ChessPiece.Color.WHITE;
        selectedPiece = null;
        validMoves = new ArrayList<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Place pawns
        for (int col = 0; col < 8; col++) {
            board[1][col] = new ChessPiece(ChessPiece.Type.PAWN, ChessPiece.Color.BLACK);
            board[6][col] = new ChessPiece(ChessPiece.Type.PAWN, ChessPiece.Color.WHITE);
        }

        // Place black pieces
        board[0][0] = new ChessPiece(ChessPiece.Type.ROOK, ChessPiece.Color.BLACK);
        board[0][1] = new ChessPiece(ChessPiece.Type.KNIGHT, ChessPiece.Color.BLACK);
        board[0][2] = new ChessPiece(ChessPiece.Type.BISHOP, ChessPiece.Color.BLACK);
        board[0][3] = new ChessPiece(ChessPiece.Type.QUEEN, ChessPiece.Color.BLACK);
        board[0][4] = new ChessPiece(ChessPiece.Type.KING, ChessPiece.Color.BLACK);
        board[0][5] = new ChessPiece(ChessPiece.Type.BISHOP, ChessPiece.Color.BLACK);
        board[0][6] = new ChessPiece(ChessPiece.Type.KNIGHT, ChessPiece.Color.BLACK);
        board[0][7] = new ChessPiece(ChessPiece.Type.ROOK, ChessPiece.Color.BLACK);

        // Place white pieces
        board[7][0] = new ChessPiece(ChessPiece.Type.ROOK, ChessPiece.Color.WHITE);
        board[7][1] = new ChessPiece(ChessPiece.Type.KNIGHT, ChessPiece.Color.WHITE);
        board[7][2] = new ChessPiece(ChessPiece.Type.BISHOP, ChessPiece.Color.WHITE);
        board[7][3] = new ChessPiece(ChessPiece.Type.QUEEN, ChessPiece.Color.WHITE);
        board[7][4] = new ChessPiece(ChessPiece.Type.KING, ChessPiece.Color.WHITE);
        board[7][5] = new ChessPiece(ChessPiece.Type.BISHOP, ChessPiece.Color.WHITE);
        board[7][6] = new ChessPiece(ChessPiece.Type.KNIGHT, ChessPiece.Color.WHITE);
        board[7][7] = new ChessPiece(ChessPiece.Type.ROOK, ChessPiece.Color.WHITE);
    }

    public ChessPiece getPiece(int row, int col) {
        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            return board[row][col];
        }
        return null;
    }

    public ChessPiece.Color getCurrentTurn() {
        return currentTurn;
    }

    public Position getSelectedPiece() {
        return selectedPiece;
    }

    public List<Position> getValidMoves() {
        return validMoves;
    }

    public void selectPiece(int row, int col) {
        ChessPiece piece = getPiece(row, col);

        if (piece != null && piece.getColor() == currentTurn) {
            selectedPiece = new Position(row, col);
            validMoves = calculateValidMoves(selectedPiece);
        } else {
            selectedPiece = null;
            validMoves.clear();
        }
    }

    public boolean movePiece(int toRow, int toCol) {
        if (selectedPiece == null) return false;

        Position targetPos = new Position(toRow, toCol);
        if (!validMoves.contains(targetPos)) return false;

        // Perform the move
        ChessPiece piece = board[selectedPiece.row][selectedPiece.col];
        board[toRow][toCol] = piece;
        board[selectedPiece.row][selectedPiece.col] = null;
        piece.setMoved(true);

        // Switch turns
        currentTurn = (currentTurn == ChessPiece.Color.WHITE) ?
                      ChessPiece.Color.BLACK : ChessPiece.Color.WHITE;

        selectedPiece = null;
        validMoves.clear();

        return true;
    }

    private List<Position> calculateValidMoves(Position pos) {
        List<Position> moves = new ArrayList<>();
        ChessPiece piece = board[pos.row][pos.col];

        if (piece == null) return moves;

        switch (piece.getType()) {
            case PAWN:
                calculatePawnMoves(pos, piece, moves);
                break;
            case ROOK:
                calculateRookMoves(pos, piece, moves);
                break;
            case KNIGHT:
                calculateKnightMoves(pos, piece, moves);
                break;
            case BISHOP:
                calculateBishopMoves(pos, piece, moves);
                break;
            case QUEEN:
                calculateRookMoves(pos, piece, moves);
                calculateBishopMoves(pos, piece, moves);
                break;
            case KING:
                calculateKingMoves(pos, piece, moves);
                break;
        }

        return moves;
    }

    private void calculatePawnMoves(Position pos, ChessPiece piece, List<Position> moves) {
        int direction = (piece.getColor() == ChessPiece.Color.WHITE) ? -1 : 1;

        // Move forward one square
        int newRow = pos.row + direction;
        if (isValidPosition(newRow, pos.col) && board[newRow][pos.col] == null) {
            moves.add(new Position(newRow, pos.col));

            // Move forward two squares from starting position
            if (!piece.hasMoved()) {
                newRow = pos.row + (2 * direction);
                if (board[newRow][pos.col] == null) {
                    moves.add(new Position(newRow, pos.col));
                }
            }
        }

        // Capture diagonally
        newRow = pos.row + direction;
        for (int colOffset : new int[]{-1, 1}) {
            int newCol = pos.col + colOffset;
            if (isValidPosition(newRow, newCol)) {
                ChessPiece target = board[newRow][newCol];
                if (target != null && target.getColor() != piece.getColor()) {
                    moves.add(new Position(newRow, newCol));
                }
            }
        }
    }

    private void calculateRookMoves(Position pos, ChessPiece piece, List<Position> moves) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        calculateLinearMoves(pos, piece, moves, directions);
    }

    private void calculateBishopMoves(Position pos, ChessPiece piece, List<Position> moves) {
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        calculateLinearMoves(pos, piece, moves, directions);
    }

    private void calculateLinearMoves(Position pos, ChessPiece piece, List<Position> moves, int[][] directions) {
        for (int[] dir : directions) {
            int row = pos.row + dir[0];
            int col = pos.col + dir[1];

            while (isValidPosition(row, col)) {
                ChessPiece target = board[row][col];
                if (target == null) {
                    moves.add(new Position(row, col));
                } else {
                    if (target.getColor() != piece.getColor()) {
                        moves.add(new Position(row, col));
                    }
                    break;
                }
                row += dir[0];
                col += dir[1];
            }
        }
    }

    private void calculateKnightMoves(Position pos, ChessPiece piece, List<Position> moves) {
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] move : knightMoves) {
            int newRow = pos.row + move[0];
            int newCol = pos.col + move[1];

            if (isValidPosition(newRow, newCol)) {
                ChessPiece target = board[newRow][newCol];
                if (target == null || target.getColor() != piece.getColor()) {
                    moves.add(new Position(newRow, newCol));
                }
            }
        }
    }

    private void calculateKingMoves(Position pos, ChessPiece piece, List<Position> moves) {
        int[][] kingMoves = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] move : kingMoves) {
            int newRow = pos.row + move[0];
            int newCol = pos.col + move[1];

            if (isValidPosition(newRow, newCol)) {
                ChessPiece target = board[newRow][newCol];
                if (target == null || target.getColor() != piece.getColor()) {
                    moves.add(new Position(newRow, newCol));
                }
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public void reset() {
        board = new ChessPiece[8][8];
        currentTurn = ChessPiece.Color.WHITE;
        selectedPiece = null;
        validMoves.clear();
        initializeBoard();
    }
}
