package com.esl;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Lab extends AppCompatActivity {
    Button mark,print;
    Spinner spinner;
    DBConnect db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);
        db = new DBConnect(this);
        ArrayList<String> list = db.getStudent();
        spinner = (Spinner) findViewById(R.id.spinner);
        String arr[] = new String[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        mark = (Button) findViewById(R.id.mark);
        mark.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mark();
            }
        });

        print = (Button) findViewById(R.id.print);
        print.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                print();
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void print() {
        //read values from fields
        String sname = spinner.getSelectedItem().toString();
        boolean flag = db.print(sname,"Lab");
        if (flag) {
            Toast.makeText(Lab.this, "Print request successfull", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Lab.this,StudentPage.class);
            startActivity(intent);
        } else {
            //show login fail
            Toast.makeText(Lab.this, "Error occured while printing", Toast.LENGTH_LONG).show();
        }
    }
    public void mark() {
        //read values from fields
        String student = spinner.getSelectedItem().toString();
        boolean flag = db.labAttendence(student);
        if (flag) {
            Toast.makeText(Lab.this, "Lab marking successfull", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Lab.this,StudentPage.class);
            startActivity(intent);
        } else {
            //show login fail
            Toast.makeText(Lab.this, "Error occured while lab marking", Toast.LENGTH_LONG).show();
        }
    }
}


