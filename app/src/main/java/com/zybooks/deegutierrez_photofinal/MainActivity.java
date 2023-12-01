package com.zybooks.deegutierrez_photofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerContainer;
    Button btnAdd, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerContainer = findViewById(R.id.recyclerContainer);
        btnAdd = findViewById(R.id.btnAdd);
//        btnUpdate = findViewById(R.id.btnUpdate);
//        btnDelete = findViewById(R.id.btnDelete);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddScreen.class);
                startActivity(intent);
            }
        });
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}