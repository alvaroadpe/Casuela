package com.alvaroadpe.casuela;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casuela.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class equiposActivity extends AppCompatActivity {

    private teams teams;
    int playerNumber = 0;
    //teams teams = (teams) getIntent().getExtras().getSerializable("teamsKey");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);

        //Bundle recievedBundle = getIntent().getExtras();
        teams = (teams) getIntent().getExtras().getSerializable("teamsKey");


        ((Button)findViewById(R.id.confirmButton2)).setEnabled(false);
        ((Button)findViewById(R.id.addWordButton)).setEnabled(true);
        ((Button)findViewById(R.id.resetButton2)).setEnabled(false);
        ((TextView) findViewById(R.id.descriptionTextView)).setText("Es el turno de " + teams.getPlayersList().get(playerNumber) + " de escribir sus 3 palabras.");
    }

    List<String> individualWordList = new ArrayList<String>();
    int contador = 0;
    String newLine = System.getProperty("line.separator");


    public void addWord (View v){
        if (contador <= 2){
            TextView t = findViewById(R.id.wordSource);
            String input = t.getText().toString();

            if (input.equals("") || input.contains(" ")) {
                Toast.makeText(this, "NOMBRE VACIO O CON ESPACIOS", Toast.LENGTH_LONG).show();
                ((TextView) findViewById(R.id.wordSource)).setText("");
            }

            else{
                individualWordList.add(0,input);

                StringBuilder playerWords = new StringBuilder("Palabras:\n");
                for (int i = 0; i < individualWordList.size(); i++) {
                    playerWords.append("    ").append(individualWordList.get(i)).append(newLine);
                }
                ((TextView) findViewById(R.id.palabras)).setText(playerWords.toString());


                //Toast.makeText(this, input + " añadido", Toast.LENGTH_LONG).show();
                ((TextView) findViewById(R.id.wordSource)).setText("");
                contador = contador + 1;
                ((Button)findViewById(R.id.resetButton2)).setEnabled(true);
            }
        }
        if (contador > 2){
            v.setEnabled(false);
            Button b = (Button) v;
            b.setText("3 PALABRAS YA INTRODUCIDAS");
            ((Button)findViewById(R.id.confirmButton2)).setEnabled(true);

        }
    }

    @SuppressLint("SetTextI18n")
    public void erase (View v){
        individualWordList.clear();
        ((TextView)findViewById(R.id.wordSource)).setText("");
        ((TextView)findViewById(R.id.palabras)).setText("");
        ((Button)findViewById(R.id.addWordButton)).setText("AÑADIR PALABRA");
        ((Button)findViewById(R.id.resetButton2)).setEnabled(false);
        ((Button)findViewById(R.id.addWordButton)).setEnabled(true);
        ((Button)findViewById(R.id.confirmButton2)).setEnabled(false);
        contador = 0;
    }


    @SuppressLint("SetTextI18n")
    public void confirm (View v){
        Collections.shuffle(individualWordList);
        //teams teams = (teams) getIntent().getExtras().getSerializable("teamsKey");
        List<String> playerlist = teams.getPlayersList();
        for (int i = 0; i < individualWordList.size(); i++) {
            teams.addWord(individualWordList.get(i));
        }

        individualWordList.clear();
        ((TextView)findViewById(R.id.wordSource)).setText("");
        ((TextView)findViewById(R.id.palabras)).setText("");
        ((Button)findViewById(R.id.addWordButton)).setText("AÑADIR PALABRA");
        ((Button)findViewById(R.id.resetButton2)).setEnabled(false);
        ((Button)findViewById(R.id.addWordButton)).setEnabled(true);
        ((Button)findViewById(R.id.confirmButton2)).setEnabled(false);

        contador = 0;
        //playerNumber = playerNumber + 1;
        if (playerNumber < playerlist.size()-1){
            playerNumber = playerNumber + 1;

            AlertDialog.Builder nextPlayerPopUp = new AlertDialog.Builder(equiposActivity.this);
            nextPlayerPopUp.setMessage("Pasale el móbil a " + playerlist.get(playerNumber))
                    .setCancelable(false)
                    .setPositiveButton("Soy " + playerlist.get(playerNumber), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //finish();
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog title = nextPlayerPopUp.create();
            title.setTitle("Siguiente jugador");
            title.show();

            ((TextView) findViewById(R.id.descriptionTextView)).setText("Es el turno de " + teams.getPlayersList().get(playerNumber) + " de escribir sus 3 palabras.");
        }
        else{
            ((TextView) findViewById(R.id.descriptionTextView)).setText("");

            Intent intentTimer = new Intent(this,timerActivity.class);
            Bundle sentBundle = new Bundle();
            sentBundle.putSerializable("teamsKey", (Serializable) teams);
            intentTimer.putExtras(sentBundle);
            startActivity(intentTimer);

        }

    }






   /*
    public void revelarEquipos (View v){
        Log.d("informacion","revelar equipos iniciado");
        List<String> playersList = teams.getPlayersList();
        Log.d("informacion","playerlist" + playersList.toString());
        int quantity = teams.getQuantity();
        Collections.shuffle(playersList);

        Log.d("informacion","revuelta" + playersList.toString());

        StringBuilder playersText = new StringBuilder();
        for (int i=0;i<quantity;i+=2){
            String nextTeam = "Equipo " + (i+2)/2 + newLine + playersList.get(i) + newLine + playersList.get(i+1) + newLine + newLine;
            playersText.append(nextTeam);
        }

        if (playersList.size()%2 == 1) {
            playersText.replace(-1,-1,playersList.get(playersList.size()-1));
        }

        ((TextView)findViewById(R.id.playersView)).setText(playersText.toString());

    }


    */



}