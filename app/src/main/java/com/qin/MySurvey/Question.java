package com.qin.MySurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Question extends AppCompatActivity {

    int flag=0;     //Used to determine how many questions to answer
    int flag1=0;
    int[] question_num=new int[100];  //Used to determine that there are several options in this question to limit the user's answers
    String [][]answer=new String[100][100]; //Holds options read from a file
    String []type=new String[100];  //Type of storage question
    String []question=new String[100];  //store questions
    String id;
    String len;
    boolean HaveDo=false;

    public static String save_data="your answers are here:\n \n";  //User's answer

    private static final String ACTIVITY_TAG="LogDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApplication.getInstance().addActivity(this);
        Init();
        Set();

    }

    void Init(){
        try {
            String show_json=SActivity.result;
            JSONObject rootObject = new JSONObject(show_json);
            JSONObject surveyObject = rootObject.getJSONObject("survey");
             id = surveyObject.getString("id");

             len = surveyObject.getString("len");

            Log.v("Log", id);
            Log.v("Log", len);

            JSONArray questionsArray = surveyObject.getJSONArray("questions");
            for(int i=0;i<questionsArray.length();i++){
                JSONObject questionsObject = questionsArray.getJSONObject(i);
                type[i]=questionsObject.getString("type");
                question[i]=questionsObject.getString("question");
                JSONArray optionsArray=questionsObject.getJSONArray("options");
                for(int j=0;j<optionsArray.length();j++){
                    String number = String.valueOf(j+1);
                    JSONObject optionsObject = optionsArray.getJSONObject(j);
                    answer[i][j] = optionsObject.toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void Set(){
        Log.v(ACTIVITY_TAG,"AAAAAAAA"+flag);
        int length= Integer.parseInt(len);
        if(question[flag]!=null&&flag<length) {

            if(type[flag].equals("single")){
                    single();
                return;
                }

            if(type[flag].equals("multiple")){
                    multiple();
                return;
                }

            if(type[flag].equals("fill")){
                fill();
            }

        }
        else{
            Toast.makeText(Question.this,"Please choose and click to record your location and the date:",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Question.this,
                    TimeActivity.class);
            startActivity(intent);
        }


    }

    void single(){

        setContentView(R.layout.activity_question);
        TextView txt_question;
        RadioButton bt_1;
        RadioButton bt_2;
        RadioButton bt_3;
        RadioButton bt_4;
        RadioButton bt_5;
        RadioButton bt_6;
        RadioButton bt_7;
        RadioGroup group;
        Button bt_choose;
        bt_1=(RadioButton)findViewById(R.id.choose_s_1);
        bt_2=(RadioButton)findViewById(R.id.choose_s_2);
        bt_3=(RadioButton)findViewById(R.id.choose_s_3);
        bt_4=(RadioButton)findViewById(R.id.choose_s_4);
        bt_5=(RadioButton)findViewById(R.id.choose_s_5);
        bt_6=(RadioButton)findViewById(R.id.choose_s_6);
        bt_7=(RadioButton)findViewById(R.id.choose_s_7);
        bt_choose=(Button)findViewById(R.id.bt_choose_s);
        group=findViewById(R.id.json_rgp_s);
        txt_question=(TextView)findViewById(R.id.txt_questions_s);
        txt_question.setText(question[flag]);

        for(int i=0;answer[flag][i]!=null;i++){
            String temp;
            temp="bt_"+(i+1);
            switch (temp){
                case "bt_1":
                    bt_1.setText(answer[flag][i]);
                    bt_1.setVisibility(View.VISIBLE);
                    break;
                case "bt_2":
                    bt_2.setText(answer[flag][i]);
                    bt_2.setVisibility(View.VISIBLE);
                    break;
                case "bt_3":
                    bt_3.setText(answer[flag][i]);
                    bt_3.setVisibility(View.VISIBLE);
                    break;
                case "bt_4":
                    bt_4.setText(answer[flag][i]);
                    bt_4.setVisibility(View.VISIBLE);
                    break;
                case "bt_5":
                    bt_5.setText(answer[flag][i]);
                    bt_5.setVisibility(View.VISIBLE);
                    break;
                case "bt_6":
                    bt_6.setText(answer[flag][i]);
                    bt_6.setVisibility(View.VISIBLE);
                    break;
                case "bt_7":
                    bt_7.setText(answer[flag][i]);
                    bt_7.setVisibility(View.VISIBLE);
                    break;
            }
        }

        bt_choose.setText("CHOOSE");
        if(MainActivity.lang.equals("zh") ){
            bt_choose.setText("选择");
        }



        group.setOnCheckedChangeListener((group1, checkedId) -> {
            flag+=1;
            boolean te=false;
            switch (checkedId){
                case R.id.choose_s_1:
                    te=true;
                    save_data += "question" + flag + ":" + bt_1.getText().toString() + "\n";
                    break;
                case R.id.choose_s_2:
                    te=true;
                    save_data += "question" + flag + ":" + bt_2.getText().toString() + "\n";
                    break;
                case R.id.choose_s_3:
                    te=true;
                    save_data += "question" + flag + ":" + bt_3.getText().toString() + "\n";
                    break;
                case R.id.choose_s_4:
                    te=true;
                    save_data += "question" + flag + ":" + bt_4.getText().toString() + "\n";
                    break;
                case R.id.choose_s_5:
                    te=true;
                    save_data += "question" + flag + ":" + bt_5.getText().toString() + "\n";
                    break;
                case R.id.choose_s_6:
                    te=true;
                    save_data += "question" + flag + ":" + bt_6.getText().toString() + "\n";
                    break;
                    case R.id.choose_s_7:
                    te=true;
                    save_data += "question" + flag + ":" + bt_7.getText().toString() + "\n";
                    break;
            }
            if(!te)flag-=1;

            bt_choose.setOnClickListener(v -> {
                Log.v(ACTIVITY_TAG,"BBBBBBBB"+flag);
                Set();
            });

        });
    }


    void multiple(){
        setContentView(R.layout.activity_mu_scan);
        TextView txt_question;
        CheckBox bt_1;
        CheckBox bt_2;
        CheckBox bt_3;
        CheckBox bt_4;
        CheckBox bt_5;
        CheckBox bt_6;
        CheckBox bt_7;
        Button bt_choose;
        bt_1=(CheckBox)findViewById(R.id.choose_m_1);
        bt_2=(CheckBox)findViewById(R.id.choose_m_2);
        bt_3=(CheckBox)findViewById(R.id.choose_m_3);
        bt_4=(CheckBox)findViewById(R.id.choose_m_4);
        bt_5=(CheckBox)findViewById(R.id.choose_m_5);
        bt_6=(CheckBox)findViewById(R.id.choose_m_6);
        bt_7=(CheckBox)findViewById(R.id.choose_m_7);
        bt_choose=(Button)findViewById(R.id.bt_choose_m);
        txt_question=(TextView)findViewById(R.id.txt_questions_m);
        txt_question.setText(question[flag]);

        for(int i=0;answer[flag][i]!=null;i++){
            String temp;
            temp="bt_"+(i+1);
            switch (temp){
                case "bt_1":
                    bt_1.setText(answer[flag][i]);
                    bt_1.setVisibility(View.VISIBLE);
                    break;
                case "bt_2":
                    bt_2.setText(answer[flag][i]);
                    bt_2.setVisibility(View.VISIBLE);
                    break;
                case "bt_3":
                    bt_3.setText(answer[flag][i]);
                    bt_3.setVisibility(View.VISIBLE);
                    break;
                case "bt_4":
                    bt_4.setText(answer[flag][i]);
                    bt_4.setVisibility(View.VISIBLE);
                    break;
                case "bt_5":
                    bt_5.setText(answer[flag][i]);
                    bt_5.setVisibility(View.VISIBLE);
                    break;
                case "bt_6":
                    bt_6.setText(answer[flag][i]);
                    bt_6.setVisibility(View.VISIBLE);
                    break;
                case "bt_7":
                    bt_7.setText(answer[flag][i]);
                    bt_7.setVisibility(View.VISIBLE);
                    break;

            }
        }

        bt_choose.setText("CHOOSE");
        if(MainActivity.lang.equals("zh") ){
            bt_choose.setText("选择");
        }



        bt_choose.setOnClickListener(v -> {
            boolean te=false;
            flag+=1;
            save_data += "question" + flag + ":";
            if(bt_1.isChecked()){
                te=true;
                save_data +=  bt_1.getText().toString() + "\n";
            }
            if(bt_2.isChecked()){
                te=true;
                save_data +=  bt_2.getText().toString() + "\n";
            }
            if(bt_3.isChecked()){
                te=true;
                save_data +=  bt_3.getText().toString() + "\n";
            }
            if(bt_4.isChecked()){
                te=true;
                save_data += bt_4.getText().toString() + "\n";
            }
            if(bt_5.isChecked()){
                te=true;
                save_data +=bt_5.getText().toString() + "\n";
            }
            if(bt_6.isChecked()){
                te=true;
                save_data += bt_4.getText().toString() + "\n";
            }
            if(bt_7.isChecked()){
                te=true;
                save_data += bt_5.getText().toString() + "\n";
            }
            if(!te)flag-=1;
            if(te){
                Log.v(ACTIVITY_TAG,"CCCCCCC"+flag);
                Set();
            }

        });
    }

    void fill(){
        setContentView(R.layout.activity_fill);
        TextView txt_question;
        Button bt_choose;
        EditText ed_qu;
        ed_qu=findViewById(R.id.edit_q);
        bt_choose=(Button)findViewById(R.id.bt_choose_q);
        txt_question=(TextView)findViewById(R.id.txt_questions_q);
        txt_question.setText(question[flag]);
        ed_qu=findViewById(R.id.edit_q);
        EditText finalEd_qu = ed_qu;
        ed_qu.setOnClickListener(v -> {
            String Get= finalEd_qu.getText().toString();
            if(!Get.equals("")){
                if(!HaveDo){
                    HaveDo=true;
                    flag+=1;

                }

                bt_choose.setOnClickListener(v1 -> {
                    Log.v(ACTIVITY_TAG,"DDDDDDD"+flag);
                    HaveDo=false;
                    save_data += "question" + flag + ":" + Get + "\n";
                    Set();
                });
            }

        });


    }

}
