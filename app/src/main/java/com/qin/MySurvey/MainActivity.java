package com.qin.MySurvey;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    protected Button w_btn,lang_btn;
    protected CheckBox w_b;
    protected RadioGroup ra_g;
    public static String lang="en";

    void w_b() {
        w_btn = findViewById(R.id.button);
        w_b = findViewById(R.id.checkBox);

        w_btn.setOnClickListener(v -> {
            if (w_b.isChecked()) {
                Intent intent1 = new Intent(MainActivity.this, SActivity.class);
                startActivity(intent1);
            }
        });

        lang_btn=findViewById(R.id.btn_setLang);
        lang_btn.setOnClickListener(v -> {
            ra_g=findViewById(R.id.Ra_g);
            ra_g.setVisibility(View.VISIBLE);
            ra_g.setOnCheckedChangeListener((group, checkedId) -> {

                switch (checkedId) {
                    case R.id.Ra_E:
                        SetLang(0);
                        break;
                    case R.id.Ra_C:
                        SetLang(1);
                        break;
                }
            });

        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale locale = getResources().getConfiguration().locale;
        lang = locale.getLanguage();

            w_b();
    }

    public void updateActivity(String sta) {

        // 本地语言设置
        Locale myLocale = new Locale(sta);
        Resources res = getResources();// 获得res资源对象
        DisplayMetrics dm = res.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        Configuration conf = res.getConfiguration();// 获得设置对象
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        startActivity((new Intent(this, MainActivity.class)));
        this.finish();
    }


    public void SetLang(int SetLanguage){

        if (SetLanguage==0) {
            LanguageSet.SetCurrentLanguage("en");
            updateActivity("en");
        } else if(SetLanguage==1) {
            LanguageSet.SetCurrentLanguage("zh");
            updateActivity("zh");
        }

    }


}