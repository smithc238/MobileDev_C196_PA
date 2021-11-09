package com.mySchool.mobiledev_c196_pa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;

import java.util.ArrayList;
import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentHolder> {
    private OnAssessmentClickListener listener;
    private List<Assessment> assessments = new ArrayList<>();

    public AssessmentListAdapter(Context context){
    }

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assessment,parent,false);
        return new AssessmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        Assessment assessment = assessments.get(position);
        holder.textViewTitle.setText(assessment.getTitle());
        holder.textViewType.setText(assessment.getType().toString());
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    class AssessmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewType;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.assessment_item_title);
            textViewType = itemView.findViewById(R.id.assessment_item_type);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onAssessmentClick(assessments.get(position));
                }
            });
        }
    }

    public Assessment getAssessmentAt(int position) {
        return assessments.get(position);
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    public interface OnAssessmentClickListener {
        void onAssessmentClick(Assessment assessment);
    }

    public void setOnAssessmentClickListener(OnAssessmentClickListener listener) {
        this.listener = listener;
    }
}
