package com.example.lesson_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    ArrayList<Subject> studentSubjects;
    SubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info);

        Student s = getIntent().getParcelableExtra("Student");
        ((TextView) findViewById(R.id.student_fio)).setText(s.getFIO());
        ((TextView) findViewById(R.id.student_faculty)).setText(s.getFaculty());
        ((TextView) findViewById(R.id.student_group)).setText(s.getGroup());

        studentSubjects = s.getmSubjects();
        subjectAdapter = new SubjectAdapter(this, studentSubjects);
        ListView subjectsList = findViewById(R.id.subject_list);
        subjectsList.setAdapter(subjectAdapter);

        AdapterView.OnItemClickListener clSubject = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(
                        getApplicationContext(),
                        studentSubjects.get(i).toString(),
                        Toast.LENGTH_SHORT
                ).show();
                            }
        };
        subjectsList.setOnItemClickListener(clSubject);

    }

    public void addSubject(View view) {
        studentSubjects.add(new Subject(
                ((EditText) findViewById(R.id.edit_subject_name)).getText().toString(),
                ((EditText) findViewById(R.id.edit_subject_mark)).getText().toString()
        ));
        subjectAdapter.notifyDataSetChanged();

        ((EditText) findViewById(R.id.edit_subject_name)).setText("");
        ((EditText) findViewById(R.id.edit_subject_mark)).setText("");
    }
}