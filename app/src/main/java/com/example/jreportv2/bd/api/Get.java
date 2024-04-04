package com.example.jreportv2.bd.api;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jreportv2.bd.ConstBD;
import com.example.jreportv2.bd.api.interfaces.ApiDisparador;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Get extends ApiComun {

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
                return args;
            }
        };
        res_que = Volley.newRequestQueue(contexto);
        res_que.add(sr);
        return server_estado;
    }

    @Override
    public boolean set(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        setContexto(contexto);
        StringRequest sr = new StringRequest(url,
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
                return args;
            }
        };
        res_que = Volley.newRequestQueue(contexto);
        res_que.add(sr);
        return this.server_estado;
    }

    @Override
    public Map<String, String> get(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        setContexto(contexto);
        Map<String, String> map = new HashMap<>();
        JsonArrayRequest sr = new JsonArrayRequest(url,
                res -> {
                    JSONObject o = null;
                    try {
                        for (int i = 0; i < res.length(); i++) {
                            o = res.getJSONObject(i);
                            for (String j : ConstBD.TAB_USUARIOS) {
                                map.put(j, o.getString(j));
                            }
                        }
                    } catch (JSONException e) {
                        Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

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
                return args;
            }
        };
        res_que = Volley.newRequestQueue(this.contexto);
        res_que.add(sr);
        return map;
    }

    @Override
    public boolean delete(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        setContexto(contexto);
        StringRequest sr = new StringRequest(url,
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
                return args;
            }
        };
        res_que = Volley.newRequestQueue(this.contexto);
        res_que.add(sr);
        return server_estado;
    }
}
