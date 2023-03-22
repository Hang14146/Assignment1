package com.example.assignment1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private ListView leaderboardListView;
    private ArrayList<Score> scoresList;
    private DatabaseHelper databaseHelper;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Initialize UI elements
        leaderboardListView = findViewById(R.id.leaderboardListView);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);


        // Retrieve scores data from database
        scoresList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {"username", "score"};
        Cursor cursor = db.query("scores", projection, null, null, null, null, "score DESC","25");
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
            Score newScore = new Score(username, score);
            scoresList.add(newScore);
        }
        cursor.close();
        db.close();


        // Set up leaderboard ListView
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, scoresList);
        leaderboardListView.setAdapter(adapter);
    }

    private static class Score {
        String username;
        int score;

        public Score(String username, int score) {
            this.username = username;
            this.score = score;
        }
    }

    private static class LeaderboardAdapter extends ArrayAdapter<Score> {

        private Context context;
        private ArrayList<Score> scoresList;

        public LeaderboardAdapter(Context context, ArrayList<Score> scoresList) {
            super(context, R.layout.leaderboard_row_layout, scoresList);
            this.context = context;
            this.scoresList = scoresList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.leaderboard_row_layout, parent, false);

            TextView usernameTextView = rowView.findViewById(R.id.usernameTextView);
            TextView scoreTextView = rowView.findViewById(R.id.scoreTextView);

            Score score = scoresList.get(position);

            usernameTextView.setText(score.username);
            scoreTextView.setText(String.valueOf(score.score));

            return rowView;
        }
    }
}

