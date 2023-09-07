package com.alvaroadpe.casuela;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casuela.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String extraTeams = "com.example.casuela.extraTeams";
    private teams teams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.resetButton)).setEnabled(false);
        ((Button)findViewById(R.id.equiposButton)).setEnabled(false);
        ((Button)findViewById(R.id.confirmButton)).setEnabled(false);
        teams = new teams();
        int contador = 0;

    }

    //public teams teams = new teams();
    int contador = 0;
    String newLine = System.getProperty("line.separator");

    @SuppressLint("SetTextI18n")
    public void addName (View v){
        if (contador <= 14){
            TextView t = findViewById(R.id.source);
            String input = t.getText().toString();

            if (input.equals("") || input.contains(" ")) {
                Toast.makeText(this, "NOMBRE VACIO O CON ESPACIOS", Toast.LENGTH_LONG).show();
                ((TextView) findViewById(R.id.source)).setText("");
            }

            else{
                teams.addPlayer(input);

                StringBuilder playerNames = new StringBuilder("Jugadores:\n");
                for (int i = 0; i < teams.getPlayersList().size(); i++) {
                    playerNames.append("    ").append(teams.getPlayersList().get(i)).append(newLine);
                }
                ((TextView) findViewById(R.id.nombres)).setText(playerNames.toString());


                //Toast.makeText(this, input + " añadido", Toast.LENGTH_LONG).show();
                ((TextView) findViewById(R.id.source)).setText("");
                ((Button)findViewById(R.id.resetButton)).setEnabled(true);
                ((Button)findViewById(R.id.equiposButton)).setText("CREAR EQUIPOS");
                contador = contador + 1;
            }
        }
        else {
            v.setEnabled(false);
            Button b = (Button) v;
            b.setText("MAXIMO NUMERO DE JUGADORES");
        }

        if (contador == 4)
            ((Button)findViewById(R.id.equiposButton)).setEnabled(true);



    }

    public void hacerEquipos (View v){

        List<String> playersList = teams.getPlayersList();
        int quantity = teams.getQuantity();
        Collections.shuffle(playersList);

        StringBuilder playersText = new StringBuilder();
        int i = 0;
        while (i<quantity/2){
            String nextTeam = "Equipo " + (i+1) + newLine + "    " + playersList.get(i) + newLine + "    " +
                    playersList.get(i+quantity/2) + newLine;
            playersText.append(nextTeam);

            if (quantity%2 == 1 && i == quantity/2 -1) {
                playersText.append("    ").append(playersList.get(quantity-1));
            }

            playersText.append(newLine);
            i += 1;
        }

        // playersText.append(newLine).append("Empieza ").append(playersList.get((int) (Math.random()*(playersList.size()-1))));
        //playersText.append(newLine).append("Empieza ").append(playersList.get(0));
        ((TextView)findViewById(R.id.nombres)).setText(playersText.toString());
        ((TextView)findViewById(R.id.source)).setText("");
        Button b = (Button) v;
        b.setText("REHACER LOS EQUIPOS");

        ((Button)findViewById(R.id.confirmButton)).setEnabled(true);


    }

    public void reset (View v){
        teams.erasePlayers();
        teams.eraseWords();
        ((TextView)findViewById(R.id.source)).setText("");
        ((TextView)findViewById(R.id.nombres)).setText("");
        ((Button)findViewById(R.id.nameButton)).setText("AÑADIR");
        ((Button)findViewById(R.id.equiposButton)).setText("CREAR EQUIPOS");
        ((Button)findViewById(R.id.resetButton)).setEnabled(false);
        ((Button)findViewById(R.id.equiposButton)).setEnabled(false);
        ((Button)findViewById(R.id.confirmButton)).setEnabled(false);
        ((Button)findViewById(R.id.nameButton)).setEnabled(true);
        contador = 0;
    }

    public void wordsActivity (View v){
        Intent i = new Intent(this,equiposActivity.class);
        Bundle sentBundle = new Bundle();
        sentBundle.putSerializable("teamsKey", (Serializable) teams);
        i.putExtras(sentBundle);
        startActivity(i);


    }

    public void developper (View v){

        String[] players={"Alvaro","Anna","Lucas","Miquel"};
        String[] words={"Arbol","Plexiglas","Orbe","Uniforme","China","Mango","Unos","Anal","Implosion","Cable","Falda","Insecto"};
        teams teams = new teams();
        teams.teamBuilder(Arrays.asList(players), Arrays.asList(words));
        Intent i = new Intent(this,timerActivity.class);
        Bundle sentBundle = new Bundle();
        sentBundle.putSerializable("teamsKey", (Serializable) teams);
        i.putExtras(sentBundle);
        startActivity(i);


        /*teams.addPlayer("Alvaro");
        teams.addPlayer("Anna");
        teams.addPlayer("Lucas");
        teams.addPlayer("Miquel");
        teams.addWord("");
        teams.addWord("");
        teams.addWord("");
        teams.addWord("");

         */

    }
}