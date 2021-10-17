package com.example.lesson_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {
    ArrayList<Student> students = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    StudentAdapter(Context context, ArrayList<Student> students) {
        ctx = context;
        this.students = students;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.students.size();
    }

    @Override
    public Object getItem(int i) {
        return this.students.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View new_view = lInflater.inflate(R.layout.student_element, viewGroup, false);
        if (students.isEmpty()) return new_view;

        ((TextView) new_view.findViewById(R.id.twFIO)).setText(students.get(i).getFIO());
        ((TextView) new_view.findViewById(R.id.twFacuty)).setText(students.get(i).getFaculty());
        ((TextView) new_view.findViewById(R.id.twGroup)).setText(students.get(i).getGroup());

        if (i % 2 != 0) {
            ((LinearLayout) new_view.findViewById(R.id.ll_element))
                    .setBackgroundResource(R.color.even_element);
        }

        return new_view;
    }
}
