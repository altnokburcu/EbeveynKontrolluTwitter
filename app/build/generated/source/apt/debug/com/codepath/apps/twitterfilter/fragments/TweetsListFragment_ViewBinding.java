// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitterfilter.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.codepath.apps.twitterfilter.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TweetsListFragment_ViewBinding implements Unbinder {
  private TweetsListFragment target;

  @UiThread
  public TweetsListFragment_ViewBinding(TweetsListFragment target, View source) {
    this.target = target;

    target.rvTweets = Utils.findRequiredViewAsType(source, R.id.rvTweet, "field 'rvTweets'", RecyclerView.class);
    target.swipeContainer = Utils.findRequiredViewAsType(source, R.id.swipeContainer, "field 'swipeContainer'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TweetsListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvTweets = null;
    target.swipeContainer = null;
  }
}
