// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitterfilter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TimelineActivity_ViewBinding implements Unbinder {
  private TimelineActivity target;

  @UiThread
  public TimelineActivity_ViewBinding(TimelineActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TimelineActivity_ViewBinding(TimelineActivity target, View source) {
    this.target = target;

    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewPager'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TimelineActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewPager = null;
  }
}
