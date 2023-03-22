
package com.example.assignment1;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Level4Activity extends AppCompatActivity {

    private View[] views4;
    private int highlightedIndex;
    private int successfulTouches;
    private TextView successfulTouchesTextView;
    private CountDownTimer countDownTimer;
    private boolean canProceed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level4);

        // Initialize variables
        views4 = new View[] {
                findViewById(R.id.view1),
                findViewById(R.id.view2),
                findViewById(R.id.view3),
                findViewById(R.id.view4),
                findViewById(R.id.view5),
                findViewById(R.id.view6),
                findViewById(R.id.view7),
                findViewById(R.id.view8),
                findViewById(R.id.view9),
                findViewById(R.id.view10),
                findViewById(R.id.view11),
                findViewById(R.id.view12),
                findViewById(R.id.view13),
                findViewById(R.id.view14),
                findViewById(R.id.view15),
                findViewById(R.id.view16),
                findViewById(R.id.view17),
                findViewById(R.id.view18),
                findViewById(R.id.view19),
                findViewById(R.id.view20),
                findViewById(R.id.view21),
                findViewById(R.id.view22),
                findViewById(R.id.view23),
                findViewById(R.id.view24),
                findViewById(R.id.view25)

        };
        highlightedIndex = -1;
        int scoreLevel3 = getIntent().getIntExtra("score_level3", 0);
        successfulTouches = scoreLevel3;
        successfulTouchesTextView = findViewById(R.id.successfulTouchesTextView);

        // Randomly highlight a view
        highlightRandomView();

        // Set up touch listeners for views
        for (View view : views4) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v == views4[highlightedIndex]) {
                        // Player touched the highlighted view
                        successfulTouches++;
                        successfulTouchesTextView.setText("Score: " + successfulTouches);
                        highlightRandomView();
                    }
                    return true;
                }
            });
        }

        // Set up countdown timer
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {

                if (canProceed) {
                    // Game over
                    Intent intent = new Intent(Level4Activity.this, Level5Activity.class);
                    intent.putExtra("score", successfulTouches);
                    intent.putExtra("score_level4", successfulTouches);
                    startActivity(intent);
                    finish();
                }
            }
        };
        countDownTimer.start();

        // Set up exit button
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set canProceed flag to false and navigate back to MainActivity
                canProceed = false;
                Intent intent = new Intent(Level4Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void highlightRandomView() {
        // Remove highlighting from previous view
        if (highlightedIndex != -1) {
            views4[highlightedIndex].setBackgroundColor(Color.WHITE);
        }
        // Randomly highlight a new view that is different from the previous view
        int randomIndex = highlightedIndex;
        while (randomIndex == highlightedIndex) {
            randomIndex = (int) (Math.random() * views4.length);
        }
        highlightedIndex = randomIndex;
        views4[highlightedIndex].setBackgroundColor(Color.CYAN);
    }

}
