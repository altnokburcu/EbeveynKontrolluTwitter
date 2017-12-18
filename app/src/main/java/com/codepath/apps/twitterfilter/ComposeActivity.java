package com.codepath.apps.twitterfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.twitterfilter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    @BindView(R.id.etCompose) EditText etCompose;
    @BindView(R.id.btnTweet) Button btnTweet;
    @BindView(R.id.tvReplyBlurb) TextView tvReplyBlurb;

    TwitterClient client;

    long replyingToTweet;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkText();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        ButterKnife.bind(this);

        client = TwitterApp.getRestClient();

        etCompose.addTextChangedListener(textWatcher);

        Intent in = getIntent();
        boolean reply = in.getBooleanExtra("reply", false);

        // if replying to a previous tweet,
        if (reply) {
            // extract previous tweet
            Tweet replyingTo = (Tweet) Parcels.unwrap(in.getParcelableExtra("tweet"));

            // append @handle to which reply is being sent to response
            etCompose.setText(String.format("@" + replyingTo.user.screenName), TextView.BufferType.EDITABLE);

            // get id of previous tweet to send in POST network call
            replyingToTweet = replyingTo.uid;

            // show reply blurb
            tvReplyBlurb.setVisibility(View.VISIBLE);
            // set reply blurb text
            tvReplyBlurb.setText(String.format("Replying to @" + replyingTo.user.screenName), TextView.BufferType.EDITABLE);
        } else {
            // no previous tweet id
            replyingToTweet = -1;

            // hide reply blurb
            tvReplyBlurb.setVisibility(View.GONE);
        }

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make network request to post data from Twitter API
                // this jumps to TwitterClient
                client.sendTweet(etCompose.getText().toString(), replyingToTweet, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("TwitterClient", response.toString());

                        try {
                            Tweet tweet = Tweet.fromJSON(response, "home");

                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));

                            // return to required activity
                            setResult(RESULT_OK, intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.d("TwitterClient", response.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("TwitterClient", responseString);
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("TwitterClient", throwable.getMessage());
                        throwable.printStackTrace();            }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Log.d("TwitterClient", throwable.getMessage());
                        throwable.printStackTrace();            }
                });
            }
        });

        checkText();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void checkText() {
        String text = etCompose.getText().toString();

        if (text.length() == 0 || text.length() > 140) {
            btnTweet.setEnabled(false);
        }
        else {
            btnTweet.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
