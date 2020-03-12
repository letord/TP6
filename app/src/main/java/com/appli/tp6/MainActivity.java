package com.appli.tp6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button serviceBtn;
    private Button serviceBtn2;
    private Intent heureservice;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceBtn = (Button) findViewById(R.id.serviceBtn);
        serviceBtn2 = (Button) findViewById(R.id.serviceBtn2);
        Button serviceBtn3 = findViewById(R.id.serviceBtn3);
        serviceBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isServiceRunning("com.appli.tp6.HeureService")){
                    serviceBtn2.setText("Service en cours");
                }
                else{
                    serviceBtn2.setText("Service arrêté");
                }
            }
        });
        heureservice =new Intent(MainActivity.this, HeureService.class);
        serviceBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, Géolocalisation.class));
            }
        });
        serviceBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startService(heureservice);
        this.serviceBtn.setText("Arretez le Service");
        this.serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(heureservice);
            }
        });
    }
    private boolean isServiceRunning(String nomService) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            Toast.makeText(this, "" + service.service.getClassName(), Toast.LENGTH_SHORT).show();
            if (nomService.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause(){
        super.onPause();
        stopService(heureservice);
    }
    @Override
    public void onRestart(){
        super.onRestart();
        startService(heureservice);
    }
}

