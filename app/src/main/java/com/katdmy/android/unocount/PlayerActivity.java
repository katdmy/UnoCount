package com.katdmy.android.unocount;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    private PlayerViewModel mPlayerViewModel;
    private PlayerAdapter mAdapter;
    private ArrayList<String> mPlayers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        setRecyclerView();
        setButtons();

        mPlayerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        mPlayerViewModel.getPlayers().observe(this, (players) -> {
            mAdapter.setPlayers(players);
            for (int i = 0; i < players.size(); i++)
                mPlayers.add(players.get(i).getName());
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

        mAdapter.setOnItemClickListener(new PlayerAdapter.OnItemClickListener() {
            @Override
            public void onActiveClick(int code, boolean active) {
                AsyncTask.execute(() -> mPlayerViewModel.setActive(code, !active));
            }

            @Override
            public void onDeleteClick(Player player) {
                AsyncTask.execute(() -> mPlayerViewModel.deletePlayer(player));
            }
        });
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
                AsyncTask.execute(() -> mPlayerViewModel.insertPlayer(new Player(newName)));
                dialog.dismiss();
            });
            builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        Button buttonNew = findViewById(R.id.buttonNew);
        buttonNew.setOnClickListener((v) -> startScoreActivity());
    }

    void startScoreActivity() {
        Intent intent = new Intent(PlayerActivity.this, ScoreActivity.class);
        intent.putStringArrayListExtra("players", mPlayers);
        startActivity(intent);
    }

}
