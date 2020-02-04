package com.katdmy.android.unocount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    private ScoreViewModel mScoreViewModel;
    private ScoreAdapter mScoreAdapter;
    private List<String> mActivePlayers;
    private int playersCount;
    private String LOG_TAG = ScoreActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mScoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        mScoreViewModel.getScore().observe(this, score -> mScoreAdapter.setScore(score));

        boolean newGame = false;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            newGame = extras.getBoolean("newGame");
        }

        if (newGame) {
            clearData();
            mScoreViewModel.getActivePlayers().observe(this, (players) -> {
                if (players.size() > 0)
                    createViews(players);
            });
        }

        setRecyclerView();
        setButtons();

//        TextView total1TextView = findViewById(R.id.total1TextView);
//        TextView total2TextView = findViewById(R.id.total2TextView);
//        TextView total3TextView = findViewById(R.id.total3TextView);
//        TextView total4TextView = findViewById(R.id.total4TextView);
//        TextView total5TextView = findViewById(R.id.total5TextView);

//        mScoreViewModel.getTotal().observe(this, (total) -> {
//            total1TextView.setText(String.valueOf(total.getPlayer1()));
//            total2TextView.setText(String.valueOf(total.getPlayer2()));
//            total3TextView.setText(String.valueOf(total.getPlayer3()));
//            total4TextView.setText(String.valueOf(total.getPlayer4()));
//            total5TextView.setText(String.valueOf(total.getPlayer5()));

//            for (int i = 1; i < playersCount; i++) {
//                LinearLayout totalParent = findViewById(R.id.totalLayout);
//                TextView totalTextView = totalParent.findViewWithTag("total" + i);
//                totalTextView.setText(total.getPlayer1());
//            }

//        });
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mScoreAdapter = new ScoreAdapter(this);
        recyclerView.setAdapter(mScoreAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setButtons() {
//        EditText new1EditText = findViewById(R.id.new1EditText);
//        EditText new2EditText = findViewById(R.id.new2EditText);
//        EditText new3EditText = findViewById(R.id.new3EditText);
//        EditText new4EditText = findViewById(R.id.new4EditText);
//        EditText new5EditText = findViewById(R.id.new5EditText);
//        Button newRoundButton = findViewById(R.id.newRoundButton);
//        newRoundButton.setOnClickListener((view) -> {
//            if (!TextUtils.isEmpty(new1EditText.getText()) &&
//                    !TextUtils.isEmpty(new2EditText.getText()) &&
//                    !TextUtils.isEmpty(new3EditText.getText()) &&
//                    !TextUtils.isEmpty(new4EditText.getText()) &&
//                    !TextUtils.isEmpty(new5EditText.getText())) {
//                int new1 = Integer.parseInt(new1EditText.getText().toString());
//                int new2 = Integer.parseInt(new2EditText.getText().toString());
//                int new3 = Integer.parseInt(new3EditText.getText().toString());
//                int new4 = Integer.parseInt(new4EditText.getText().toString());
//                int new5 = Integer.parseInt(new5EditText.getText().toString());
//
//                Score score = new Score(0, );
//                mScoreViewModel.insert(score);
//
//                new1EditText.setText("");
//                new2EditText.setText("");
//                new3EditText.setText("");
//                new4EditText.setText("");
//                new5EditText.setText("");
//            } else {
//                Toast.makeText(getApplicationContext(), "Заполните счёт всех игроков!", Toast.LENGTH_LONG).show();
//            }
//        });

        Button newRoundButton = findViewById(R.id.newRoundButton);
        newRoundButton.setOnClickListener((view) -> {
            String scoreData = "";
            LinearLayout newRoundParent = findViewById(R.id.newRoundLayout);
            for (int i = 0; i < playersCount; i++) {
                TextView totalTextView = newRoundParent.findViewWithTag("newRound" + i);
                String currentPlayerScore = totalTextView.getText().toString();
                if (currentPlayerScore.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Заполните счёт всех игроков!", Toast.LENGTH_LONG).show();
                    break;
                }
                scoreData = scoreData + currentPlayerScore + ",";
            }
            Score newScore = new Score(0, scoreData);
            mScoreViewModel.insert(newScore);
        });
    }

    private void clearData() {
        AsyncTask.execute(() -> mScoreViewModel.deleteAll());

        LinearLayout headerParent = findViewById(R.id.headerLayout);
        Log.e(LOG_TAG, "Children elements: " + headerParent.getChildCount());
        int count = headerParent.getChildCount();
        for (int i = 1; i < count; i++) {
            TextView childView = (TextView) headerParent.getChildAt(1);
            Log.e(LOG_TAG, "i=" + i + ", deleting " + childView.getText().toString());
            headerParent.removeView(childView);
        }
    }

    private void createViews(List<String> players) {
        playersCount = players.size();

        LinearLayout headerParent = findViewById(R.id.headerLayout);
        for (int i = 0; i < players.size(); i++) {
            String name = players.get(i);
            VerticalTextView playerTextView = new VerticalTextView(this, null);
            playerTextView.setVerticalText(name);
            playerTextView.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL));
            headerParent.addView(playerTextView);

            LinearLayout.LayoutParams loParams = (LinearLayout.LayoutParams) playerTextView.getLayoutParams();
            loParams.width = 0;
            loParams.weight = 3f;
            loParams.height = headerParent.getHeight();
            playerTextView.setLayoutParams(loParams);
        }

        LinearLayout totalParent = findViewById(R.id.totalLayout);
        for (int i = 0; i < players.size(); i++) {
            TextView totalTextView = new TextView(this);
            totalTextView.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL));
            totalTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            totalTextView.setTag("total" + i);
            totalParent.addView(totalTextView);

            LinearLayout.LayoutParams loParams = (LinearLayout.LayoutParams) totalTextView.getLayoutParams();
            loParams.width = 0;
            loParams.weight = 3f;
            loParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            totalTextView.setLayoutParams(loParams);
        }

        LinearLayout newRoundParent = findViewById(R.id.newRoundLayout);
        for (int i = 0; i < players.size(); i++) {
            EditText newRoundEditText = new EditText(this);
            newRoundEditText.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL));
            newRoundEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            newRoundEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            newRoundEditText.setTag("newRound" + i);
            newRoundParent.addView(newRoundEditText);

            LinearLayout.LayoutParams loParams = (LinearLayout.LayoutParams) newRoundEditText.getLayoutParams();
            loParams.width = 0;
            loParams.weight = 3f;
            loParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            newRoundEditText.setLayoutParams(loParams);
        }
    }
}