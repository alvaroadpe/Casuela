package com.alvaroadpe.casuela;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class teams implements Serializable {
    // Instance Variables
    List<String> players;
    int quantity;
    List<String> words;
    List<Integer> score;

    // Constructor Declaration of Class
    public teams()
    {
        this.players = new ArrayList<String>();
        this.quantity = 0;
        this.words = new ArrayList<String>();
        this.score = new ArrayList<Integer>();
    }

    public teams teamBuilder(List<String> players, List<String> words){
        this.players = players;
        this.quantity = players.size();
        this.words = words;
        this.score = new ArrayList<Integer>(Collections.nCopies(quantity, 0));

        return this;
    }

    // method 1
    public List<String> getPlayersList()
    {
        return players;
    }

    // method 2
    public int getQuantity()
    {
        return quantity;
    }

    // method 3
    public void addPlayer(String playerName)
    {
        players.add(0,playerName);
        quantity = players.size();
        score.add(quantity-1,0);
    }

    // method 4
    public void erasePlayers()
    {
        players.clear();
        quantity = 0;
        score.clear();
    }

    // method 5
    public List<String> getWordsList()
    {
        return words;
    }


    // method 6
    public void addWord(String word)
    {
        words.add(0,word);
    }

    // method 7
    public void eraseWords()
    {
        words.clear();
    }

    public void shuffleWords() {Collections.shuffle(words);}

    public void increaseScore(int playerPosition){
        score.set(playerPosition, score.get(playerPosition) + 1);
    }

    public int getScore(int playerPosition){
        return score.get(playerPosition);
    }


    /*
    @Override
    public String toString()
    {
        return("Hi my name is "+ this.getName()+
                ".\nMy breed,age and color are " +
                this.getBreed()+"," + this.getAge()+
                ","+ this.getColor());
    }

    public static void main(String[] args)
    {
        Dog tuffy = new Dog("tuffy","papillon", 5, "white");
        System.out.println(tuffy.toString());


    }

     */
}