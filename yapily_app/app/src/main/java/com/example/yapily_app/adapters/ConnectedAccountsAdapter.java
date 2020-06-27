package com.example.yapily_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yapily_app.R;
import com.example.yapily_app.databinding.ListItemAccountBinding;
import com.example.yapily_app.models.BankAccount;
import com.example.yapily_app.repositories.AccountRepository;
import com.example.yapily_app.utilities.ItemClickListener;

import java.util.List;

public class ConnectedAccountsAdapter extends RecyclerView.Adapter<ConnectedAccountsAdapter.ConnectedAccountsViewHolder> {

    private ItemClickListener itemClickListener;
    private List<BankAccount> accounts;
    private AccountRepository accountRepository;

    public ConnectedAccountsAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.accountRepository = new AccountRepository();
    }

    public List<BankAccount> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    class ConnectedAccountsViewHolder extends RecyclerView.ViewHolder {

        private ListItemAccountBinding binding;

        public ConnectedAccountsViewHolder(ListItemAccountBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BankAccount account, int position) {
            this.binding.setAccount(account);
            this.binding.accountCardView.setOnLongClickListener(button -> {
                itemClickListener.onItemHeld(this.binding.getRoot(), position);
                return true;
            });
            this.binding.reauthoriseAccountButton.setOnClickListener(button -> {
                itemClickListener.onItemClick(this.binding.getRoot(), position);
            });
            if (!account.getStatus().equalsIgnoreCase("AUTHORIZED")) {
                this.binding.reauthoriseAccountButton.setVisibility(View.VISIBLE);
                this.binding.reauthoriseAccountText.setVisibility(View.VISIBLE);
            }
        }
    }

    // create new views
    @Override
    public ConnectedAccountsAdapter.ConnectedAccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemAccountBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_account,
                parent,
                false
        );

        return new ConnectedAccountsAdapter.ConnectedAccountsViewHolder(binding);
    }

    // replace contents of a view
    @Override
    public void onBindViewHolder(ConnectedAccountsAdapter.ConnectedAccountsViewHolder holder, int position) {
        holder.bind(this.accounts.get(position), position);
    }

    // return size of dataset
    @Override
    public int getItemCount() {
        return this.accounts != null ? this.accounts.size() : 0;
    }
}
