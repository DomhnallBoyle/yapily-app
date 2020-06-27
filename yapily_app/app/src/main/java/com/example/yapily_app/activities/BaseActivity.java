package com.example.yapily_app.activities;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yapily_app.R;
import com.example.yapily_app.fragments.BaseFragment;
import com.example.yapily_app.fragments.LoginFragment;
import com.example.yapily_app.utilities.Authentication;

public class BaseActivity extends AppCompatActivity {

    public void replaceFragment(int fragmentContainer, BaseFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(fragmentContainer, fragment)
                .commit();
    }

    public void replaceFragment(int fragmentContainer, BaseFragment fragment, String addToBackstack) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(fragmentContainer, fragment)
                .addToBackStack(addToBackstack)
                .commit();
    }

    protected void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void logout() {
        Authentication auth = Authentication.getInstance();
        auth.remove();
        replaceFragment(R.id.main_fragment, new LoginFragment());
    }
}
