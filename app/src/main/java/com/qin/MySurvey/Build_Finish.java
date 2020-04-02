package com.qin.MySurvey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Build_Finish extends AppCompatActivity {

    TextView txt_show;
    Button btn_test,btn_QR;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build__finish);
        ExitApplication.getInstance().addActivity(this);

        txt_show=findViewById(R.id.txt_showBuild);
        txt_show.setText(BuildQuestion.QuesJ+"\n ");
        btn_test=findViewById(R.id.btn_test);
        btn_QR=findViewById(R.id.btn_buildQR);
        btn_test.setOnClickListener(this::Test);
        btn_QR.setOnClickListener(this::QR);

    }

    public void Test(View v){
        SActivity.result=BuildQuestion.QuesJ;
        Intent intent1 = new Intent(Build_Finish.this, Question.class);
        startActivity(intent1);
    }
    public void QR(View v){
        Intent intent1 = new Intent(Build_Finish.this, Build_QR.class);
        startActivity(intent1);
    }

}
