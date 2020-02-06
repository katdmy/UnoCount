package com.katdmy.android.unocount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
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
    private int playerCount;
    private String LOG_TAG = ScoreActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        playerCount = getIntent().getExtras().getInt("playerCount");

        mScoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);
        //mScoreViewModel = ViewModelProviders.of(this, new ScoreViewModelFactory(this.getApplication(), playerCount)).get(ScoreViewModel.class);

        clearData();

        mScoreViewModel.getScore().observe(this, score -> mScoreAdapter.setScore(score));
        mScoreViewModel.getActivePlayers().observe(this, (players) -> {
            if (players.size() > 0) {
                createViews(players);
                setRecyclerView();
            }
        });
        mScoreViewModel.getTotal().observe(this, (total) -> {
            Log.e(LOG_TAG, "Пришёл total с " + total.size() + " элементов");
            if (total != null) {
                for (int i = 0; i < playerCount; i++) {
                    LinearLayout totalParent = findViewById(R.id.totalLayout);
                    TextView totalTextView = totalParent.findViewWithTag("total" + i);
                    totalTextView.setText(String.valueOf(total.get(i)));
                }
            } else {
                for (int i = 0; i < playerCount; i++) {
                    LinearLayout totalParent = findViewById(R.id.totalLayout);
                    TextView totalTextView = totalParent.findViewWithTag("total" + i);
                    totalTextView.setText("0");
                }
            }
        });
        setButtons();
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mScoreAdapter = new ScoreAdapter(this, playerCount);
        recyclerView.setAdapter(mScoreAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setButtons() {
        Button newRoundButton = findViewById(R.id.newRoundButton);
        newRoundButton.setOnClickListener((view) -> {
            String scoreData = "";
            LinearLayout newRoundParent = findViewById(R.id.newRoundLayout);
            for (int i = 0; i < playerCount; i++) {
                TextView totalTextView = newRoundParent.findViewWithTag("newRound" + i);
                if (TextUtils.isEmpty(totalTextView.getText())) {
                    Toast.makeText(getApplicationContext(), "Заполните счёт всех игроков!", Toast.LENGTH_LONG).show();
                    break;
                }
                String currentPlayerScore = totalTextView.getText().toString();
                scoreData = scoreData + currentPlayerScore + ",";
            }
            Score newScore = new Score(0, scoreData);
//            mScoreViewModel.insert(newScore);
        });
    }

    private void clearData() {
        //AsyncTask.execute(() -> mScoreViewModel.deleteAll());

        int count;
        LinearLayout headerParent = findViewById(R.id.headerLayout);
        count = headerParent.getChildCount();
        for (int i = 1; i < count; i++) {
            TextView childView = (TextView) headerParent.getChildAt(1);
            headerParent.removeView(childView);
        }

        LinearLayout totalParent = findViewById(R.id.totalLayout);
        count = totalParent.getChildCount();
        for (int i = 1; i < count; i++) {
            TextView childView = (TextView) totalParent.getChildAt(1);
            totalParent.removeView(childView);
        }

        LinearLayout newRoundParent = findViewById(R.id.newRoundLayout);
        count = newRoundParent.getChildCount();
        for (int i = 1; i < count; i++) {
            TextView childView = (TextView) newRoundParent.getChildAt(1);
            newRoundParent.removeView(childView);
        }
    }

    private void createViews(List<String> players) {
        LinearLayout headerParent = findViewById(R.id.headerLayout);
        for (int i = 0; i < playerCount; i++) {
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
        for (int i = 0; i < playerCount; i++) {
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
        for (int i = 0; i < playerCount; i++) {
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