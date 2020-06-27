package com.example.yapily_app.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.yapily_app.models.Institution;
import com.example.yapily_app.repositories.InstitutionRepository;

import java.util.List;

public class InstitutionsViewModel extends AndroidViewModel {

    private InstitutionRepository institutionRepository;

    public InstitutionsViewModel(Application application) {
        super(application);
        this.institutionRepository = new InstitutionRepository();
    }

    public LiveData<List<Institution>> getInstitutions() {
        return this.institutionRepository.getInstitutions();
    }
}
