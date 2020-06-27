package com.example.yapily_app.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.yapily_app.models.BankAccount;
import com.example.yapily_app.models.AccountAuthResponse;
import com.example.yapily_app.repositories.AccountRepository;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository accountRepository;

    public AccountViewModel(Application application) {
        super(application);
        this.accountRepository = new AccountRepository();
    }

    public LiveData<AccountAuthResponse> initiateAccountAuthorisationRequest(String institutionId,
                                                                             String applicationUserId) {
        return this.accountRepository.initiateAccountAuthorisationRequest(institutionId,
                applicationUserId);
    }

    public LiveData<List<BankAccount>> getAccounts() {
        return this.accountRepository.getAccounts();
    }
}