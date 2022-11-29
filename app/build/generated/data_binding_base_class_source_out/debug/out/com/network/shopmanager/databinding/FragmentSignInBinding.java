// Generated by view binder compiler. Do not edit!
package com.network.shopmanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.network.shopmanager.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSignInBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnSignin;

  @NonNull
  public final TextInputEditText etLogin;

  @NonNull
  public final TextInputEditText etPassword;

  @NonNull
  public final TextInputLayout lLoginInput;

  @NonNull
  public final TextInputLayout lPasswordInput;

  @NonNull
  public final TextView title;

  @NonNull
  public final TextView tvSignup;

  private FragmentSignInBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnSignin,
      @NonNull TextInputEditText etLogin, @NonNull TextInputEditText etPassword,
      @NonNull TextInputLayout lLoginInput, @NonNull TextInputLayout lPasswordInput,
      @NonNull TextView title, @NonNull TextView tvSignup) {
    this.rootView = rootView;
    this.btnSignin = btnSignin;
    this.etLogin = etLogin;
    this.etPassword = etPassword;
    this.lLoginInput = lLoginInput;
    this.lPasswordInput = lPasswordInput;
    this.title = title;
    this.tvSignup = tvSignup;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSignInBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSignInBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_sign_in, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSignInBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_signin;
      Button btnSignin = ViewBindings.findChildViewById(rootView, id);
      if (btnSignin == null) {
        break missingId;
      }

      id = R.id.et_login;
      TextInputEditText etLogin = ViewBindings.findChildViewById(rootView, id);
      if (etLogin == null) {
        break missingId;
      }

      id = R.id.et_password;
      TextInputEditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.l_login_input;
      TextInputLayout lLoginInput = ViewBindings.findChildViewById(rootView, id);
      if (lLoginInput == null) {
        break missingId;
      }

      id = R.id.l_password_input;
      TextInputLayout lPasswordInput = ViewBindings.findChildViewById(rootView, id);
      if (lPasswordInput == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      id = R.id.tv_signup;
      TextView tvSignup = ViewBindings.findChildViewById(rootView, id);
      if (tvSignup == null) {
        break missingId;
      }

      return new FragmentSignInBinding((ConstraintLayout) rootView, btnSignin, etLogin, etPassword,
          lLoginInput, lPasswordInput, title, tvSignup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
