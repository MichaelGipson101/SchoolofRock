package com.example.schoolofrock;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //declare intent
    Intent intent;

    //delcare editTexts
    EditText voterEditText;

    Spinner tracklistSpinner;

    //declare DBHandler
    DBHandler dbHandler;

    String tracklist;

    int vote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        voterEditText = (EditText) findViewById(R.id.voterEditText);

        dbHandler = new DBHandler(this, null);

        tracklistSpinner = (Spinner) findViewById(R.id.tracklistSpinner);

        //initialize arrayadapter with values in quantities string-array
        //and stylize it with style defined by simple spinner item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tracklist, android.R.layout.simple_spinner_item);

        //further stylize the arrayadapter
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //set the arrayadapter on the spinner
        tracklistSpinner.setAdapter(adapter);

        //register an onitemselectedlistener to spinner
        tracklistSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the menu item selected
        switch (item.getItemId()) {
            case R.id.like_button :
                vote = 1;
                return true;
            case R.id.dislike_button :
                vote = 0;
                return true;
            case R.id.count_button :
                // Display toast that has count of CIT students
                Toast.makeText(this, getMessage(1, 0), Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when a menu-item in the overflow menu is selected.
     * @param like selected song's likes
     * @param dislike selected song's dislikes
     * @return String that contains count of the number of students who have the selected major.
     */
    public String getMessage (Integer like, Integer dislike) {
        int count = dbHandler.getCountLikes(like);
        int count2 = dbHandler.getCountLikes(dislike);
        return (count + " likes." + "/n" + count2 + " dislikes.");
    }

    /**
     * This method gets called when the add button in the action bar gets clicked
     * @param menuItem add liked menu item
     */
    public void addLikedSong(MenuItem menuItem) {
        //get data input in editTexts and store it in Strings
        String voter = voterEditText.getText().toString();
        int like = vote;

        //trim strings and see if they are equal to empty string
        if (voter.trim().equals("")  || tracklist.trim().equals("")){
            //display toast
            Toast.makeText(this, "Please enter a song and name!", Toast.LENGTH_LONG).show();
        } else {
            //add list to DB
            dbHandler.addVote(voter, tracklist, vote);
            //display Toast
            Toast.makeText(this, "Vote Cast!", Toast.LENGTH_LONG).show();

        }
    }
    /**
     * This method gets called when the add button in the action bar gets clicked
     * @param menuItem adddisliked menu item
     */
    public void addDislikedSong(MenuItem menuItem) {
        //get data input in editTexts and store it in Strings
        String voter = voterEditText.getText().toString();
        int dislike = vote;

        //trim strings and see if they are equal to empty string
        if (voter.trim().equals("") || tracklist.trim().equals("")) {
            //display toast
            Toast.makeText(this, "Please enter a song and name!", Toast.LENGTH_LONG).show();
        } else {
            //add list to DB
            dbHandler.addVote(voter, tracklist, vote);
            //display Toast
            Toast.makeText(this, "Vote Cast!", Toast.LENGTH_LONG).show();

        }
    }
    /**
     * This method gets called when an item in the spinner is selected
     * @param parent Spinner adapterview
     * @param view add item view
     * @param position position of item that was selected in the spinner
     * @param id database id of the selected item in the spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tracklist = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}