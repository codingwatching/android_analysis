package com.plug.export;

import android.annotation.SuppressLint;

import com.google.gson.Gson;


public class frida_helper {
    public static String object_2_string(Object obj) {
        return new Gson().toJson(obj);

    }

    public static String byte_2_hex_str(byte[] by) {
    
        StringBuilder sb = new StringBuilder();
        for (byte item : by) {
            sb.append(String.format("%02x", item));
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String byte_2_string(byte[] by, String decode) {
        try {
            return new String(by, decode);
        } catch (Exception e) {
            return e.toString();
        }
    }

    @SuppressLint("DefaultLocale")
    public static String byte_2_ip_str(byte[] by) {
        StringBuilder sb = new StringBuilder();
        for (byte item : by) {
            sb.append(String.format("%d", (int) (item & 0xff)));
            sb.append(".");
        }
        return sb.toString();
    }

}
