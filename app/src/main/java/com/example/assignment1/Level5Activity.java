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

public class Level5Activity extends AppCompatActivity {

    private View[] views5;
    private int highlightedIndex;
    private int successfulTouches;
    private TextView successfulTouchesTextView;
    private CountDownTimer countDownTimer;
    private boolean canProceed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5);

        // Initialize variables
        views5 = new View[] {
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
                findViewById(R.id.view25),
                findViewById(R.id.view26),
                findViewById(R.id.view27),
                findViewById(R.id.view28),
                findViewById(R.id.view29),
                findViewById(R.id.view30),
                findViewById(R.id.view31),
                findViewById(R.id.view32),
                findViewById(R.id.view33),
                findViewById(R.id.view34),
                findViewById(R.id.view35),
                findViewById(R.id.view36)

        };
        highlightedIndex = -1;
        int scoreLevel4 = getIntent().getIntExtra("score_level4", 0);
        successfulTouches = scoreLevel4;
        successfulTouchesTextView = findViewById(R.id.successfulTouchesTextView);

        // Randomly highlight a view
        highlightRandomView();

        // Set up touch listeners for views
        for (View view : views5) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v == views5[highlightedIndex]) {
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
                    //Game ends proceed to name input
                    Intent intent = new Intent(Level5Activity.this, UsernameInputActivity.class);
                    intent.putExtra("score", successfulTouches);
                    intent.putExtra("score_level5", successfulTouches);
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
                Intent intent = new Intent(Level5Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void highlightRandomView() {
        // Remove highlighting from previous view
        if (highlightedIndex != -1) {
            views5[highlightedIndex].setBackgroundColor(Color.WHITE);
        }
        // Randomly highlight a new view that is different from the previous view
        int randomIndex = highlightedIndex;
        while (randomIndex == highlightedIndex) {
            randomIndex = (int) (Math.random() * views5.length);
        }
        highlightedIndex = randomIndex;
        views5[highlightedIndex].setBackgroundColor(Color.RED);
    }
}