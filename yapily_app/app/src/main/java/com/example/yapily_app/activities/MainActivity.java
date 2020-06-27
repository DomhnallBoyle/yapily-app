package com.example.yapily_app.activities;

import android.os.Bundle;

import com.example.yapily_app.R;
import com.example.yapily_app.fragments.ConnectedAccountsFragment;
import com.example.yapily_app.fragments.LoginFragment;
import com.example.yapily_app.utilities.Authentication;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Authentication authentication = Authentication.getInstance();
        if (authentication.getToken() == null)
            replaceFragment(R.id.main_fragment, new LoginFragment());
        else {
            // come here from yapily API redirect
            replaceFragment(R.id.main_fragment, new ConnectedAccountsFragment());
        }
    }
}
