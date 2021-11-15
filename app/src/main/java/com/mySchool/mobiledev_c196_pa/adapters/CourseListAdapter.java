package com.mySchool.mobiledev_c196_pa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseHolder> {
    private OnCourseClickListener listener;
    private List<Course> courses = new ArrayList<>();

    public CourseListAdapter(Context context) {
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course,parent,false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course course = courses.get(position);
        holder.textViewTitle.setText(course.getTitle());
        holder.textViewStart.setText(DateTimeConv.dateToStringLocal(course.getStart()));
        holder.textViewEnd.setText(DateTimeConv.dateToStringLocal(course.getEnd()));
        holder.textViewStatus.setText(course.getStatus().toString());
        //set Assessment
        if (course.getNote() != null) {
            holder.textViewNote.setText(course.getNote());
        } else {
            holder.itemView.findViewById(R.id.course_item_note_body).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.course_item_note_header).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;
        private TextView textViewStatus;
        private TextView textViewNote;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.course_item_title);
            textViewStart = itemView.findViewById(R.id.course_item_start);
            textViewEnd = itemView.findViewById(R.id.course_item_end);
            textViewStatus = itemView.findViewById(R.id.course_item_status);
            textViewNote = itemView.findViewById(R.id.course_item_note_body);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onCourseClick(courses.get(position));
                }
            });
        }
    }

    public Course getCourseAt(int position) {
        return courses.get(position);
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }

    public void setOnCourseClickListener(OnCourseClickListener listener) {
        this.listener = listener;
    }
}
