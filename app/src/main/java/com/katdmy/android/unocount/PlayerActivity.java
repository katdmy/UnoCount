package com.katdmy.android.unocount;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    private PlayerViewModel mPlayerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        PlayerAdapter adapter = new PlayerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mPlayerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        mPlayerViewModel.getPlayers().observe(this, (players) -> adapter.setPlayers(players));

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
    }

}
