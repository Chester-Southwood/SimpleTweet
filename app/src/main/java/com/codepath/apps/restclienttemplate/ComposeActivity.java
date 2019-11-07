package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import okhttp3.Headers;
import org.parceler.Parcel;
import org.parceler.Parcels;


public class ComposeActivity extends AppCompatActivity {

    int           MAX_TWEET_LENGTH = 140;
    EditText      etCompose;
    Button        btnTweet;
    TwitterClient twitterClient;
    private static final String TAG = "ComposeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        twitterClient = TwitterApplication.getRestClient(this);

        etCompose     = findViewById(R.id.etCompose);
        etCompose.clearComposingText();
        etCompose.setText("");
        btnTweet      = findViewById(R.id.buttonTweet);
        btnTweet      = findViewById(R.id.buttonTweet);

        final TextView etValue = (TextView) findViewById(R.id.wordCount);
        etValue.setText(MAX_TWEET_LENGTH + " Characters");

        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right after the text has changed
                etValue.setText(MAX_TWEET_LENGTH - s.toString().trim().length() + " Characters");
                //etValue.setText(s.toString().length() + " Characters");
                if(s.toString().length() > MAX_TWEET_LENGTH) {
                    etValue.setTextColor(Color.RED);
                    btnTweet.setEnabled(false);
                } else {
                    etValue.setTextColor(Color.BLACK);
                    btnTweet.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tweetContent = etCompose.getText().toString().trim();
                //Make a api call to twitter to publish tweet
                if(tweetContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet cannot be submitted if empty!", Toast.LENGTH_LONG).show();
                } else if (tweetContent.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet is too LONG! Length must be less than or equal to " + MAX_TWEET_LENGTH, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ComposeActivity.this, etCompose.getText().toString(), Toast.LENGTH_LONG).show();
                    twitterClient.publishTweet(etCompose.getText().toString(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i(TAG, "onSucess to publish tweets");
                            try {
                                Tweet tweet = Tweet.fromJson(json.jsonObject);
                                Log.i(TAG, "PublishTweet");
                                Intent intent = new Intent();
                                intent.putExtra("tweet", Parcels.wrap(tweet));
                                setResult(RESULT_OK, intent);
                                //Closed the activity, pass data to response
                                finish();
                            } catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e(TAG, "onFailure to publish tweet", throwable);
                        }
                    });
                }
            }
        });
    }

}
