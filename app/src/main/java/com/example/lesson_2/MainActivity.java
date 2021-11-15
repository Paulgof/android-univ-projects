package com.example.lesson_2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Student> studentsList;
    ArrayList<Student> filteredStudentList;
    StudentAdapter studentAdapter;
    private int mPosition;
    private ActivityResultLauncher<Intent> mActivityResultLauncher;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    AlertDialog.Builder infoDialog;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAbout:{
                infoDialog = new AlertDialog.Builder(MainActivity.this);
                infoDialog.setTitle("О программе");
                infoDialog.setMessage("Это программа группы 47/1");
                infoDialog.setCancelable(false);
                infoDialog.setPositiveButton("ОК", null);
                infoDialog.show();
                return true;
            }
            case R.id.miExit:{
                finish();
                return true;
            }
            default:{}
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ll);

        ((LinearLayout) findViewById(R.id.student_form_ll)).setVisibility(View.GONE);
        ((Button) findViewById(R.id.bAddStudent)).setVisibility(View.GONE);

        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            Student s = intent.getParcelableExtra("Student");
                            studentsList.set(mPosition, s);
                            filterStudents();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Student " + s.toString() + " successfully saved",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                }
        );

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        int size = sharedPreferences.getInt("count", 0);
        if (size > 0) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            for (int i = 0; i < size; ++i) {
                String s = sharedPreferences.getString("student"+i, "");
                Student st = gson.fromJson(s, Student.class);
                studentsList.add(st);
            }
        }
        studentListCreate(null);

    }

    public void studentListCreate(View view) {
        ListView listView = findViewById(R.id.myList2);

        //studentsList = new ArrayList<>();
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
                //startActivity(intent);
                mPosition = i;
                mActivityResultLauncher.launch(intent);

                // TODO
            }
        };
        listView.setOnItemClickListener(clStudent);

    }

    public void addStudent(View view) {
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.editFIO)).getText().toString())) {
            ((EditText) findViewById(R.id.editFIO)).setError("Укажите ФИО");
            return;
        }

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

    @Override
    protected void onDestroy() {
        if (studentsList != null) {
            SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            ed.putInt("count", studentsList.size());
            for (int i = 0; i < studentsList.size(); ++i) {
                String s = gson.toJson(studentsList.get(i));
                ed.putString("student" + i, s);
            }
            ed.commit();
        }
        super.onDestroy();
    }
}