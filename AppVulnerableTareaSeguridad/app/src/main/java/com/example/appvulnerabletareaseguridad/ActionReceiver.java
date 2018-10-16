package com.example.appvulnerabletareaseguridad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class ActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //se define el vibrator para que vibre el celular
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        // vibracion del celular
        assert v != null;
        v.vibrate(new long[]{0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500}, -1);

    }
}