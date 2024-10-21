package com.example.jreportv2.controladores;

public class FabricaControlador {
    public static CUsuarios getControladorUsuarios(){
        return new CUsuarios();
    }

    public static CReportes getControladorReportes(){
        return new CReportes();
    }

    public static CTipoReportes getControladorTR(){
        return new CTipoReportes();
    }
}
