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

public class UsersListFragment_ViewBinding implements Unbinder {
  private UsersListFragment target;

  @UiThread
  public UsersListFragment_ViewBinding(UsersListFragment target, View source) {
    this.target = target;

    target.rvUsers = Utils.findRequiredViewAsType(source, R.id.rvUser, "field 'rvUsers'", RecyclerView.class);
    target.swipeContainer = Utils.findRequiredViewAsType(source, R.id.swipeContainer, "field 'swipeContainer'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UsersListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvUsers = null;
    target.swipeContainer = null;
  }
}
