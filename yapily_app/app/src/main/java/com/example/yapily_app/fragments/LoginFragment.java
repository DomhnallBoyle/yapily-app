package com.example.yapily_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.yapily_app.R;
import com.example.yapily_app.activities.BaseActivity;
import com.example.yapily_app.repositories.UserRepository;
import com.example.yapily_app.utilities.Authentication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.username)
    EditText usernameEditText;

    @BindView(R.id.login)
    Button loginButton;

    @BindView(R.id.create_user)
    Button createUserButton;

    private UserRepository userRepository;

    public LoginFragment() {
        this.userRepository = new UserRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        loginButton.setOnClickListener(button -> {
            String username = usernameEditText.getText().toString();

            if (!username.isEmpty()) {
                this.userRepository.login(username).observe(this, loginResponse -> {
                    if (loginResponse != null) {
                        login(username, loginResponse.getApplicationUserId(), loginResponse.getToken());
                    } else {
                        makeToast("Unable to login.");
                    }
                });
            } else {
                makeToast("Please enter a username.");
            }
        });

        createUserButton.setOnClickListener(button -> {
            String username = usernameEditText.getText().toString();

            if (!username.isEmpty()) {
                userRepository.createUser(username).observe(this, createUserResponse -> {
                    if (createUserResponse != null) {
                        login(username, createUserResponse.getApplicationUserId(), createUserResponse.getToken());
                    } else {
                        makeToast("Unable to create user.");
                    }
                });
            } else {
                makeToast("Please enter a username.");
            }
        });

        return view;
    }

    private void login(String username, String applicationUserId, String token) {
        Authentication authentication = Authentication.getInstance();
        authentication.setUsername(username);
        authentication.setApplicationUserId(applicationUserId);
        authentication.setToken(token);

        userRepository.getIsConnected().observe(this, connectedResponse -> {
            if (connectedResponse != null) {
                BaseFragment fragment;

                if (connectedResponse.getIsConnected()) {
                    fragment = new ConnectedAccountsFragment();
                } else {
                    fragment = new InstitutionsFragment();
                }

                ((BaseActivity) getActivity()).replaceFragment(R.id.main_fragment, fragment);
            } else {
                makeToast("Something went wrong.");
            }
        });

    }
}
