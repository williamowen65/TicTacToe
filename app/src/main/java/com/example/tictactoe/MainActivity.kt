package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Shapes
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TicTacToeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                TicTacToeApp()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeApp(viewModel: TicTacToeViewModel = TicTacToeViewModel()) {



    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Tic Tac Toe",
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3F51B5),
                    titleContentColor = Color.White
                )

            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TicTacToeBoard(
                modifier = Modifier.fillMaxSize(),
                board = viewModel.board,
                onCellClick = viewModel::handleBoardClick,
                currentPlayer = viewModel.currentPlayer,
                gameStatus = viewModel.gameStatus,
                onNewGameClick = viewModel::newGame
                )
        }
    }
}


@Composable
fun TicTacToeBoard(
    modifier: Modifier = Modifier,
    board: Array<Array<String>>,
    onCellClick: (Int, Int) -> Unit,
    currentPlayer: String,
    gameStatus: String,
    onNewGameClick: () -> Unit
    ) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    BoardCell(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp),
                        onClick = { onCellClick(row, col) },
                        value = board[row][col]
                    )
                }
            }
        }

        Text(
            text = when {
                gameStatus.startsWith("Winner") -> gameStatus
                gameStatus == "Draw" -> "Game ended in a Draw"
                else -> "Current Player: $currentPlayer"
            },
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally))

        Button(
            onClick = onNewGameClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (30).dp)
        ) {
            Text(
                text = "New Game",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun BoardCell(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    value: String
    ) {
    Box(
        modifier = modifier
            .background(Color.Gray)
            .padding(2.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "",
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
        TicTacToeApp()
    }
}

