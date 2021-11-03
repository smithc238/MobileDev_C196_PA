package com.mySchool.mobiledev_c196_pa.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mySchool.mobiledev_c196_pa.data.entities.Term;
import com.mySchool.mobiledev_c196_pa.data.repository.TermRepo;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private TermRepo repo;
    private LiveData<List<Term>> allTerms;
    private MutableLiveData<Term> selectedTerm;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repo = new TermRepo(application);
        allTerms = repo.getAllTerms();
        selectedTerm = new MutableLiveData<>();
    }

    public void insert(Term term) {
        repo.insert(term);
    }

    public void update(Term term) {
        repo.update(term);
    }

    public void delete(Term term) {repo.delete(term);}

    public void deleteAllTerms() {repo.deleteAllTerms();}

    public LiveData<List<Term>> getTermById(long id) {
        return repo.getTermById(id);
    }

    public LiveData<List<Term>> getAllTerms() {return allTerms;}

    public LiveData<Term> getTerm() {
        return this.selectedTerm;
    }

    public void setTerm(Term term) {
        this.selectedTerm.setValue(term);
    }
}
