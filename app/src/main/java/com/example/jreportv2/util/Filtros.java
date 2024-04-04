package com.example.jreportv2.util;

public class Filtros {

    public static boolean noNull(String obj) {
        return obj != null;
    }

    public static boolean noEmpty(String obj) {
        return !obj.isEmpty();
    }

    public static boolean noNullAndEmpty(String o) {
        return noNull(o) && noEmpty(o);
    }



}
