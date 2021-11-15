package com.example.lesson_2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Student implements Parcelable {
    private String mFIO, mFaculty, mGroup;
    private ArrayList<Subject> mSubjects;

    public Student(String FIO, String faculty, String group, ArrayList<Subject> subjects) {
        mFIO = FIO;
        mFaculty = faculty;
        mGroup = group;
        mSubjects = subjects;

    }

    public Student(Parcel in) {
        mFIO = in.readString();
        mFaculty = in.readString();
        mGroup = in.readString();
        mSubjects = in.createTypedArrayList(Subject.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mFIO);
        parcel.writeString(this.mFaculty);
        parcel.writeString(this.mGroup);
        parcel.writeTypedList(mSubjects);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {

        @Override
        public Student createFromParcel(Parcel parcel) {
            return new Student(parcel);
        }

        @Override
        public Student[] newArray(int i) {
            return new Student[i];
        }
    };

    @Override
    public String toString() {
        return "Student{" +
                "ФИО:'" + mFIO + '\'' +
                ", факультет: '" + mFaculty + '\'' +
                ", группа: '" + mGroup + '\'' +
                ", subjects count: " + mSubjects.size() +
                '}';
    }

    public String getFIO() {
        return mFIO;
    }

    public void setFIO(String mFIO) {
        this.mFIO = mFIO;
    }

    public String getFaculty() {
        return mFaculty;
    }

    public void setFaculty(String mFaculty) {
        this.mFaculty = mFaculty;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String mGroup) {
        this.mGroup = mGroup;
    }

    public ArrayList<Subject> getmSubjects() {
        return mSubjects;
    }
}
