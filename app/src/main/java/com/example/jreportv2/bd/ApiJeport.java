package com.example.jreportv2.bd;

public class ApiJeport {
    private static ApiJeport instancia;

    public static ApiJeport getInstancia() {
        if (instancia == null) instancia = new ApiJeport();
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
    ;
    public static String USUARIOS_DELTE = urlUsuarios(2);
    public static String USUARIOS_EDIT = urlUsuarios(3);
    public static String USUARIOS_LOGIN = urlUsuarios(4);
    public static String REPORTES_SAVE = urlReportes(0);
    public static String REPORTES_GET = urlReportes(1);
    public static String REPORTES_DELTE = urlReportes(2);
    public static String REPORTES_EDIT = urlReportes(3);

    public static String TEST_SAVE = urlTest(0);

    private static final String DOMINIO = "192.168.1.76";
    private static final String PORT = "80";
    private static final String URL = "http://%s:%s/jreport/api/%s";
    public static String apiMetod(String metod) {
        return String.format(URL, DOMINIO, PORT, metod);
    }
}
