package com.example.arpit.productiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button viewButton = (Button) findViewById(R.id.view_btn);
        viewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DailyProductionActivity.class);
                startActivity(intent);
            }
        });
        Button updateButton = (Button) findViewById(R.id.update_btn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UpdateProductionActivity.class);
                startActivity(intent);
            }
        });
        Button monthlyButton = (Button) findViewById(R.id.monthly_report_btn);
        monthlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MonthlyProductionActivity.class);
                startActivity(intent);
            }
        });
    }
}
