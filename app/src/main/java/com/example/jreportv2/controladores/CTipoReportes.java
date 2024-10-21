package com.example.jreportv2.controladores;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.jreportv2.bd.api.ApiComun;
import com.example.jreportv2.bd.api.interfaces.ApiDisparador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CTipoReportes extends ApiComun {
    @Override
    public boolean put(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        return false;
    }

    @Override
    public boolean set(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        return false;
    }

    @Override
    public synchronized Map<String, String> get(Context contexto, final Map<String, String> args, String url, ApiDisparador disparo) {
        System.out.println("get");
        JsonArrayRequest jar = new JsonArrayRequest(url, ok -> {
            Map<String, String> ok1 = getOk(ok, contexto, args);

        }, err -> getErr(err, contexto));

        RequestQueue rq = Volley.newRequestQueue(contexto);
        rq.add(jar);

        System.out.println("get fin");
        return args;
    }

    private synchronized Map<String, String> getOk(JSONArray res, Context context, final Map<String, String> args) {
        int res_size = res.length();
        if (res_size <= 0) {
            Toast.makeText(context, "Error en el servicio", Toast.LENGTH_LONG).show();
            return args;
        }
        JSONObject j;
        try {
            for (int i = 0; i < res_size; i++) {
                j = res.getJSONObject(i);
                args.put(
                        String.copyValueOf(j.getString("id").toCharArray()),
                        String.copyValueOf(j.getString("tipo").toCharArray()));
            }
        } catch (JSONException e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }
        System.out.println("ok");
        System.out.println(args.toString());
        System.out.println("ok fin");
        return args;
    }

    private void getErr(VolleyError err, Context context) {
        Toast.makeText(context, "Error en el servicio", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean delete(Context contexto, Map<String, String> args, String url, ApiDisparador disparo) {
        return false;
    }
}
