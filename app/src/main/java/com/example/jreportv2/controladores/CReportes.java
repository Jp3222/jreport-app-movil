package com.example.jreportv2.controladores;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jreportv2.bd.api.ApiComun;
import com.example.jreportv2.bd.api.interfaces.ApiDisparador;

import java.util.Map;

public class CReportes extends ApiComun {
    @Override
    public boolean put(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        setContexto(contexto);
        StringRequest sr = new StringRequest(Request.Method.POST, url,
                e -> {
                    disparo.ejecutar();
                    dispararMensaje(1);
                    server_estado = true;

                },
                e -> {
                    dispararMensaje(0);
                    server_estado = false;
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                System.out.println(args.toString());
                return args;
            }
        };
        res_que = Volley.newRequestQueue(contexto);
        res_que.add(sr);
        return server_estado;
    }

    @Override
    public boolean set(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        return false;
    }

    @Override
    public Map<String, String> get(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        return null;
    }

    @Override
    public boolean delete(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        return false;
    }
}
