// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitterfilter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ComposeActivity_ViewBinding implements Unbinder {
  private ComposeActivity target;

  @UiThread
  public ComposeActivity_ViewBinding(ComposeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ComposeActivity_ViewBinding(ComposeActivity target, View source) {
    this.target = target;

    target.etCompose = Utils.findRequiredViewAsType(source, R.id.etCompose, "field 'etCompose'", EditText.class);
    target.btnTweet = Utils.findRequiredViewAsType(source, R.id.btnTweet, "field 'btnTweet'", Button.class);
    target.tvReplyBlurb = Utils.findRequiredViewAsType(source, R.id.tvReplyBlurb, "field 'tvReplyBlurb'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ComposeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etCompose = null;
    target.btnTweet = null;
    target.tvReplyBlurb = null;
  }
}
