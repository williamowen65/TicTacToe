package com.example.tictactoe

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TicTacToeViewModel : ViewModel() {
    // Game board as a 3x3 grid
    var board by mutableStateOf(Array(3) { Array(3) { "" } })
        private set
    
    // Current player (X or O)
    var currentPlayer by mutableStateOf("X")
        private set
        
    // Game status
    var gameStatus by mutableStateOf("Playing")
        private set

    fun handleBoardClick(row: Int, col: Int) {
        // Only allow moves on empty cells and when game is still active
        if (board[row][col] == "" && gameStatus == "Playing") {
            // Update the board with current player's mark
            val newBoard = board.map { it.clone() }.toTypedArray()
            newBoard[row][col] = currentPlayer
            board = newBoard
            
            // Check for win or draw
            when {
                checkForWin(row, col) -> gameStatus = "Winner: $currentPlayer"
                isBoardFull() -> gameStatus = "Draw"
                else -> {
                    // Switch players
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                }
            }
        }
    }

    fun newGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameStatus = "Playing"
    }
    
    private fun checkForWin(row: Int, col: Int): Boolean {
        val mark = board[row][col]
        
        // Check row
        if (board[row].all { it == mark }) return true
        
        // Check column
        if (board.indices.all { board[it][col] == mark }) return true
        
        // Check diagonals
        if (row == col && board.indices.all { board[it][it] == mark }) return true
        if (row + col == 2 && board.indices.all { board[it][2 - it] == mark }) return true
        
        return false
    }
    
    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { cell -> cell.isNotEmpty() } }
    }
}