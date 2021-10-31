package com.mySchool.mobiledev_c196_pa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;

import java.util.ArrayList;
import java.util.List;

public class InstructorsListAdapter extends RecyclerView.Adapter<InstructorsListAdapter.InstructorHolder> {
    private OnInstructorClickListener listener;
    private List<Instructor> instructors = new ArrayList<>();
    private final Context context;

    public InstructorsListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public InstructorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_item,parent,false);
        return new InstructorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorHolder holder, int position) {
        Instructor instructor = instructors.get(position);
        holder.textViewName.setText(instructor.getName());
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }

    class InstructorHolder extends RecyclerView.ViewHolder  {
        private TextView textViewName;

        public InstructorHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.instructor_item_name);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onInstructorClick(instructors.get(position));
                }
            });
        }
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
        notifyDataSetChanged();
    }

    public interface OnInstructorClickListener {
        void onInstructorClick(Instructor instructor);
    }

    public void setOnInstructorClickListener(OnInstructorClickListener listener) {
        this.listener = listener;
    }
}
