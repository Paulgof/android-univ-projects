package com.example.lesson_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Student> studentsList;
    ArrayList<Student> filteredStudentList;
    StudentAdapter studentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ll);

        ((LinearLayout) findViewById(R.id.student_form_ll)).setVisibility(View.GONE);
        ((Button) findViewById(R.id.bAddStudent)).setVisibility(View.GONE);

    }

    public void studentListCreate(View view) {
        ListView listView = findViewById(R.id.myList2);

        studentsList = new ArrayList<>();
        filteredStudentList = new ArrayList<>();

        studentAdapter = new StudentAdapter(this, filteredStudentList);

        listView.setAdapter(studentAdapter);

        ((LinearLayout) findViewById(R.id.student_form_ll)).setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.bAddStudent)).setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.bCreateList2)).setVisibility(View.GONE);
        AdapterView.OnItemClickListener clStudent = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                intent.putExtra("Student", filteredStudentList.get(i));
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(clStudent);

    }

    public void addStudent(View view) {
        studentsList.add(new Student(
                ((EditText) findViewById(R.id.editFIO)).getText().toString(),
                ((EditText) findViewById(R.id.editFaculty)).getText().toString(),
                ((EditText) findViewById(R.id.editGroup)).getText().toString(),
                new ArrayList<Subject>()
        ));
        filterStudents();

        ((EditText) findViewById(R.id.editFIO)).setText("");
        ((EditText) findViewById(R.id.editFaculty)).setText("");
        ((EditText) findViewById(R.id.editGroup)).setText("");
    }

    public void filterStudents() {
        TextView groupView = findViewById(R.id.groupFilter);
        String group = groupView.getText().toString();

        filteredStudentList.clear();
        if (!group.equals("")) {
            for (Student s :
                    studentsList) {
                if (s.getGroup().equals(group)) filteredStudentList.add(s);
            }
        }
        else {
            filteredStudentList.addAll(studentsList);
        }
        studentAdapter.notifyDataSetChanged();

    }

    public void clickFilterGroup(View view) {
        filterStudents();

    }
}