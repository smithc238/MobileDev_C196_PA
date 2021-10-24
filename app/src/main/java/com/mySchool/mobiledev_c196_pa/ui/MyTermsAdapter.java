package com.mySchool.mobiledev_c196_pa.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;

import java.util.ArrayList;
import java.util.List;

public class MyTermsAdapter extends RecyclerView.Adapter<MyTermsAdapter.MyTermsHolder> {
    private List<Term> terms = new ArrayList<>();

    @NonNull
    @Override
    public MyTermsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item,parent,false);
        return new MyTermsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTermsHolder holder, int position) {
        Term term = terms.get(position);
        holder.textViewTitle.setText(term.getTitle());
        holder.textViewStart.setText(DateTimeConv.dateToStringLocal(term.getStart()));
        holder.textViewEnd.setText(DateTimeConv.dateToStringLocal(term.getEnd()));
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    class MyTermsHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;

        public MyTermsHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.term_item_title);
            textViewStart = itemView.findViewById(R.id.term_item_start);
            textViewEnd = itemView.findViewById(R.id.term_item_end);
        }
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }
}
