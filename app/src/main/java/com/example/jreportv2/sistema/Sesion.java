package com.example.jreportv2.sistema;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sesion {

    private static Sesion instancia;

    public synchronized static Sesion getInstancia(){
        if(instancia == null){
            instancia = new Sesion();
        }
        return instancia;
    }
    private JSONObject usuario;

    private Sesion(){
    }


    public void setUsuario(JSONObject usuario){
        this.usuario = usuario;
    }

    public JSONObject getUsuario() {
        return usuario;
    }

    public void clearUsuario(){
        usuario = null;
    }

    public boolean sesionVacia(){
        return usuario == null;
    }
}
