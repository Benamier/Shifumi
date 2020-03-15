package com.example.shifumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.connection.SendReceive;
import com.example.model.Game;
import com.example.model.Player;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MESSAGE_READ = 1;
    private static final int MESSAGE_WRITE = 2;


    public static Game game;

    private static  Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MESSAGE_READ:  byte[] readBuffer = (byte[]) msg.obj;
                    String tmpMsg = new String(readBuffer, 0, msg.arg1);
                    //Toast.makeText(, tmpMsg, Toast.LENGTH_LONG).show();
                    Log.i("MESSAGE", "message reçu");
                    MainActivity.hostAnswer.setText(tmpMsg);
                    break;

                case MESSAGE_WRITE:
            }
            return true;
        }
    });

    private SendReceive sendReceive;

    private ImageButton rockButton;
    private ImageButton paperButton;
    private ImageButton scissorButton;

    private Button validate;
    private Button quit;

    private TextView hostName;
    private TextView clientName;
    private static TextView hostAnswer;
    private static TextView clientAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rockButton = findViewById(R.id.rockButton);
        paperButton = findViewById(R.id.paperButton);
        scissorButton = findViewById(R.id.scissorButton);

        validate = findViewById(R.id.valider);
        quit = findViewById(R.id.Quitter);

        hostName = findViewById(R.id.hostName);
        clientName = findViewById(R.id.clientName);
        hostAnswer = findViewById(R.id.hostAnswer);
        clientAnswer = findViewById(R.id.clientAnswer);


        game = (Game) getIntent().getSerializableExtra("GAME");
        List<Player> players = game.getPlayers();
        hostName.setText(players.get(0).getName());
        clientName.setText(players.get(1).getName());
        setListeners();

    }


    private void setListeners() {

        this.validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String msg = "P";
            hostAnswer.setText(msg);
            }
        });

    }

    public static void obtainMessage(int bytes, byte[] buffer) {
        handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer);
    }

    public static void hideAnswer(int playerId) {
        if(playerId == 0)
            hostAnswer.setText("?");
        else
            clientAnswer.setText("?");
    }

}
