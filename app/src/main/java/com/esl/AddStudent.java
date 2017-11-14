package com.esl;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class AddStudent extends AppCompatActivity {
    EditText sid, name;
    Button save;
    DBConnect db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);
        sid = (EditText) findViewById(R.id.sid);
        name = (EditText) findViewById(R.id.name);
        db = new DBConnect(this);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void save() {
        //read values from fields
        String s1 = sid.getText().toString();
        String s2 = name.getText().toString();
        if (s1.trim().length() == 0 || s1 == null) {
            Toast.makeText(AddStudent.this, "Please enter student id", Toast.LENGTH_LONG).show();
            sid.requestFocus();
            return;
        }
        if (s2.trim().length() == 0 || s2 == null) {
            Toast.makeText(AddStudent.this, "Please enter student name", Toast.LENGTH_LONG).show();
            name.requestFocus();
            return;
        }
        boolean flag = db.addStudent(s1,s2);
        if (flag) {
            Toast.makeText(AddStudent.this, "Student details added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AddStudent.this,DirectorPage.class);
            startActivity(intent);
        } else {
            //show login fail
            Toast.makeText(AddStudent.this, "Student id or name already exists", Toast.LENGTH_LONG).show();
        }
    }
}


