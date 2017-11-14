package com.esl;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

public class TutoringReportView extends Activity {
    ListView list;//list view refrence
    String name[];
    DBConnect db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlist);
        db = new DBConnect(this);
        String stime = getIntent().getExtras().getString("stime");
        String etime = getIntent().getExtras().getString("etime");
        ArrayList<String> report = db.getTutoringReport(Long.parseLong(stime), Long.parseLong(etime));
        if(report.size() > 0) {
            name = new String[report.size()];
            for (int i = 0; i < report.size(); i++) {
                name[i] = report.get(i);
            }
            ViewActivity adapter = new ViewActivity(TutoringReportView.this, name);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
        }else{
            Toast.makeText(TutoringReportView.this, "No record found", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TutoringReportView.this,TutoringReport.class);
            startActivity(intent);
        }
    }
}
