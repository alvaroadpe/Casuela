package com.alvaroadpe.casuela;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.casuela.R;

import java.util.List;

public class scoresActivity extends AppCompatActivity {

    private teams teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        teams = (teams) getIntent().getExtras().getSerializable("timerToScores");

        ((TextView)findViewById(R.id.scoreTextView)).setText("");


        List<String> playersList = teams.getPlayersList();
        int quantity = teams.getQuantity();

        StringBuilder playersText = new StringBuilder();
        int i = 0;
        while (i<quantity/2){
            String nextTeam = "Team " + (i+1) + "\n    " + playersList.get(i) + "  &  " +
                    playersList.get((i+playersList.size()/2)) + "\n";
            playersText.append("\n");
            i += 1;
        }

        // playersText.append(newLine).append("Empieza ").append(playersList.get((int) (Math.random()*(playersList.size()-1))));
        //playersText.append(newLine).append("Empieza ").append(playersList.get(0));
        ((TextView)findViewById(R.id.scoreTextView)).setText(playersText.toString());
        //((TextView)findViewById(R.id.source)).setText("");
    }

}