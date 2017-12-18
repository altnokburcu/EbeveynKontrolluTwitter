// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitterfilter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserAdapter$ViewHolder_ViewBinding implements Unbinder {
  private UserAdapter.ViewHolder target;

  @UiThread
  public UserAdapter$ViewHolder_ViewBinding(UserAdapter.ViewHolder target, View source) {
    this.target = target;

    target.ivProfileImage = Utils.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivProfileImage'", ImageView.class);
    target.tvUserName = Utils.findRequiredViewAsType(source, R.id.tvUserName, "field 'tvUserName'", TextView.class);
    target.tvHandle = Utils.findRequiredViewAsType(source, R.id.tvHandle, "field 'tvHandle'", TextView.class);
    target.tvTagline = Utils.findRequiredViewAsType(source, R.id.tvTagline, "field 'tvTagline'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivProfileImage = null;
    target.tvUserName = null;
    target.tvHandle = null;
    target.tvTagline = null;
  }
}
