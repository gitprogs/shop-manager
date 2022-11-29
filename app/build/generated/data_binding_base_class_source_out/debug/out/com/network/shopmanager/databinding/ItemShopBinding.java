// Generated by view binder compiler. Do not edit!
package com.network.shopmanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.network.shopmanager.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemShopBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView ivMore;

  @NonNull
  public final ImageView ivShop;

  @NonNull
  public final TextView tvShopName;

  private ItemShopBinding(@NonNull LinearLayout rootView, @NonNull ImageView ivMore,
      @NonNull ImageView ivShop, @NonNull TextView tvShopName) {
    this.rootView = rootView;
    this.ivMore = ivMore;
    this.ivShop = ivShop;
    this.tvShopName = tvShopName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemShopBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemShopBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_shop, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemShopBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iv_more;
      ImageView ivMore = ViewBindings.findChildViewById(rootView, id);
      if (ivMore == null) {
        break missingId;
      }

      id = R.id.iv_shop;
      ImageView ivShop = ViewBindings.findChildViewById(rootView, id);
      if (ivShop == null) {
        break missingId;
      }

      id = R.id.tv_shop_name;
      TextView tvShopName = ViewBindings.findChildViewById(rootView, id);
      if (tvShopName == null) {
        break missingId;
      }

      return new ItemShopBinding((LinearLayout) rootView, ivMore, ivShop, tvShopName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
