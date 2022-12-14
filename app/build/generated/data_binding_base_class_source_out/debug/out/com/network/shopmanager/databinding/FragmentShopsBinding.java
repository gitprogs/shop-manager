// Generated by view binder compiler. Do not edit!
package com.network.shopmanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.network.shopmanager.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentShopsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final FloatingActionButton fabAddShop;

  @NonNull
  public final ImageView ivBackArrow;

  @NonNull
  public final LinearLayout lTop;

  @NonNull
  public final RecyclerView rvShops;

  private FragmentShopsBinding(@NonNull RelativeLayout rootView,
      @NonNull FloatingActionButton fabAddShop, @NonNull ImageView ivBackArrow,
      @NonNull LinearLayout lTop, @NonNull RecyclerView rvShops) {
    this.rootView = rootView;
    this.fabAddShop = fabAddShop;
    this.ivBackArrow = ivBackArrow;
    this.lTop = lTop;
    this.rvShops = rvShops;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentShopsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentShopsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_shops, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentShopsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.fab_add_shop;
      FloatingActionButton fabAddShop = ViewBindings.findChildViewById(rootView, id);
      if (fabAddShop == null) {
        break missingId;
      }

      id = R.id.iv_back_arrow;
      ImageView ivBackArrow = ViewBindings.findChildViewById(rootView, id);
      if (ivBackArrow == null) {
        break missingId;
      }

      id = R.id.l_top;
      LinearLayout lTop = ViewBindings.findChildViewById(rootView, id);
      if (lTop == null) {
        break missingId;
      }

      id = R.id.rv_shops;
      RecyclerView rvShops = ViewBindings.findChildViewById(rootView, id);
      if (rvShops == null) {
        break missingId;
      }

      return new FragmentShopsBinding((RelativeLayout) rootView, fabAddShop, ivBackArrow, lTop,
          rvShops);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
