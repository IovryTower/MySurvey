package com.qin.MySurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class BuildQuestion extends AppCompatActivity {
    public static String QuesJ;
    public final int NUM_QUESTION=1000;
    String qu_id="";
    String []qu_type=new String[NUM_QUESTION];//single,multiple,fill
    String []qu_title=new String[NUM_QUESTION];
    String qu_option1=" ";
    String qu_option2=" ";
    String qu_option3=" ";
    String qu_option4=" ";
    int temp_qi=0;
    RadioGroup bu_rp;

    Button btn_question,btn_finish;
    private static final String ACTIVITY_TAG="LogDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_question);

        ExitApplication.getInstance().addActivity(this);

        btn_question=findViewById(R.id.btn_add_question);
        btn_finish=findViewById(R.id.btn_build_finish);
        btn_question.setOnClickListener(this::Add_qu);
        btn_finish.setOnClickListener(this::finish);
        bu_rp=findViewById(R.id.bu_rp);
        bu_rp.setOnCheckedChangeListener((group, checkedId) -> {
            EditText ed_id;
            ed_id=findViewById(R.id.edit_id);
            qu_id=ed_id.getText().toString();
            switch (checkedId){
                case R.id.ra_s:
                    qu_type[temp_qi]="single";
                    break;
                case R.id.ra_m:
                    qu_type[temp_qi]="multiple";
                    break;
            }
        });

    }

    public void Add_qu(View v){

        EditText ed_qu;
        EditText ed_op1,ed_op2,ed_op3,ed_op4;
        ed_qu=findViewById(R.id.edit_question_title);
        ed_op1=findViewById(R.id.edit_option1);
        ed_op2=findViewById(R.id.edit_option2);
        ed_op3=findViewById(R.id.edit_option3);
        ed_op4=findViewById(R.id.edit_option4);

        qu_option1=ed_op1.getText().toString();
        qu_option2=ed_op2.getText().toString();
        qu_option3=ed_op3.getText().toString();
        qu_option4=ed_op4.getText().toString();

        String str_p=ed_qu.getText().toString();

        if(qu_type[0]==null){return;}
        if(qu_type[temp_qi]==null){
            qu_type[temp_qi]=qu_type[temp_qi-1];
        }

        if(!str_p.equals(qu_title[temp_qi])&& !str_p.equals(" ")&& !qu_option1.equals(" ")
                && !qu_option2.equals(" ")&& !qu_option3.equals(" ")&& !qu_option4.equals(" ")){
            qu_title[temp_qi]=str_p;
            Log.v(ACTIVITY_TAG,"QUESTION"+  qu_title[temp_qi]);

            temp_qi+=1;
            ed_qu.setText(" ");
            ed_op1.setText(" ");
            ed_op2.setText(" ");
            ed_op3.setText(" ");
            ed_op4.setText(" ");
        }
    }

    public void finish(View v){

        QuesJ=  "{\n" +
                "  \"survey\": {\n" +
                "    \"id\": \""+ qu_id+"\",\n" +
                "    \"len\":\"" + (temp_qi) +"\",\n" +
                "    \"questions\":  [\n";
        for(int i=0;i<temp_qi;i++){
            QuesJ+= "      {\n" +
                    "        \"type\":\""+qu_type[i]+"\",\n" +
                    "        \"question\":\""+qu_title[i]+" \",\n" +
                    "        \"options\":[\n" ;

            QuesJ+= "          {\"1\":\""+  qu_option1 +"\"},\n" +
                    "          {\"2\":\""+  qu_option2 +"\"},\n" +
                    "          {\"3\":\""+  qu_option3 +"\"},\n" +
                    "          {\"4\":\""+  qu_option4 +"\"}\n" ;

            QuesJ+= "        ]\n" +
                    "      }" ;
            if(i+1<temp_qi){
                QuesJ+=",\n";
            }
        }
        QuesJ+= "   \n ]\n" +
                "  }\n" +
                "}";
        Log.v(ACTIVITY_TAG,QuesJ);

        Intent intent1 = new Intent(BuildQuestion.this, Build_Finish.class);
        startActivity(intent1);

    }
}
