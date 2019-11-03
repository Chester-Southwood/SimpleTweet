package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

    public String body;
    public String created_at;
    public User user;
    public long id;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.created_at = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");

        return tweet;

    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweetList = new ArrayList<Tweet>();
        for(int i = 0; i < jsonArray.length(); i++) {
            tweetList.add(fromJson(jsonArray.getJSONObject(i)));
            Log.d("Adding eleemnets", "adding elements");
        }
        Log.d("Returning array json " + jsonArray.length() + "", "Returning json array");
        return tweetList;
    }
}
