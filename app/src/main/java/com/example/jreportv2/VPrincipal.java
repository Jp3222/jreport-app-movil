package com.example.jreportv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jreportv2.sistema.Sesion;
import com.example.jreportv2.util.ViewStruc;

import org.json.JSONException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VPrincipal extends AppCompatActivity implements ViewStruc {
    private TextView saludo,
            usuario;
    private Button perfil,
            Reportes,
            Historial_reportes,
            otros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vprincipal);
        init();
    }

    @Override
    public void init() {
        perfil = findViewById(R.id.pb_perfil);
        Historial_reportes = findViewById(R.id.pb_historial);
        Reportes = findViewById(R.id.pb_reportar);
        otros = findViewById(R.id.pb_otros);
        saludo = (TextView) findViewById(R.id.pt_saludo);
        //
        compoentesEstadoFinal();
        compoentesEstadoInicial();
        manejoEventos();
        //
        try {
            Saludo();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void compoentesEstadoInicial() {
    }

    @Override
    public void compoentesEstadoFinal() {
    }

    @Override
    public void manejoEventos() {

        Reportes.setOnClickListener(e -> evtGoTo(0));
        perfil.setOnClickListener(e -> evtGoTo(1));
        Historial_reportes.setOnClickListener(e -> evtGoTo(2));
        otros.setOnClickListener(e -> evtGoTo(3));
    }

    public void evtGoTo(int view) {
        Intent intent;
        if (view == 0)
            intent = new Intent(this, VReportes.class);
        else if (view == 1)
            intent = new Intent(this, VPerfil.class);
        else if (view == 2)
            intent = new Intent(this, VHistorial.class);
        else
            intent = new Intent(this, VLogin.class);
        startActivity(intent);
    }

    private void Saludo() throws JSONException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        LocalTime lt = LocalTime.now();
        int hour = lt.getHour();
        String menssage;
        if (hour >= 5 && hour < 12) {

            menssage = "Linda Mañana ";
        } else if (hour >= 12 && hour < 18) {
            menssage = "Linda Tarde ";
        } else if (hour >= 18 && hour < 23) {
            menssage = "Linda Noche ";
        } else {
            menssage = "Linda Madrugada";
        }
        Sesion s = Sesion.getInstancia();
        if(!s.sesionVacia()) saludo.setText(menssage.concat(" ").concat(s.getUsuario().getString("nombre")));
    }
}