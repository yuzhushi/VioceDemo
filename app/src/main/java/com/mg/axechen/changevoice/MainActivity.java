package com.mg.axechen.changevoice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private String[] permissions = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private static final int REQUEST_PERMISSIONS = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(MainActivity.this, "用户曾拒绝权限", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_PERMISSIONS);
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream =  getResources().openRawResource(R.raw.voice);
                    File file = new File(Environment.getExternalStorageDirectory().getPath(),"voice.wav");
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[10];
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    int len = 0;
                    while ((len=inputStream.read(buffer))!=-1){
                        outputStream.write(buffer,0,len);
                    }
                    byte[] bs = outputStream.toByteArray();
                    fileOutputStream.write(bs);
                    outputStream.close();
                    inputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("getPath",e.getMessage());
                }
            }
        }).start();

    }

    public void startChange(View view) {
        String path = Environment.getExternalStorageDirectory().getPath()+File.separator+"voice.wav";
        Log.i("getPath",path);
        File file = new File(path);
        if (!file.exists()) {
            Log.e("Main", "没有文件");
            return;
        }
        switch (view.getId()) {
            // 普通
            case R.id.btn_normal:
                VoiceTools.changeVoice(path, 0);
                break;
            // 萝莉
            case R.id.btn_luoli:
                VoiceTools.changeVoice(path, 1);
                break;// 萝莉
            // 大叔
            case R.id.btn_dashu:
                VoiceTools.changeVoice(path, 2);
                break;
            // 惊悚
            case R.id.btn_jingsong:
                VoiceTools.changeVoice(path, 3);
                break;
            // 搞怪
            case R.id.btn_gaoguai:
                VoiceTools.changeVoice(path, 4);
                break;
            // 空灵
            case R.id.btn_kongling:
                VoiceTools.changeVoice(path, 5);
                break;

        }

    }

}
