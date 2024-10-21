package com.example.jreportv2.controladores;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jreportv2.bd.ConstBD;
import com.example.jreportv2.bd.api.ApiComun;
import com.example.jreportv2.bd.api.interfaces.ApiDisparador;
import com.example.jreportv2.sistema.Sesion;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class CUsuarios extends ApiComun {

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
                        System.out.println(map.toString());
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

        return server_estado;
    }

    public boolean login(Context c, String URL, Map<String, String> mapa, ApiDisparador o) {
        AtomicBoolean estado = new AtomicBoolean(false);
        StringRequest sr = new StringRequest(Request.Method.POST, URL, res -> {
            try {
                estado.set(loginOK(c, res, mapa, o));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> loginErr(error, c)) {
            @Override
            protected Map<String, String> getParams() {
                return mapa;
            }
        };

        RequestQueue rs = Volley.newRequestQueue(c);
        rs.add(sr);
        return estado.get();
    }

    private boolean loginOK(Context context, String res, Map<String, String> mapa, ApiDisparador o) throws JSONException {
        if (res == null || res.isEmpty() || res.equals("null")) {
            Toast.makeText(context,
                    "usuario y/o contraseña incorrectos",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        JSONObject js = new JSONObject(res);

        Sesion s = Sesion.getInstancia();
        s.setUsuario(js);
        o.ejecutar();
        return true;
    }

    private void loginErr(VolleyError error, Context context) {
        Toast.makeText(context, "error en el servicio", Toast.LENGTH_LONG).show();
    }
}
