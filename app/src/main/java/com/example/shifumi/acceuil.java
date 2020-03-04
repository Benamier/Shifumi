package com.example.shifumi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class acceuil extends AppCompatActivity {

    private EditText playerInput;
    private Button joinGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        this.playerInput = findViewById(R.id.player_input);
        this.joinGame = findViewById(R.id.join_game);

        setListener();
    }

    private void setListener() {
        Intent intent = new Intent(this, Salon.class);
        startActivity(intent);
    }
}
