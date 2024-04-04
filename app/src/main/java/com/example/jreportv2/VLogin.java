package com.example.jreportv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jreportv2.bd.ApiJeport;
import com.example.jreportv2.controladores.CLogin;
import com.example.jreportv2.util.Filtros;

import java.util.HashMap;
import java.util.Map;

public class VLogin extends AppCompatActivity {
    TextView campo_nombre, campo_contraseña;
    Button inicio_sesion, registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlogin);
        init();
        evt();
    }

    public void init() {
        campo_nombre = findViewById(R.id.lc_usuario);
        campo_contraseña = findViewById(R.id.lc_contraseña);
        inicio_sesion = findViewById(R.id.lb_inicio_sesion);
        registrarse = findViewById(R.id.lb_registrar);
    }

    public void evt() {
        inicio_sesion.setOnClickListener(e -> evtLogin());
        registrarse.setOnClickListener(e -> evtRegistrar());
    }


    public void evtLogin() {
        String user, password;
        user = campo_nombre.getText().toString();
        password = campo_contraseña.getText().toString();
        if (!Filtros.noNullAndEmpty(user) && !Filtros.noNullAndEmpty(password)) {
            Toast.makeText(this, "Campos vacios", Toast.LENGTH_LONG).show();
            return;
        }
        Map<String, String> mapa = new HashMap<>(2);
        mapa.put("usuario", user);
        mapa.put("contra", password);
        System.out.println(ApiJeport.apiMetod(ApiJeport.USUARIOS_LOGIN));
        CLogin.login(this,
                ApiJeport.apiMetod(ApiJeport.USUARIOS_LOGIN),
                mapa,
                this::evtPrincipal);

        estadoInicial();
    }

    public void evtPrincipal() {
        Intent intent = new Intent(this, VPrincipal.class);
        startActivity(intent);
        finish();
    }

    public void evtRegistrar() {
        Intent intent = new Intent(this, VRegistro.class);
        startActivity(intent);
    }

    public void estadoInicial() {
        campo_nombre.setText(null);
        campo_contraseña.setText(null);
    }
}