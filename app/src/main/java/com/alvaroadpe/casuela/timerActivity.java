package com.alvaroadpe.casuela;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.casuela.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class timerActivity extends AppCompatActivity {

    private teams teams;
    private List<String> playerList;
    private List<String> wordList;
    int wordNumber = 0;
    int playerNumber = 0;
    int round = 1;
    int seconds = 30;
    Boolean gameEnded = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);



        teams = (teams) getIntent().getExtras().getSerializable("teamsKey");
        teams.shuffleWords();
        wordList = new ArrayList(teams.getWordsList());
        playerList = new ArrayList(teams.getPlayersList());


        resetTimerActivity();

        Log.d("informacion","timer activity iniciada");
    }




    @SuppressLint("SetTextI18n")
    public void startTimer (View v){
        if(!gameEnded) {
            ((Button) findViewById(R.id.startButton)).setEnabled(false);
            ((Button) findViewById(R.id.scoreButton)).setEnabled(false);
            startTimer();
            ((TextView) findViewById(R.id.wordTextView)).setText(wordList.get(wordNumber));
            ((Button) findViewById(R.id.nextWordButton)).setEnabled(true);
            ((Button) findViewById(R.id.guessedWordButton)).setEnabled(true);
        }
        else{
            Intent intentRestartGame = new Intent(this,MainActivity.class);
            startActivity(intentRestartGame);
        }
    }

    public void nextWord(View v){
        if(wordList.size()>0) {
            if (wordNumber < wordList.size() - 1) { wordNumber++; }
            else { wordNumber = 0; }
            ((TextView) findViewById(R.id.wordTextView)).setText(wordList.get(wordNumber));
        }
        else{
            wordNumber = 0;
            ((TextView) findViewById(R.id.wordTextView)).setText("");
            }
    }



    public void guessedWord(View v){
        if(wordList.size() > 0){
            wordList.remove(wordNumber);
            teams.increaseScore(playerNumber);
            wordNumber = 0;
            Collections.shuffle(wordList);
            if(wordList.size() > 0){
                ((TextView)findViewById(R.id.wordTextView)).setText(wordList.get(wordNumber));
            }
            else{

                if (round < 3){
                    wordList = new ArrayList(teams.getWordsList());
                    Collections.shuffle(wordList);
                    round++;

                    if(seconds == 0){seconds = 1;}
                    AlertDialog.Builder nextPlayerPopUp = new AlertDialog.Builder(timerActivity.this);
                    nextPlayerPopUp.setMessage("All the words have been guessed. You still have " + seconds +
                            " seconds for the next round. \n \n")
                            .setCancelable(false)
                            .setPositiveButton("Next Round", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int intDialogPositive) {
                                    //finish();
                                    dialogInterface.cancel();
                                }
                            })
                            .setNegativeButton("Read Rules", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int intDialogNegative) {
                                    AlertDialog.Builder nextPlayerPopUp2 = new AlertDialog.Builder(timerActivity.this);
                                    nextPlayerPopUp2.setMessage( roundName(round)[1] +
                                            System.getProperty("line.separator"))
                                            .setCancelable(false)
                                            .setPositiveButton("Start Round", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface2, int intDialogPositive2) {

                                                    dialogInterface2.cancel();
                                                }
                                            });

                                    AlertDialog title2 = nextPlayerPopUp2.create();
                                    title2.setTitle("Rules Round " + round + ": " + roundName(round)[0]);
                                    title2.show();

                                    dialogInterface.cancel();

                                }
                            });

                    AlertDialog title = nextPlayerPopUp.create();
                    title.setTitle("Change to Round " + round + ": " + roundName(round)[0]);
                    title.show();
                    cancelTimer();
                }

                else{restartGame();}


            }
        }
    }
    public void watchScoresButton(View v){watchScores();}
    public void watchScores (){
        List<String> playersList = teams.getPlayersList();
        int quantity = teams.getQuantity();

        StringBuilder playersText = new StringBuilder();
        int i = 0;
        while (i<quantity/2){
            /*String nextTeam = "Equipo " + (i+1) + ": \n    " +
                    teams.getPlayersList().get(i) + "  &  " +
                    teams.getPlayersList().get((i+quantity/2))
                    + (teams.getScore(i) + teams.getScore((i+quantity/2))) + " puntos"
                    + "\n \n";

             */
            playersText.append("Team " + (i+1) + ":                            " + "" +
                    (teams.getScore(i) + teams.getScore((i+quantity/2))) + " points" + "\n    " +
                    teams.getPlayersList().get(i) + "  &  " +
                    teams.getPlayersList().get((i+quantity/2)));

            if (quantity%2 == 1 && i == quantity/2 -1) {
                playersText.append("  &  " + playersList.get(quantity-1));
            }
            playersText.append("\n\n");
            //playersText.append("      "+ (teams.getScore(i) + teams.getScore((i+quantity/2))) + " puntos" + "\n \n");
            i += 1;
        }

        AlertDialog.Builder nextPlayerPopUp2 = new AlertDialog.Builder(timerActivity.this);
        nextPlayerPopUp2.setMessage(playersText.toString())
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface2, int intDialogPositive2) {
                        dialogInterface2.cancel();
                    }
                });

        AlertDialog title2 = nextPlayerPopUp2.create();
        title2.setTitle("Scoreboard");
        title2.show();
    }

    @SuppressLint("SetTextI18n")
    public  void resetTimerActivity (){
        if(seconds == 0) {seconds = 30;}
        ((TextView)findViewById(R.id.timerTextView)).setText(String.valueOf(seconds));
        ((TextView)findViewById(R.id.wordTextView)).setText("");
        ((Button)findViewById(R.id.startButton)).setEnabled(true);
        ((Button)findViewById(R.id.nextWordButton)).setEnabled(false);
        ((Button)findViewById(R.id.guessedWordButton)).setEnabled(false);
        ((Button)findViewById(R.id.scoreButton)).setEnabled(true);

        ((TextView)findViewById(R.id.roundTextView))
                .setText("Round " + round + ": " + roundName(round)[0]);
        ((TextView)findViewById(R.id.playerTurnTextView))
                .setText("Turn of " + playerList.get(playerNumber));

    }
    @SuppressLint("SetTextI18n")
    public void restartGame (){
        cancelTimer();

        ((Button)findViewById(R.id.startButton)).setEnabled(true);
        ((Button)findViewById(R.id.nextWordButton)).setEnabled(false);
        ((Button)findViewById(R.id.guessedWordButton)).setEnabled(false);
        ((Button)findViewById(R.id.scoreButton)).setEnabled(true);

        ((TextView)findViewById(R.id.roundTextView)).setText("End of the game");
        ((TextView)findViewById(R.id.playerTurnTextView)).setText("");
        ((TextView)findViewById(R.id.timerTextView)).setText("");
        ((TextView)findViewById(R.id.wordTextView)).setText("");
        ((Button)findViewById(R.id.startButton)).setText("Restart the game");

        watchScores();
    }

    public String[] roundName(int round){
        String name;
        String description;
        if(round == 1){
            name = "Description";
            description = "Explanation of the round: \n" +
                    "During this round you will have 30 seconds to describe the word that will appear in your screen to your teammate. " +
                    "The only words you cannot use are the ones with the same base as the word being described. " +
                    "If your teammate guesses the word, press the GUESSED button to earn a point and get a new word to guess. " +
                    "If you don't understand the word, press the SKIP button to switch words. " ;
        }
        else if(round == 2){
            name = "Single Word";
            description = "Explanation of the round: \n" +
                    "During this round, all the words from the previous round will reappear, " +
                    "But you can only use a single word to describe the word you teammate has to guess. " +
                    "The only words you cannot use are the ones with the same base as the word being described. " +
                    "If your teammate guesses the word, press the GUESSED button to earn a point and get a new word to guess. " +
                    "If you don't understand the word, press the SKIP button to switch words. " ;
        }
        else{
            name = "Charades";
            description = "Explanation of the round: \n" +
                    "During this round, all the words from the previous round will reappear, " +
                    "but you will have to act out the words. " +
                    "It is NOT allowed to talk or make any noises. " +
                    "If your teammate guesses the word, press the GUESSED button to earn a point and get a new word to guess. " +
                    "If you don't understand the word, press the SKIP button to switch words. " ;
        }
        return new String[]{name, description};
    }


    //Declare timer
    CountDownTimer cTimer = null;


    //start timer function
    public void startTimer() {
        cTimer = new CountDownTimer(seconds* 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                seconds--;
                ((TextView)findViewById(R.id.timerTextView)).setText(String.valueOf(seconds));

            }
            public void onFinish() {
                if (playerNumber < playerList.size() - 1) { playerNumber++; }
                else { playerNumber = 0; }

                AlertDialog.Builder nextPlayerPopUp = new AlertDialog.Builder(timerActivity.this);
                nextPlayerPopUp.setMessage("Time's up, and your turn is over :(" + System.getProperty("line.separator") +
                        "Hand the device to " + playerList.get(playerNumber)
                        + System.getProperty("line.separator") + System.getProperty("line.separator"))
                        .setCancelable(false)
                        .setPositiveButton("I am " + playerList.get(playerNumber), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //finish();
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog title = nextPlayerPopUp.create();
                title.setTitle("Round " + round + ": " + roundName(round)[0]);
                title.show();

                resetTimerActivity();
            }
        };
        cTimer.start();
    }


    //cancel timer
    public void cancelTimer() {
        if(cTimer!=null) {
            cTimer.cancel();
            resetTimerActivity();
        }
    }
}











    /*
    Timer timer = new Timer();
    int seconds = 30;
    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (seconds > 0){
                        --seconds;
                        ((TextView)findViewById(R.id.timerTextView)).setText(String.valueOf(seconds));
                        Log.d("informacion",String.valueOf(seconds));
                    }
                    else {
                        seconds = 30;
                        timer.cancel();
                        Log.d("informacion","terminado" + String.valueOf(seconds));
                    }
                }
            });
        }
    };

     */

//timer.scheduleAtFixedRate (timerTask,1000,100);





