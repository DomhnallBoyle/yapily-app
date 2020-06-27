package com.example.yapily_app.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yapily_app.R;
import com.example.yapily_app.activities.BaseActivity;
import com.example.yapily_app.adapters.InstitutionsAdapter;
import com.example.yapily_app.models.Institution;
import com.example.yapily_app.repositories.UserRepository;
import com.example.yapily_app.utilities.Authentication;
import com.example.yapily_app.utilities.ItemClickListener;
import com.example.yapily_app.view_models.AccountViewModel;
import com.example.yapily_app.view_models.InstitutionsViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstitutionsFragment extends BaseFragment implements ItemClickListener {

    @BindView(R.id.institutions_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.shimmer_view_institutions)
    ShimmerFrameLayout shimmerContainer;

    private InstitutionsViewModel institutionsViewModel;
    private AccountViewModel accountViewModel;
    private InstitutionsAdapter institutionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_institutions, container, false);

        ButterKnife.bind(this, view);

        shimmerContainer.startShimmerAnimation();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.institutionsViewModel = ViewModelProviders.of(this)
                .get(InstitutionsViewModel.class);
        this.accountViewModel = ViewModelProviders.of(this)
                .get(AccountViewModel.class);
        this.institutionsAdapter = new InstitutionsAdapter(this);

        this.recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        this.recyclerView.setAdapter(this.institutionsAdapter);

        this.institutionsViewModel.getInstitutions().observe(this, institutions -> {
            if (institutions != null) {
                this.institutionsAdapter.setInstitutions(institutions);
                shimmerContainer.stopShimmerAnimation();
                shimmerContainer.setVisibility(View.GONE);
            } else {
                makeToast("Could not load institutions.");
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Institution institution = this.institutionsAdapter.getInstitutions().get(position);

        accountViewModel.initiateAccountAuthorisationRequest(institution.getId(), Authentication.getInstance().getApplicationUserId()).observe(this, account -> {
            if (account != null) {
                Authentication.getInstance().setYapilyUserId(account.getYapilyUserId());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(account.getAuthorisationUrl()));
                startActivity(browserIntent);
            } else {
                makeToast("Something went wrong.");
            }
        });
    }

    @Override
    public void onItemHeld(View view, int position) {

    }

    @Override
    public void onResume() {
        super.onResume();

        new UserRepository().login(Authentication.getInstance().getUsername()).observe(this, loginResponse -> {
            if (loginResponse != null) {
                Authentication.getInstance().setToken(loginResponse.getToken());
            } else {
                makeToast("Unable to login.");
                ((BaseActivity) getActivity()).replaceFragment(R.id.main_fragment, new LoginFragment());
            }
        });
    }
}
