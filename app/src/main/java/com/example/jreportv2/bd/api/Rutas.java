package com.example.jreportv2.bd.api;

public class Rutas {

    private static final String DOMINIO = "192.168.1.92";
    private static final String PUERTO = "80";
    private static final String URL = "http://%s:%s/jreport/api/%s.php";

    private static String getMetodo(String metodo){
        return String.format(URL, DOMINIO, PUERTO, metodo);
    }

    private static String USER_SAVE = getMetodo("UserSave");

    private static String USER_SAVE = getMetodo("UserSave");
    private static String USER_SAVE = getMetodo("UserSave");
    private static String USER_SAVE = getMetodo("UserSave");
    private static String USER_SAVE = getMetodo("UserSave");
}
