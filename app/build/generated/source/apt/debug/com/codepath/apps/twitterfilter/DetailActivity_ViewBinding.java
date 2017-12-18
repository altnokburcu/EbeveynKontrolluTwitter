// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitterfilter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailActivity_ViewBinding implements Unbinder {
  private DetailActivity target;

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target, View source) {
    this.target = target;

    target.ivProfileImage = Utils.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivProfileImage'", ImageView.class);
    target.tvUsername = Utils.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
    target.tvHandle = Utils.findRequiredViewAsType(source, R.id.tvHandle, "field 'tvHandle'", TextView.class);
    target.tvBody = Utils.findRequiredViewAsType(source, R.id.tvBody, "field 'tvBody'", TextView.class);
    target.ivMediaImage = Utils.findRequiredViewAsType(source, R.id.ivMediaImage, "field 'ivMediaImage'", ImageView.class);
    target.tvTimestamp = Utils.findRequiredViewAsType(source, R.id.tvTimestamp, "field 'tvTimestamp'", TextView.class);
    target.btnReply = Utils.findRequiredViewAsType(source, R.id.btnReply, "field 'btnReply'", ImageButton.class);
    target.btnRetweet = Utils.findRequiredViewAsType(source, R.id.btnRetweet, "field 'btnRetweet'", ImageButton.class);
    target.btnFav = Utils.findRequiredViewAsType(source, R.id.btnFav, "field 'btnFav'", ImageButton.class);
    target.tvRTCount = Utils.findRequiredViewAsType(source, R.id.tvRTCount, "field 'tvRTCount'", TextView.class);
    target.tvFavCount = Utils.findRequiredViewAsType(source, R.id.tvFavCount, "field 'tvFavCount'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivProfileImage = null;
    target.tvUsername = null;
    target.tvHandle = null;
    target.tvBody = null;
    target.ivMediaImage = null;
    target.tvTimestamp = null;
    target.btnReply = null;
    target.btnRetweet = null;
    target.btnFav = null;
    target.tvRTCount = null;
    target.tvFavCount = null;
  }
}
