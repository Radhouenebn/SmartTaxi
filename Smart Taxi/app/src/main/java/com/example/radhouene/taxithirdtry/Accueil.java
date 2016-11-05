package com.example.radhouene.taxithirdtry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Accueil extends AppCompatActivity {
    Button btmlogclt,btmlogdvr,linkregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        linkregister = (Button) findViewById(R.id.LinkRegisterId);
        btmlogclt = (Button) findViewById(R.id.btnLoginClt);
        btmlogdvr = (Button) findViewById(R.id.btnLoginDrv);
        btmlogclt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.radhouene.taxithirdtry.MainActivity");
                        startActivity(intent);
                    }
                }
        );
        btmlogdvr.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.radhouene.taxithirdtry.Main2Activity");
                        startActivity(intent);
                    }
                }
        );
        linkregister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Accueil.this);
                        alert.setTitle("Sign up");
                        alert.setMessage("Choisissez votre status :");
                        alert.setPositiveButton("Customer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent("com.example.radhouene.taxithirdtry.RegisterClt");
                                startActivity(intent);
                            }
                        });
                        alert.setNegativeButton("Driver", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent("com.example.radhouene.taxithirdtry.registerdriver");
                                startActivity(intent);
                            }
                        });
                        alert.show();
                    }
                }
        );

    }







}
