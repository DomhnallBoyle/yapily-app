package com.example.yapily_app.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yapily_app.R;
import com.example.yapily_app.databinding.ListItemInstitutionBinding;
import com.example.yapily_app.models.Institution;
import com.example.yapily_app.repositories.AccountRepository;
import com.example.yapily_app.utilities.ItemClickListener;

import java.util.List;

public class InstitutionsAdapter extends RecyclerView.Adapter<InstitutionsAdapter.InstitutionsViewHolder> {

    private ItemClickListener itemClickListener;
    private List<Institution> institutions;
    private AccountRepository accountRepository;

    public InstitutionsAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.accountRepository = new AccountRepository();
    }

    public List<Institution> getInstitutions() {
        return this.institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
        notifyDataSetChanged();
    }

    class InstitutionsViewHolder extends RecyclerView.ViewHolder {

        private ListItemInstitutionBinding binding;

        public InstitutionsViewHolder(ListItemInstitutionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Institution institution, int position) {
            this.binding.setInstitution(institution);
            this.binding.institutionCardView.setOnClickListener(view -> {
                itemClickListener.onItemClick(this.binding.getRoot(), position);
            });
        }
    }

    // create new views
    @Override
    public InstitutionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemInstitutionBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_institution,
                parent,
                false
        );

        return new InstitutionsViewHolder(binding);
    }

    // replace contents of a view
    @Override
    public void onBindViewHolder(InstitutionsViewHolder holder, int position) {
        holder.bind(this.institutions.get(position), position);
    }

    // return size of dataset
    @Override
    public int getItemCount() {
        return this.institutions != null ? this.institutions.size() : 0;
    }
}
