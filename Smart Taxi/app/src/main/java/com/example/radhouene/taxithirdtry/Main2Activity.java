package com.example.radhouene.taxithirdtry;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    Button registerlink,btmsbm;
    String strUser,strPass;
    EditText Username,Pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        registerlink = (Button) findViewById(R.id.LinkRegisterId);
        Username = (EditText) findViewById(R.id.LogEmail);
        Pass = (EditText) findViewById(R.id.LogPass);
        btmsbm = (Button) findViewById(R.id.btnLogin);
        btmsbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        strUser = Username.getText().toString();
                        strPass = Pass.getText().toString();
                        if(strUser.matches("")) {
                            alertDialog("Username is required");
                        }else if(strPass.matches("")) {
                            alertDialog("Pasword is required");
                        }
                    }
                }
        );
        registerlink.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.radhouene.taxithirdtry.registerdriver");
                        startActivity(intent);
                    }
                }
        );
    }
    public void alertDialog(String Message){


        new AlertDialog.Builder(this)
                .setTitle("SmartTaxi Message")
                .setMessage(Message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }
}
