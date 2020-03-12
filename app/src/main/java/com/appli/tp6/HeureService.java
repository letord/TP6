package com.appli.tp6;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class HeureService extends Service {
    private Timer timer;
    public HeureService() {
    }

    //La méthode onBind sert quand on veut faire communiquer le service
// avec un autre service ou avec une activité
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    private void initService() {

        Toast.makeText(getApplicationContext(), "Service démarré ...", Toast.LENGTH_SHORT).show();
        timer = new Timer();
        //répéter afficheHeure() toutes les 5 secondes à partir de maintenant
        timer.scheduleAtFixedRate(new Manege(), 0, 5000);    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        exitService();
    }

    private void exitService() {
        Toast.makeText(getApplicationContext(), "Service arrêté ...", Toast.LENGTH_SHORT).show();
        timer.cancel();
        timer.purge();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initService() ;
        //le service n'est pas redémarré après arrêt
        //SystemClock.sleep(30000);// attendre 30 secondes ininterruptibles
        return Service.START_NOT_STICKY ;
    }
    private class Manege extends TimerTask
    {
        private Handler updateUI = new Handler(){
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                afficheHeure();
            }
        };
        public void run()
        {
            updateUI.sendEmptyMessage(0);
            //SystemClock.sleep(30000);
            //afficheHeure();
        }
    }
    private void afficheHeure() {
        String s= (String) DateFormat.format("dd/MM/yy hh:mm:ss",    System.currentTimeMillis());
        Toast.makeText(getApplicationContext(),s ,Toast.LENGTH_SHORT).show();
    }
}
