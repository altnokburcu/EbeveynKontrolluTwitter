package com.codepath.apps.twitterfilter;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/scribejava/scribejava/tree/master/scribejava-apis/src/main/java/com/github/scribejava/apis
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // base API URL
	public static final String REST_CONSUMER_KEY = "eD2vUsTUtSNycV4Q4to5N2F3y";
	public static final String REST_CONSUMER_SECRET = "nhyMS9Zj83Dez5S3vEFsIBuUapwpTAg8zQhKbJJTFUoXvxOvgP";

	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

	// See https://developer.chrome.com/multidevice/android/intents
	public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

	public TwitterClient(Context context) {
		super(context, REST_API_INSTANCE,
				REST_URL,
				REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET,
				String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
						context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
	}
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getHomeTimeline(long maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (maxId > 1) {
			params.put("max_id", maxId);
		}
		client.get(apiUrl, params, handler);
	}

	public void sendTweet(String message, long status, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		// specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("status", message);
		// check previous tweet id to see if this is a reply to a previous tweet
		if (status != -1) {
			// notify POST that this is indeed a reply
			params.put("in_reply_to_status_id", status);
		}
		client.post(apiUrl, params, handler);
	}

	public void retweet(long id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl(String.format("statuses/retweet/" + id + ".json"));
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post(apiUrl, params, handler);
	}

	public void unRetweet(long id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl(String.format("statuses/unretweet/" + id + ".json"));
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post(apiUrl, params, handler);
	}

	public void favorite(long id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/create.json");
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post(apiUrl, params, handler);
	}

	public void unFavorite(long id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/destroy.json");
		RequestParams params = new RequestParams();
		params.put("id", id);
		client.post(apiUrl, params, handler);
	}

	public void getMentionsTimeline(long maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (maxId > 1) {
			params.put("max_id", maxId);
		}
		client.get(apiUrl, params, handler);
	}

	public void getUserTimeline(long maxId, String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		params.put("count", 25);
		if (maxId > 1) {
			params.put("max_id", maxId);
		}
		client.get(apiUrl, params, handler);
	}

	public void getUserInfo(String screenName, AsyncHttpResponseHandler handler) {
		if (screenName == null) {
			String apiUrl = getApiUrl("account/verify_credentials.json");
			client.get(apiUrl, null, handler);
		} else {
			String apiUrl = getApiUrl("users/show.json");
			RequestParams params = new RequestParams();
			params.put("screen_name", screenName);
			client.get(apiUrl, params, handler);
		}
	}

	public void getSearchResults(String query, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("search/tweets.json");
		RequestParams params = new RequestParams();
		params.put("q", query);
		client.get(apiUrl, params, handler);
	}

	public void getFollowersList(long uid, long cursor, AsyncHttpResponseHandler handler) {
		if (cursor != 0L) {
			String apiUrl = getApiUrl("followers/list.json");
			RequestParams params = new RequestParams();
			params.put("user_id", uid);
			params.put("cursor", cursor);
			client.get(apiUrl, params, handler);
		}
	}

	public void getFriendsList(long uid, long cursor, AsyncHttpResponseHandler handler) {
		if (cursor != 0L) {
			String apiUrl = getApiUrl("friends/list.json");
			RequestParams params = new RequestParams();
			params.put("user_id", uid);
			params.put("cursor", cursor);
			client.get(apiUrl, params, handler);
		}
	}
}
