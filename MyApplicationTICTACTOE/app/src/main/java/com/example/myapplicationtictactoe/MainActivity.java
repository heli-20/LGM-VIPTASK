package com.example.myapplicationtictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount = 0;

    private TextView playerTurnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerTurnText = findViewById(R.id.playerTurnText);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = (Button) gridLayout.getChildAt(i * 3 + j);
            }
        }
    }

    public void onButtonClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) view).setText("X");
            playerTurnText.setText("Player 2's Turn");
        } else {
            ((Button) view).setText("O");
            playerTurnText.setText("Player 1's Turn");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                playerWins("Player 1");
            } else {
                playerWins("Player 2");
            }
        } else if (roundCount == 9) {
            gameOver();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] board = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2]) && !board[i][0].equals("")) {
                return true;
            }
            if (board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i]) && !board[0][i].equals("")) {
                return true;
            }
        }

        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].equals("")) {
            return true;
        }

        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void playerWins(String player) {
        Toast.makeText(this, player + " wins!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void gameOver() {
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        player1Turn = true;
        roundCount = 0;
        playerTurnText.setText("Player 1's Turn");
    }
}
