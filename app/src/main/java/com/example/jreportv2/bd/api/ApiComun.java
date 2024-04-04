package com.example.jreportv2.bd.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.jreportv2.bd.api.interfaces.ApiMetodos;

public abstract class ApiComun implements ApiMetodos {
    protected RequestQueue res_que;
    protected Context contexto;
    protected boolean server_estado;


    /**
     * Metodo que dispara un mensaje de error o correcto
     * @param TIPO - tipo de mensaje 1 si el mensaje es "TODO OK" o 2 si el mensaje es "Error"
     */
    protected void dispararMensaje(int TIPO){
        String mensaje = "Error";
        if(TIPO == 1){
            mensaje = "Todo OK";
        }
        Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show();
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }
}
