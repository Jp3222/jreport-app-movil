package com.example.jreportv2.controladores;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jreportv2.bd.api.interfaces.ApiDisparador;
import com.example.jreportv2.sistema.Sesion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CLogin {
    public static void login(Context c, String URL,Map<String, String> mapa ,ApiDisparador o) {
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL,
                res -> {
                    if (!res.isEmpty()) {
                        try {
                            JSONObject js = new JSONObject(res);
                            Sesion s = Sesion.getInstancia();
                            s.setUsuario(js);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        o.ejecutar();
                    } else {
                        Toast.makeText(c, "error", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(c, "error 2", Toast.LENGTH_LONG).show();
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                System.out.println(mapa.toString());
                return mapa;
            }
        };
        RequestQueue rs = Volley.newRequestQueue(c);
        rs.add(sr);
    }
}
