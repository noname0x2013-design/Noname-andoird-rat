package com.rat.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent i) {
        ctx.startService(new Intent(ctx, CoreService.class));
    }
}
