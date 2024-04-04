package com.example.jreportv2.bd.api;

import com.example.jreportv2.bd.ApiJeport;

public class ApiInfo {
    private static ApiInfo instancia;

    public static ApiInfo getInstancia() {
        if (instancia == null) instancia = new ApiInfo();
        return instancia;
    }

    private static String[] TAB = {"User", "Reportes", "Test"};
    private static String[] METOD = {"Save", "Get", "Delete", "Edit", "Login"};
    private static String FORMATO = "%s%s.php";

    private static String urlUsuarios(int metodo) {
        return String.format(FORMATO, TAB[0], METOD[metodo]);
    }

    private static String urlReportes(int metodo) {
        return String.format(FORMATO, TAB[1], METOD[metodo]);
    }

    private static String urlTest(int metodo) {
        return String.format(FORMATO, TAB[2], METOD[metodo]);
    }

    public static String USUARIOS_SAVE = urlUsuarios(0);
    public static String USUARIOS_DELETE = urlUsuarios(2);
    public static String USUARIOS_EDIT = urlUsuarios(3);
    public static String USUARIOS_LOGIN = urlUsuarios(4);
    public static String REPORTES_SAVE = urlReportes(0);
    public static String REPORTES_GET = urlReportes(1);
    public static String REPORTES_DELETE = urlReportes(2);
    public static String REPORTES_EDIT = urlReportes(3);

    public static String TEST_SAVE = urlTest(0);

    private final String DOMINIO;
    private final String PORT;
    private final String URL;

    private ApiInfo() {
        DOMINIO = "192.168.1.73";
        PORT = "80";
        URL = "http://%s:%s/jreport/api/%s";
    }

    public String getMetodo(String metodo) {
        return String.format(URL, DOMINIO, PORT, metodo);
    }
}
