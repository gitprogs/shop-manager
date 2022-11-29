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
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.network.shopmanager.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSignUpBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnSignup;

  @NonNull
  public final TextInputEditText etAddress;

  @NonNull
  public final TextInputEditText etLogin;

  @NonNull
  public final TextInputEditText etName;

  @NonNull
  public final TextInputEditText etPassword1;

  @NonNull
  public final TextInputEditText etPassword2;

  @NonNull
  public final TextInputEditText etPhone1;

  @NonNull
  public final TextInputEditText etPhone2;

  @NonNull
  public final TextInputEditText etSurname;

  @NonNull
  public final NestedScrollView scrollview;

  @NonNull
  public final TextView title;

  private FragmentSignUpBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnSignup,
      @NonNull TextInputEditText etAddress, @NonNull TextInputEditText etLogin,
      @NonNull TextInputEditText etName, @NonNull TextInputEditText etPassword1,
      @NonNull TextInputEditText etPassword2, @NonNull TextInputEditText etPhone1,
      @NonNull TextInputEditText etPhone2, @NonNull TextInputEditText etSurname,
      @NonNull NestedScrollView scrollview, @NonNull TextView title) {
    this.rootView = rootView;
    this.btnSignup = btnSignup;
    this.etAddress = etAddress;
    this.etLogin = etLogin;
    this.etName = etName;
    this.etPassword1 = etPassword1;
    this.etPassword2 = etPassword2;
    this.etPhone1 = etPhone1;
    this.etPhone2 = etPhone2;
    this.etSurname = etSurname;
    this.scrollview = scrollview;
    this.title = title;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_signup;
      Button btnSignup = ViewBindings.findChildViewById(rootView, id);
      if (btnSignup == null) {
        break missingId;
      }

      id = R.id.et_address;
      TextInputEditText etAddress = ViewBindings.findChildViewById(rootView, id);
      if (etAddress == null) {
        break missingId;
      }

      id = R.id.et_login;
      TextInputEditText etLogin = ViewBindings.findChildViewById(rootView, id);
      if (etLogin == null) {
        break missingId;
      }

      id = R.id.et_name;
      TextInputEditText etName = ViewBindings.findChildViewById(rootView, id);
      if (etName == null) {
        break missingId;
      }

      id = R.id.et_password1;
      TextInputEditText etPassword1 = ViewBindings.findChildViewById(rootView, id);
      if (etPassword1 == null) {
        break missingId;
      }

      id = R.id.et_password2;
      TextInputEditText etPassword2 = ViewBindings.findChildViewById(rootView, id);
      if (etPassword2 == null) {
        break missingId;
      }

      id = R.id.et_phone1;
      TextInputEditText etPhone1 = ViewBindings.findChildViewById(rootView, id);
      if (etPhone1 == null) {
        break missingId;
      }

      id = R.id.et_phone2;
      TextInputEditText etPhone2 = ViewBindings.findChildViewById(rootView, id);
      if (etPhone2 == null) {
        break missingId;
      }

      id = R.id.et_surname;
      TextInputEditText etSurname = ViewBindings.findChildViewById(rootView, id);
      if (etSurname == null) {
        break missingId;
      }

      id = R.id.scrollview;
      NestedScrollView scrollview = ViewBindings.findChildViewById(rootView, id);
      if (scrollview == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      return new FragmentSignUpBinding((ConstraintLayout) rootView, btnSignup, etAddress, etLogin,
          etName, etPassword1, etPassword2, etPhone1, etPhone2, etSurname, scrollview, title);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}