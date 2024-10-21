package com.example.jreportv2.bd;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class ConstBD {
    /**
     * Array contenedor de los campos de la base de datos en la api
     * <br> 0 id
     * <br> 1 usuario
     * <br> 2 contraseña
     * <br> 3 nombre
     * <br> 4 apellidos
     * <br> 5 telefono
     * <br> 6 correo
     * <br> 7 fecha_registro
     * <br> 8 no_contrato
     */
    public static String[] TAB_USUARIOS = {
            "id",
            "usuario",
            "contra",
            "nombre",
            "apellidos",
            "telefono",
            "correo",
            "fecha_registro",
            "no_contrato"
    };
}
