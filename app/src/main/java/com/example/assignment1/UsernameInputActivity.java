package com.example.assignment1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UsernameInputActivity extends AppCompatActivity {

    private int level;
    private int score;
    private EditText usernameEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_input);

        // Get level and score data from intent
        Intent intent = getIntent();
        level = intent.getIntExtra("Level", 0);
        score = intent.getIntExtra("score_level5", 0);

        // Set up UI elements
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + score);
        usernameEditText = findViewById(R.id.usernameEditText);
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username and pass it back to MainActivity
                String username = usernameEditText.getText().toString();
                saveData(username, score);
                Intent intent = new Intent(UsernameInputActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
    }

    private void saveData(String username, int score) {
        // Insert data into database
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("score", score);
        db.insert("scores", null, values);
        db.close();

    }
}