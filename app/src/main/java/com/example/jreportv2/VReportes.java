package com.example.jreportv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jreportv2.bd.ApiJeport;
import com.example.jreportv2.controladores.FabricaControlador;
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
import org.json.JSONObject;

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
    private Button guardar;
    private ImageView imgView;
    private Map<String, String> mapa_tipo_de_reportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vreportes);
        init();
    }

    @Override
    public void init() {
        mapa_tipo_de_reportes = new HashMap<>(10);
        btnCamara = findViewById(R.id.rc_agregar_fotos);
        tipo = findViewById(R.id.rc_tipo_reporte);
        descripcion = findViewById(R.id.rc_descripcion);
        guardar = findViewById(R.id.rb_guardar);
        fillSpinner();
        compoentesEstadoFinal();
        compoentesEstadoInicial();
        manejoEventos();
        SupportMapFragment smp = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.rc_mapa);
        if (smp != null) smp.getMapAsync(this);
    }

    @Override
    public void compoentesEstadoInicial() {
        tipo.setSelection(0);
    }

    @Override
    public void compoentesEstadoFinal() {

    }

    @Override
    public void manejoEventos() {
        //btnCamara.setOnClickListener(e -> abrirCamara());
        guardar.setOnClickListener(e -> evtGenerarReporte());
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
                lt.format(DateTimeFormatter.ofPattern("hh-mm-ss"))
        );
        String usuario;
        try {
            usuario = Sesion.getInstancia().getUsuario().getString("id");
            if (!Filtros.noNullAndEmpty(usuario)) {
                Toast.makeText(this, "Error al enviar el reporte", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String lat = String.valueOf(punto.latitude);
        String lon = String.valueOf(punto.longitude);

        Map<String, String> mapa_post = new HashMap<>();
        mapa_post.put("tipo", String.valueOf(mapa_tipo_de_reportes.get(tipo.getSelectedItem().toString())));
        mapa_post.put("usuario", usuario);
        mapa_post.put("descripcion", descripcion.getText().toString());
        mapa_post.put("url_multimedia", String.format("%s/%s", usuario, no_reporte));
        mapa_post.put("lat", lat);
        mapa_post.put("lon", lon);
        mapa_post.put("no_reporte", no_reporte);
        System.out.println(mapa_post);
        StringRequest sr = new StringRequest(Request.Method.POST, ApiJeport.apiMetod(ApiJeport.REPORTES_SAVE),
                e -> {
                    Toast.makeText(this, "Reporte Enviado", Toast.LENGTH_LONG).show();
                },
                e ->{
                    Toast.makeText(this, "Reporte Erroneo", Toast.LENGTH_LONG).show();
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mapa_post;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(sr);
        /*
        FabricaControlador.getControladorReportes().put(this,
                mapa_post,
                ApiJeport.apiMetod(ApiJeport.REPORTES_SAVE),
                Toast.makeText(this, "Reporte Enviado", Toast.LENGTH_LONG)::show
        );*/
    }

    private void fillSpinner() {
        JsonArrayRequest jar = new JsonArrayRequest(ApiJeport.apiMetod(ApiJeport.TIPO_REPORTES_GET),
                ok -> {
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
                    int size_obj = ok.length();
                    for (int i = 0; i < size_obj; i++) {
                        try {
                            JSONObject jsonArray = ok.getJSONObject(i);
                            adaptador.add(jsonArray.getString("tipo"));
                            mapa_tipo_de_reportes.put(jsonArray.getString("tipo"), jsonArray.getString("id"));
                        } catch (JSONException e) {
                            System.out.println("error");
                        }
                    }
                    tipo.setAdapter(adaptador);
                },
                err -> Toast.makeText(this, "Error en el servicio", Toast.LENGTH_LONG).show());

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(jar);
    }
}