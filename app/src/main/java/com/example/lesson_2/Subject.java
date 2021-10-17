package com.example.lesson_2;

import android.os.Parcel;
import android.os.Parcelable;

public class Subject implements Parcelable {
    private String mName;
    private Integer mMark;

    public Subject(String name, Integer mark) {
        mName = name;
        mMark = mark;
    }

    public Subject(Parcel parcel) {
        mName = parcel.readString();
        mMark = parcel.readInt();
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Integer getmMark() {
        return mMark;
    }

    public void setmMark(Integer mMark) {
        this.mMark = mMark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeInt(mMark);

    }
}
