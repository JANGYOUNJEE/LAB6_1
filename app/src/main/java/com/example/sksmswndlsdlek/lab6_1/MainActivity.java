package com.example.sksmswndlsdlek.lab6_1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Button bWrite;
    Button bClear;
    Button bRead;
    Button bFinish;
    EditText txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtData=(EditText)findViewById(R.id.txtData);
        bWrite=(Button)findViewById(R.id.write);
        bClear=(Button)findViewById(R.id.clear);
        bRead=(Button)findViewById(R.id.read);
        bFinish=(Button)findViewById(R.id.finish);

        checkPermission();

        bWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File sdCard = Environment.getExternalStorageDirectory();
                    File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
                    directory.mkdirs();
                    File file=new File(directory,"textfile.txt");
                    FileOutputStream fOut = new FileOutputStream(file);
                    OutputStreamWriter osw = new OutputStreamWriter(fOut);
                    osw.write(txtData.getText().toString());
                    osw.close();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Done writing SD mysdfile",Toast.LENGTH_SHORT).show();
            }
        });

        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtData.setText("");
            }
        });

        bRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File sdCard = Environment.getExternalStorageDirectory();
                    File directory = new File(sdCard.getAbsolutePath(), "/MyFiles");
                    directory.mkdirs();
                    File file = new File(directory, "textfile.txt");
                    FileInputStream fIn = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fIn);
                    if (fIn != null) {
                        BufferedReader reader = new BufferedReader(isr);
                        String str = "";
                        StringBuffer buf = new StringBuffer();

                        while ((str = reader.readLine()) != null) {
                            buf.append(str + "\n");
                        }
                        txtData.setText(buf.toString());
                        isr.close();
                        fIn.close();
                    }
                }catch (java.io.FileNotFoundException e) {
                    e.printStackTrace();
                }catch(Throwable t){
                    t.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Done reading SD mysdfile",Toast.LENGTH_SHORT).show();
            }
        });

        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void checkPermission(){
        int permissioninfo= ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissioninfo== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"SDCard 쓰기 권한 있음",Toast.LENGTH_SHORT).show();
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(getApplicationContext(),"권한의 필요성 설명",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        }
    }





}
