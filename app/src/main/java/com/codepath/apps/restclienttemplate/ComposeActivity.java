package com.codepath.apps.restclienttemplate;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    int      MAX_TWEET_LENGTH = 140;
    EditText etCompose;
    Button   btnTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etCompose = findViewById(R.id.etCompose);
        btnTweet  = findViewById(R.id.buttonTweet);

        // Set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tweetContent = etCompose.getText().toString().trim();
                //Make a api call to twitter to publish tweet
                if(tweetContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet cannot be submitted if empty!", Toast.LENGTH_LONG).show();
                } else if (tweetContent.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet is too LONG!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
