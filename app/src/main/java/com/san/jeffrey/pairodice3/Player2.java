package com.san.jeffrey.pairodice3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.ConsumerIrManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jeffrey on 4/10/2015.
 */
public class Player2 extends ActionBarActivity {

    private FrameLayout die1, die2;
    private TextView player1, player2, totalRound;
    private Button roll, hold;
    private int score = 0; // needs to be 100 to win.
    private int round = 0, p1score, p2score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player2);

        Intent intent = getIntent();
        //boolean newGame = intent.getIntExtra("newgame", 0) == 0;
        p1score = intent.getIntExtra("p1score", 0);
        p2score = intent.getIntExtra("p2score", 0);
        score = p2score;
        Toast.makeText(this, "The score is: " + p1score, Toast.LENGTH_LONG).show(); // little view

        player1 = (TextView) findViewById(R.id.p1);
        player1.setText("P1: "+p1score);
        player2 = (TextView) findViewById(R.id.p2);
        player2.setText("P2: "+p2score);
        totalRound = (TextView) findViewById(R.id.round);
        totalRound.setText("Round: 0");

        die1 = (FrameLayout) findViewById(R.id.die1);
        die2 = (FrameLayout) findViewById(R.id.die2);

        roll = (Button) findViewById(R.id.button);

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {

                rollDice();
            }

        });

        hold = (Button) findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                score += round;
                if(score >= 100) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Player2.this).create();
                    alertDialog.setTitle("Player 2 Wins!");
                    alertDialog.setMessage("Congratulations, Player 2! You beat Player 1!\n" +
                            "\n" +
                            "Please play again.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "New Game",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    score = 0;
                                    Intent intent = new Intent(Player2.this, MainActivity.class);
                                    intent.putExtra("score", 0);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    dialog.dismiss();
                                    startActivity(intent);
                                }
                            });
                    alertDialog.show();
                }else {
                    Intent intent = new Intent(Player2.this, MainActivity.class); // this refers the the onClickListener
                    intent.putExtra("p2score", score); // takes information from one activity to another.
                    intent.putExtra("p1score", p1score);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
                round = 0;
            }

        });

    }


    // get two random numbers, change the dice to have the appropriate image
    public void rollDice(){
        int roll1 = 1 + (int) (6 * Math.random());
        int roll2 = 1 + (int) (6 * Math.random());


        setDie(roll1, die1);
        setDie(roll2, die2);

        if(roll1 == 1 || roll2 == 1){
            round = 0;
            hold.performClick();
        }else{
            round += roll1 + roll2;
            totalRound.setText("Round: "+round);
        }

    }

    // set appropriate image to FrameView for an int
    public void setDie(int value, FrameLayout die){
        Drawable pic = null;
        switch(value){
            case 1:
                pic = getResources().getDrawable(R.drawable.die_face_1);
                //die.setBackground(pic);
                break;
            case 2:
                pic = getResources().getDrawable(R.drawable.die_face_2);
                //die.setBackground(pic);
                break;
            case 3:
                pic = getResources().getDrawable(R.drawable.die_face_3);
                //die.setBackground(pic);
                break;
            case 4:
                pic = getResources().getDrawable(R.drawable.die_face_4);
                //die.setBackground(pic);
                break;
            case 5:
                pic = getResources().getDrawable(R.drawable.die_face_5);
                //die.setBackground(pic);
                break;
            case 6:
                pic = getResources().getDrawable(R.drawable.die_face_6);
                //die.setBackground(pic);
                break;
        }
        die.setBackground(pic);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void restart(){
        score = 0;
    }
}