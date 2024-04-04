package com.example.jreportv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jreportv2.sistema.Sesion;
import com.example.jreportv2.util.Filtros;
import com.example.jreportv2.util.ViewStruc;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class VReportes extends AppCompatActivity implements ViewStruc,
        OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    //**--Campos del formulario--**//
    private Spinner tipo;
    private EditText descripcion;
    //**--Variables para manipular el mapa--**//
    private double x, y;
    private GoogleMap google_map;
    private MarkerOptions marker;
    private LatLng punto;
    private String options;
    //**--Variables para manipular la camara--**//
    private Button btnCamara;
    private ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vreportes);
        init();
    }

    @Override
    public void init() {
        SupportMapFragment smp = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.rc_mapa);
        if (smp != null) smp.getMapAsync(this);
        compoentesEstadoFinal();
        compoentesEstadoInicial();
        manejoEventos();
    }

    @Override
    public void compoentesEstadoInicial() {

    }

    @Override
    public void compoentesEstadoFinal() {

    }

    @Override
    public void manejoEventos() {
        btnCamara.setOnClickListener(e -> abrirCamara());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        google_map = googleMap;
        this.google_map.setOnMapClickListener(this);
        this.google_map.setOnMapLongClickListener(this);
        //
        punto = new LatLng(18.8304861, -98.9520279);
        marker = new MarkerOptions().position(punto).title("Mexico");
        //Marcador
        google_map.addMarker(marker);
        google_map.moveCamera(CameraUpdateFactory.newLatLng(punto));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if (latLng == null) {
            Toast.makeText(this, "Ubicacion invalida", Toast.LENGTH_LONG).show();
            return;
        }
        punto = latLng;
        x = latLng.latitude;
        y = latLng.longitude;
        System.out.println(String.format("x:%s - y:%s", x, y));
        google_map.clear();
        google_map.addMarker(new MarkerOptions().position(latLng).title("XDDD"));
        google_map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        if (latLng == null) {
            Toast.makeText(this, "Ubicacion invalida", Toast.LENGTH_LONG).show();
            return;
        }
        x = latLng.latitude;
        y = latLng.longitude;
        punto = latLng;
        google_map.clear();
        google_map.addMarker(new MarkerOptions().position(latLng).title("Hola"));
        google_map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);
        }
    }

    private void evtGenerarReporte() {
        LocalDate ld;
        LocalTime lt;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return;
        }
        ld = LocalDate.now();
        lt = LocalTime.now();
        String no_reporte = String.format("%s_%s",
                ld.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                lt.format(DateTimeFormatter.ofPattern("HH_MM_SS"))
        );
        String usuario;
        try {
            usuario = Sesion.getInstancia().getUsuario().getString("id");
            if(!Filtros.noNullAndEmpty(usuario)){
                Toast.makeText(this, "Error al enviar el reporte", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String lat = String.valueOf(punto.latitude);
        String lon = String.valueOf(punto.longitude);
        Map<String, String> mapa = new HashMap<>(7);
        mapa.put("tipo",String.valueOf(tipo.getSelectedItem()));
        mapa.put("usuario",usuario);
        mapa.put("descripcion",descripcion.getText().toString());
        mapa.put("url_multimedia",String.format("%s/%s", usuario, no_reporte));
        mapa.put("lat", String.valueOf(punto.latitude));
        mapa.put("lon", String.valueOf(punto.longitude));
        mapa.put("no_reporte",no_reporte);
    }
}