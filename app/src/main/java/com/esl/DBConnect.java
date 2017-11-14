package com.esl;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import java.util.LinkedHashMap;
public class DBConnect extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "esldb.db";
    private HashMap hp;

    public DBConnect(Context context){
        super(context, DATABASE_NAME, null, 1);
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table tutor(tutorname text primary key,subject text,phone_no text)");
        db.execSQL("create table student(studentid text primary key,studentname text)");
        db.execSQL("create table labattendence(studentid text,enter_time long)");
        db.execSQL("create table tutoringattendence(studentid text,tutorname text,subjectname text,enter_time long,comments text)");
        db.execSQL("create table print(studentid text,print_time long,print_type text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS tutor");
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS labattendence");
        db.execSQL("DROP TABLE IF EXISTS tutoringattendence");
        db.execSQL("DROP TABLE IF EXISTS print");
        onCreate(db);
    }

    public boolean print(String student,String type){
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", student);
        contentValues.put("print_time", time.getTime());
        contentValues.put("print_type", type);
        db.insert("print", null, contentValues);
        return true;
    }

    public boolean addStudent(String id,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", id);
        contentValues.put("studentname", name);
        db.insert("student", null, contentValues);
        return true;
    }

    public boolean labAttendence(String name){
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", name);
        contentValues.put("enter_time", time.getTime());
        db.insert("labattendence", null, contentValues);
        return true;
    }

    public boolean tutoringAttendence(String name,String tutor,String subject,String comment){
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentid", name);
        contentValues.put("tutorname", tutor);
        contentValues.put("subjectname", subject);
        contentValues.put("enter_time", time.getTime());
        contentValues.put("comments", comment);
        db.insert("tutoringattendence", null, contentValues);
        return true;
    }

    public boolean addTutor(String tutor,String subject,String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tutorname",tutor);
        contentValues.put("subject",subject);
        contentValues.put("phone_no",phone);
        db.insert("tutor", null, contentValues);
        return true;
    }



    public ArrayList<String> getStudent() {
        ArrayList<String> sname = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select studentname from student",null);
        while(res.moveToNext()) {
            sname.add(res.getString(res.getColumnIndex("studentname")));
        }
        return sname;
    }

    public ArrayList<String> getTutor() {
        ArrayList<String> sname = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tutor",null);
        while(res.moveToNext()) {
            String tutor = res.getString(res.getColumnIndex("tutorname"));
            String subject = res.getString(res.getColumnIndex("subject"));
            sname.add(tutor+","+subject);
        }
        return sname;
    }

    public LinkedHashMap<String,Integer> getAttendedTutor() {
        LinkedHashMap<String,Integer> map = new LinkedHashMap<String,Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select tutorname from tutoringattendence",null);
        while(res.moveToNext()) {
            String tutor = res.getString(res.getColumnIndex("tutorname"));
            if(map.containsKey(tutor)){
                map.put(tutor,(map.get(tutor)+1));
            }else {
                map.put(tutor, 1);
            }
        }
        return map;
    }

    public ArrayList<String> getLabReport(long start,long end){
        ArrayList<String> sname = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from labattendence",null);
        while(res.moveToNext()) {
            String student = res.getString(res.getColumnIndex("studentid"));
            long time = res.getLong(res.getColumnIndex("enter_time"));
            if(time >= start && time <= end) {
                java.util.Date dd = new java.util.Date(time);
                java.sql.Date date = new java.sql.Date(dd.getTime());
                sname.add(student + " " + date.toString());
            }
        }
        return sname;
    }

    public ArrayList<String> getTutoringReport(long start,long end){
        ArrayList<String> sname = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tutoringattendence",null);
        while(res.moveToNext()) {
            String student = res.getString(res.getColumnIndex("studentid"));
            String tutor = res.getString(res.getColumnIndex("tutorname"));
            String subject = res.getString(res.getColumnIndex("subjectname"));
            String comment = res.getString(res.getColumnIndex("comments"));

            long time = res.getLong(res.getColumnIndex("enter_time"));
            if(time >= start && time <= end) {
                java.util.Date dd = new java.util.Date(time);
                java.sql.Timestamp date = new java.sql.Timestamp(dd.getTime());
                sname.add(student + " "     +tutor+ " "     +subject+ " "   + comment+ " "   + date.toString());
            }
        }
        return sname;
    }
}
