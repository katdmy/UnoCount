package com.katdmy.android.unocount;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.StringUtil;

import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    private PlayerViewModel mPlayerViewModel;
    private PlayerAdapter mAdapter;
    private ArrayList<String> mActivePlayers;
    private Button mButtonNew;
    private String LOG_TAG = PlayerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        setRecyclerView();
        setButtons();

        mPlayerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        mPlayerViewModel.getPlayers().observe(this, (players) -> mAdapter.setPlayers(players));
        mPlayerViewModel.getActivePlayers().observe(this, (activePlayers) -> {
            mActivePlayers = (ArrayList<String>) activePlayers;
            if (mActivePlayers.size() == 0) {
                mButtonNew.setEnabled(false);
            } else {
                mButtonNew.setEnabled(true);
            }
        });
    }

    void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new PlayerAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mAdapter.setOnItemClickListener(player ->
                AsyncTask.execute(() -> {
                    mPlayerViewModel.deletePlayer(player);
                    Log.e(LOG_TAG, "Command to delete player (" + player.getName() + ") sent to ViewModel.");
                }));
        mAdapter.setOnCheckedChangeListener((player, isChecked) ->
                AsyncTask.execute(() -> mPlayerViewModel.setActive(player, isChecked)));
    }

    void setButtons() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Введите имя нового игрока");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("Сохранить", (dialog, which) -> {
                String newName = input.getText().toString();
                if (!newName.isEmpty())
                    AsyncTask.execute(() -> mPlayerViewModel.insertPlayer(new Player(newName)));
                dialog.dismiss();
            });
            builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        mButtonNew = findViewById(R.id.buttonNew);
        mButtonNew.setOnClickListener((v) -> startScoreActivity());
    }

    void startScoreActivity() {
        Intent intent = new Intent(PlayerActivity.this, ScoreActivity.class);
        intent.putStringArrayListExtra("players", mActivePlayers);
        startActivity(intent);
    }

}
