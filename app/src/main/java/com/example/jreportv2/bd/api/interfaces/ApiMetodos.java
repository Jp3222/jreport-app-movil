package com.example.jreportv2.bd.api.interfaces;

import android.content.Context;

import java.util.Map;

public interface ApiMetodos {
    boolean put(Context contexto, Map<String, String> args, String url, ApiDisparador disparo);

    boolean set(Context contexto, Map<String, String> args, String url, ApiDisparador disparo);

    Map<String, String> get(Context contexto, Map<String, String> args, String url, ApiDisparador disparo);

    boolean delete(Context contexto, Map<String, String> args, String url, ApiDisparador disparo);
}
