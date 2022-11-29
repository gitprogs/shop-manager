// Generated by view binder compiler. Do not edit!
package com.network.shopmanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.network.shopmanager.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView ivMore;

  @NonNull
  public final ImageView ivToggle;

  private FragmentHomeBinding(@NonNull LinearLayout rootView, @NonNull ImageView ivMore,
      @NonNull ImageView ivToggle) {
    this.rootView = rootView;
    this.ivMore = ivMore;
    this.ivToggle = ivToggle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iv_more;
      ImageView ivMore = ViewBindings.findChildViewById(rootView, id);
      if (ivMore == null) {
        break missingId;
      }

      id = R.id.iv_toggle;
      ImageView ivToggle = ViewBindings.findChildViewById(rootView, id);
      if (ivToggle == null) {
        break missingId;
      }

      return new FragmentHomeBinding((LinearLayout) rootView, ivMore, ivToggle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
