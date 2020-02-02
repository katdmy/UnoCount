package com.katdmy.android.unocount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
                if (players.size() > 0) {
                    for (int i = 0; i < players.size(); i++)
                        addPlayerHeaderView(i, players.get(i));
                }
            });

        }

        setRecyclerView();
        setButtons();

        TextView total1TextView = findViewById(R.id.total1TextView);
        TextView total2TextView = findViewById(R.id.total2TextView);
        TextView total3TextView = findViewById(R.id.total3TextView);
        TextView total4TextView = findViewById(R.id.total4TextView);
        TextView total5TextView = findViewById(R.id.total5TextView);

        mScoreViewModel.getTotal().observe(this, (total) -> {
            total1TextView.setText(String.valueOf(total.getPlayer1()));
            total2TextView.setText(String.valueOf(total.getPlayer2()));
            total3TextView.setText(String.valueOf(total.getPlayer3()));
            total4TextView.setText(String.valueOf(total.getPlayer4()));
            total5TextView.setText(String.valueOf(total.getPlayer5()));
        });
    }

    void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mScoreAdapter = new ScoreAdapter(this);
        recyclerView.setAdapter(mScoreAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void setButtons() {
        EditText new1EditText = findViewById(R.id.new1EditText);
        EditText new2EditText = findViewById(R.id.new2EditText);
        EditText new3EditText = findViewById(R.id.new3EditText);
        EditText new4EditText = findViewById(R.id.new4EditText);
        EditText new5EditText = findViewById(R.id.new5EditText);
        Button newRoundButton = findViewById(R.id.newRoundButton);
        newRoundButton.setOnClickListener((view) -> {
            if (!TextUtils.isEmpty(new1EditText.getText()) &&
                    !TextUtils.isEmpty(new2EditText.getText()) &&
                    !TextUtils.isEmpty(new3EditText.getText()) &&
                    !TextUtils.isEmpty(new4EditText.getText()) &&
                    !TextUtils.isEmpty(new5EditText.getText())) {
                int new1 = Integer.parseInt(new1EditText.getText().toString());
                int new2 = Integer.parseInt(new2EditText.getText().toString());
                int new3 = Integer.parseInt(new3EditText.getText().toString());
                int new4 = Integer.parseInt(new4EditText.getText().toString());
                int new5 = Integer.parseInt(new5EditText.getText().toString());

                Score score = new Score(0, new1, new2, new3, new4, new5);
                mScoreViewModel.insert(score);

                new1EditText.setText("");
                new2EditText.setText("");
                new3EditText.setText("");
                new4EditText.setText("");
                new5EditText.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Заполните счёт всех игроков!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clearData() {
        AsyncTask.execute(() -> mScoreViewModel.deleteAll());
        LinearLayout parent = findViewById(R.id.headerLayout);
        for (int i = 0; i < parent.getChildCount(); i++) {
            TextView childView = (TextView) parent.getChildAt(i);
            parent.removeView(childView);
        }

    }

    public void addPlayerHeaderView(int i, String name) {
        LinearLayout parent = findViewById(R.id.headerLayout);

        VerticalTextView playerTextView = new VerticalTextView(this, null);
        playerTextView.setVerticalText(name);
        playerTextView.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL));
        parent.addView(playerTextView);

        LinearLayout.LayoutParams loParams = (LinearLayout.LayoutParams) playerTextView.getLayoutParams();
        loParams.width = 0;
        loParams.weight = 3f;
        loParams.height = parent.getHeight();
        playerTextView.setLayoutParams(loParams);

    }
}