package com.example.sqlitesessionsecond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sqlitesessionsecond.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.edtUsername.getText().toString().equals("admin")&&binding.edtPassword.getText().toString().equals("admin")){
                    Intent intent = new Intent(MainActivity.this,ListingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Incorrect values were enterd",Toast.LENGTH_LONG);
                }
            }
        });
    }
}