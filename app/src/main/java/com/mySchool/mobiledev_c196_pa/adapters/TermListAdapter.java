package com.mySchool.mobiledev_c196_pa.adapters;

import android.content.Context;
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

/**
 * Adapter for TermsList
 * https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView
 */
public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermHolder> {
    private OnTermClickListener listener;
    private List<Term> terms = new ArrayList<>();

    public TermListAdapter(Context context) {
    }

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_term,parent,false);
        return new TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        Term term = terms.get(position);
        holder.textViewTitle.setText(term.getTitle());
        holder.textViewStart.setText(DateTimeConv.dateToStringLocal(term.getStart()));
        holder.textViewEnd.setText(DateTimeConv.dateToStringLocal(term.getEnd()));
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    class TermHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.term_item_title);
            textViewStart = itemView.findViewById(R.id.term_item_start);
            textViewEnd = itemView.findViewById(R.id.term_item_end);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onTermClick(terms.get(position));
                }
            });
        }
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    public interface OnTermClickListener {
        void onTermClick(Term term);
    }

    public void setOnTermClickListener(OnTermClickListener listener) {this.listener = listener;}
}
