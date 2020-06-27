package com.example.yapily_app.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yapily_app.R;
import com.example.yapily_app.activities.BaseActivity;
import com.example.yapily_app.adapters.ConnectedAccountsAdapter;
import com.example.yapily_app.models.BankAccount;
import com.example.yapily_app.repositories.AccountRepository;
import com.example.yapily_app.repositories.UserRepository;
import com.example.yapily_app.utilities.Authentication;
import com.example.yapily_app.utilities.ItemClickListener;
import com.example.yapily_app.view_models.AccountViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectedAccountsFragment extends BaseFragment implements ItemClickListener {

    @BindView(R.id.accounts_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.connect_account)
    Button connectAccount;

    @BindView(R.id.shimmer_view_accounts)
    ShimmerFrameLayout shimmerView;

    private AccountViewModel accountViewModel;
    private ConnectedAccountsAdapter accountsAdapter;

    public ConnectedAccountsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connected_account, container, false);

        ButterKnife.bind(this, view);

        shimmerView.startShimmerAnimation();

        connectAccount.setVisibility(View.INVISIBLE);
        connectAccount.setOnClickListener(button -> {
            ((BaseActivity) getActivity()).replaceFragment(R.id.main_fragment, new InstitutionsFragment());
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.accountViewModel = ViewModelProviders.of(this)
                .get(AccountViewModel.class);
        this.accountViewModel = ViewModelProviders.of(this)
                .get(AccountViewModel.class);
        this.accountsAdapter = new ConnectedAccountsAdapter(this);

        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setAdapter(this.accountsAdapter);

        loadAccounts();
    }

    @Override
    public void onItemClick(View view, int position) {
        new AccountRepository().reauthoriseAccountRequest().observe(this, account -> {
            if (account != null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(account.getAuthorisationUrl()));
                startActivity(browserIntent);
            } else {
                makeToast("Something went wrong.");
            }
        });
    }

    @Override
    public void onItemHeld(View view, int position) {
        PopupMenu popup = new PopupMenu(this.getContext(), view);
        popup.inflate(R.menu.options_menu);
        popup.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.revoke_access) {
                BankAccount bankAccount = accountsAdapter.getAccounts().get(position);
                new UserRepository().deleteConsent(bankAccount.getConsentId()).observe(getActivity(), deleteConsentResponse -> {
                    if (deleteConsentResponse != null) {
                        loadAccounts();
                    } else {
                        makeToast("Something went wrong deleting");
                    }
                });
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void loadAccounts() {
        this.accountViewModel.getAccounts().observe(this, accounts -> {
            if (accounts != null) {
                if (accounts.size() == 0) {
                    ((BaseActivity) getActivity()).replaceFragment(R.id.main_fragment, new InstitutionsFragment());
                } else {
                    this.accountsAdapter.setAccounts(accounts);
                    shimmerView.stopShimmerAnimation();
                    shimmerView.setVisibility(View.GONE);
                    connectAccount.setVisibility(View.VISIBLE);
                }
            } else {
                makeToast("Could not load accounts.");
            }
        });
    }
}
