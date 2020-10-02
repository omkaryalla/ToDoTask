package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class todo implements Parcelable {
    String mDate, mName, mPriority;

    public todo(String date, String name, String priority){
        this.mDate = date;
        this.mName = name;
        this.mPriority = priority;
    }

    protected todo(Parcel in) {
        mDate = in.readString();
        mName = in.readString();
        mPriority = in.readString();
    }

    public static final Creator<todo> CREATOR = new Creator<todo>() {
        @Override
        public todo createFromParcel(Parcel in) {
            return new todo(in);
        }

        @Override
        public todo[] newArray(int size) {
            return new todo[size];
        }
    };

    public String getdate(){ return mDate; }
    public String getname(){ return mName; }
    public String getpriority(){ return mPriority; }

    public static Comparator<todo> comparator = new Comparator<todo>() {
        @Override
        public int compare(todo o1, todo o2) {
            char[] chr1 = o1.getdate().toCharArray();
            char[] chr2 = o2.getdate().toCharArray();

            String date1 = ""+chr1[6]+chr1[7]+chr1[8]+chr1[9]+chr1[3]+chr1[4]+chr1[0]+chr1[1];
            String date2 = ""+chr2[6]+chr2[7]+chr2[8]+chr2[9]+chr2[3]+chr2[4]+chr2[0]+chr2[1];

            return date1.compareTo(date2);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDate);
        dest.writeString(mName);
        dest.writeString(mPriority);
    }
}
