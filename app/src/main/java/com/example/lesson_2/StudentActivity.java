package com.example.lesson_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    Student mStudent;
    ArrayList<Subject> studentSubjects;
    SubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info);

        mStudent = getIntent().getParcelableExtra("Student");
        ((TextView) findViewById(R.id.student_fio)).setText(mStudent.getFIO());
        ((TextView) findViewById(R.id.student_faculty)).setText(mStudent.getFaculty());
        ((TextView) findViewById(R.id.student_group)).setText(mStudent.getGroup());

        studentSubjects = mStudent.getmSubjects();
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
//        studentSubjects.add(new Subject(
//                ((EditText) findViewById(R.id.edit_subject_name)).getText().toString(),
//                ((EditText) findViewById(R.id.edit_subject_mark)).getText().toString()
//        ));
//        subjectAdapter.notifyDataSetChanged();
//
//        ((EditText) findViewById(R.id.edit_subject_name)).setText("");
//        ((EditText) findViewById(R.id.edit_subject_mark)).setText("");

        AlertDialog.Builder inputDialog = new AlertDialog.Builder(StudentActivity.this);
        inputDialog.setTitle("Информация о дисциплине");
        inputDialog.setCancelable(false);
        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.subject_info, null);
        inputDialog.setView(vv);
        final EditText mName = vv.findViewById(R.id.info_subject_name);
        final Spinner mMark = vv.findViewById(R.id.sSI_mark);

        inputDialog.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mStudent.getmSubjects().add(new Subject(
                        mName.getText().toString(),
                        mMark.getSelectedItem().toString()
                ));
                subjectAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("Отмена", null);
        inputDialog.show();
    }

    public void clSave(View view) {
        Intent intent = new Intent();
        intent.putExtra("Student", mStudent);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void clCancel(View view) {
        finish();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setTitle("Сохранить изменения?");
        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clSave(null);
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clCancel(null);
            }
        });
        quitDialog.show();
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }
}