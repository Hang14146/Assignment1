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

public class Level2Activity extends AppCompatActivity {

    private View[] views2;
    private int highlightedIndex;
    private int successfulTouches;
    private TextView successfulTouchesTextView;
    private CountDownTimer countDownTimer;
    private boolean canProceed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);

        // Initialize variables
        views2 = new View[] {
                findViewById(R.id.view1),
                findViewById(R.id.view2),
                findViewById(R.id.view3),
                findViewById(R.id.view4),
                findViewById(R.id.view5),
                findViewById(R.id.view6),
                findViewById(R.id.view7),
                findViewById(R.id.view8),
                findViewById(R.id.view9)
        };
        highlightedIndex = -1;
        int scoreLevel1 = getIntent().getIntExtra("score_level1", 0);
        successfulTouches = scoreLevel1;
        successfulTouchesTextView = findViewById(R.id.successfulTouchesTextView);

        // Randomly highlight a view
        highlightRandomView();

        // Set up touch listeners for views
        for (View view : views2) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v == views2[highlightedIndex]) {
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
                if(canProceed) {
                    // Proceed to Level 3
                    Intent intent = new Intent(Level2Activity.this, Level3Activity.class);
                    intent.putExtra("score", successfulTouches);
                    intent.putExtra("score_level2", successfulTouches);
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
                Intent intent = new Intent(Level2Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void highlightRandomView() {
        // Remove highlighting from previous view
        if (highlightedIndex != -1) {
            views2[highlightedIndex].setBackgroundColor(Color.WHITE);
        }
        // Randomly highlight a new view that is different from the previous view
        int randomIndex = highlightedIndex;
        while (randomIndex == highlightedIndex) {
            randomIndex = (int) (Math.random() * views2.length);
        }
        highlightedIndex = randomIndex;
        views2[highlightedIndex].setBackgroundColor(Color.BLUE);
    }
}