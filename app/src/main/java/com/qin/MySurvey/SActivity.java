package com.qin.MySurvey;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.king.zxing.Intents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;



public class SActivity  extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";

    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int RC_CAMERA = 0X01;
    public static String result;
    protected Button btn_s,btn_getJ;

    private Class<?> cls;
    private String title;
    private boolean isContinuousScan;

    public Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc);

        context=getApplicationContext();

        btn_s=findViewById(R.id.btn0);
        btn_getJ=findViewById(R.id.btn_getJson);
        btn_s.setOnClickListener(this::Scan);
        btn_getJ.setOnClickListener(this::GetStart);

    }

    public void Scan(View v){
        isContinuousScan = false;
        if (v.getId() == R.id.btn0) {
            this.cls = EasyCaptureActivity.class;
            this.title = ((Button) v).getText().toString();
            checkCameraPermissions();
        }
    }

    public void GetStart(View v){

        result= getJson("test2",context);

        Intent intent = new Intent(SActivity.this,
                GetScan.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK && data!=null){
            if (requestCode == REQUEST_CODE_SCAN) {
                result = data.getStringExtra(Intents.Scan.RESULT);
                assert result != null;
                Log.v("Log", result);
            }

            Intent intent = new Intent(SActivity.this,
                    GetScan.class);
            startActivity(intent);
        }
    }

    private void asyncThread(Runnable runnable){
        new Thread(runnable).start();
    }
    private Context getContext(){
        return this;
    }

    /**
     * 检测拍摄权限
     */
    @AfterPermissionGranted(RC_CAMERA)
    private void checkCameraPermissions(){
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startScan(cls,title);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera),
                    RC_CAMERA, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> list) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> list) {
    }

    private void startScan(Class<?> cls,String title){
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this,R.anim.in,R.anim.out);
        Intent intent = new Intent(this, cls);
        intent.putExtra(KEY_TITLE,title);
        intent.putExtra(KEY_IS_CONTINUOUS,isContinuousScan);
        ActivityCompat.startActivityForResult(this,intent,REQUEST_CODE_SCAN,optionsCompat.toBundle());
    }

    /**
     * 读取assets本地json
     */
    public static String getJson(String fileName,Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }














}
