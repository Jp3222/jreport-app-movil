package com.example.jreportv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.jreportv2.bd.ApiJeport;
import com.example.jreportv2.bd.ConstBD;
import com.example.jreportv2.controladores.CUsuarios;

import java.util.HashMap;
import java.util.Map;

public class VRegistro extends AppCompatActivity {
    private EditText campo_nombre, campo_apellidos,
            campo_no_contrato, campo_usuario,
            campo_contraseña, campo_telefono, campo_correo;

    private Button btn_registrar, btn_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vregistro);
        init();
    }

    private void init() {
        //campos de texto
        campo_no_contrato = findViewById(R.id.rc_no_contrato);
        campo_nombre = findViewById(R.id.rc_nombre);
        campo_apellidos = findViewById(R.id.rc_apellidos);
        campo_telefono = findViewById(R.id.rc_telefono);
        campo_correo = findViewById(R.id.rc_correo);
        campo_usuario = findViewById(R.id.rc_usuario);
        campo_contraseña = findViewById(R.id.rc_contraseña);
        //botones
        btn_registrar = findViewById(R.id.rb_registrar);
        btn_cancelar = findViewById(R.id.rb_cancelar);
        //
        btn_registrar.setOnClickListener(e -> evtRegistrar());
        btn_cancelar.setOnClickListener(e -> intent());
    }

    public void evtRegistrar() {
        Map<String, String> mapa = new HashMap<>(9);
        mapa.put(ConstBD.TAB_USUARIOS[1], campo_usuario.getText().toString());
        mapa.put(ConstBD.TAB_USUARIOS[2], campo_contraseña.getText().toString());
        mapa.put(ConstBD.TAB_USUARIOS[3], campo_nombre.getText().toString());
        mapa.put(ConstBD.TAB_USUARIOS[4], campo_apellidos.getText().toString());
        mapa.put(ConstBD.TAB_USUARIOS[5], campo_telefono.getText().toString());
        mapa.put(ConstBD.TAB_USUARIOS[6], campo_correo.getText().toString());
        mapa.put(ConstBD.TAB_USUARIOS[8], campo_no_contrato.getText().toString());

        CUsuarios c = new CUsuarios();
        c.put(this, mapa, ApiJeport.getInstancia().apiMetod(ApiJeport.USUARIOS_SAVE), () -> {
            intent();
        });
    }

    public void intent() {
        Intent intent = new Intent(this, VLogin.class);
        startActivity(intent);
    }

}