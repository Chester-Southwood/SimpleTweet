package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetsAdapter  extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        if(tweets != null)
            this.tweets  = tweets;
        else
            this.tweets = new ArrayList<Tweet>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Pass in the context and list of tweets

    // For each row, inflate the layout

    // Bind values based on the position of the element

    //Define a viewholder

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfileImage;
        TextView  tvBody;
        TextView  tvScreenName;
        TextView  tvTimeStamp;
        TextView  tvNickName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody         = itemView.findViewById(R.id.tvBody);
            tvScreenName   = itemView.findViewById(R.id.tvNickname); //TODO FIX NAME ISSUE
            tvTimeStamp    = itemView.findViewById(R.id.tvTimeStamp);
            tvNickName     = itemView.findViewById(R.id.tvScreenName);
        }

        public void bind(Tweet tweet) {
            Log.d("BIND", "Attempting to bind right now");
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvTimeStamp.setText(tweet.getFormmatedDate());
            tvNickName.setText(tweet.user.name);
            Log.d("TIMESTAMP", tweet.getFormmatedDate());
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
        }

    }

    public void addAll(List <Tweet> list) {
        this.tweets.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

}


