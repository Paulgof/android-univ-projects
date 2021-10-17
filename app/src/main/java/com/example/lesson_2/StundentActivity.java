package com.example.lesson_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class StundentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info);
        Student s = getIntent().getParcelableExtra("Student");
        ((TextView) findViewById(R.id.student_fio)).setText(s.getFIO());
        ((TextView) findViewById(R.id.student_faculty)).setText(s.getFaculty());
        ((TextView) findViewById(R.id.student_group)).setText(s.getGroup());

    }
}