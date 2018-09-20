package com.example.appa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;

public class ActionReceiver extends BroadcastReceiver {
    String mMensaje;
    String mNumero;
    @Override
    public void onReceive(Context context, Intent intent) {
        mMensaje = intent.getStringExtra("mensaje");
        mNumero = intent.getStringExtra("numero");
        System.out.println(mMensaje);
        System.out.println(mNumero);
    }
}
