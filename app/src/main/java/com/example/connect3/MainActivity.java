package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 0:Yellow, 1:Red, 2:empty
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {6, 4, 2}};
    boolean gameActive = true;

    public boolean isTie (int[] stateArray) {
        int sumEmpty = 0;
        for (int position : stateArray) {
            if (position == 2) {
                sumEmpty++;
            }
        }
        if (sumEmpty == 0) {
            return true;
        }
        return false;
    }
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            if (isTie(gameState)) {
                Toast.makeText(this, "Tie", Toast.LENGTH_SHORT).show();
            }


            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2) {
                    String winner;

                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

                    Button newGameButton = (Button) findViewById(R.id.newGameButton);
                    Drawable buttonDrawable = newGameButton.getBackground();
                    buttonDrawable = DrawableCompat.wrap(buttonDrawable);

                    gameActive = false;
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Yellow";
                        winnerTextView.setTextColor(Color.YELLOW);
                        DrawableCompat.setTint(buttonDrawable, Color.YELLOW);
                    } else {
                        winner = "Red";
                        winnerTextView.setTextColor(Color.RED);
                        DrawableCompat.setTint(buttonDrawable, Color.RED);
                    }

                    winnerTextView.setText(winner + " has WON!!!");
                    winnerTextView.setVisibility(view.VISIBLE);

                    newGameButton.setBackground(buttonDrawable);

                }
            }
        }
    }

    public void newGame (View view) {

        Log.i("Info", "New Game Pressed");
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        winnerTextView.setVisibility(view.INVISIBLE);

        androidx.gridlayout.widget.GridLayout gridLayout =
                (androidx.gridlayout.widget.GridLayout) findViewById (R.id.gridLayout);


        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        gameActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
