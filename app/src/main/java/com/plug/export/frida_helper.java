package com.plug.export;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.alibaba.fastjson.JSON;
import com.units.log;

public class frida_helper {
    private static final boolean fieldBased = true;
    private static SerializeConfig serializeConfig = new SerializeConfig(fieldBased);
    private static ParserConfig parserConfig = new ParserConfig(fieldBased);

    public static String object_2_string(Object obj) {
        log.i("on object_2_string, obj is " + obj);
        String ret = "";
        String json_err = "";
        try {
            ret = JSON.toJSONString(obj, serializeConfig);
        } catch (Exception e2) {
            json_err = "Fast json error: " + e2.toString() + "\n";
            log.i(json_err);

            try {
                ret = new Gson().toJson(obj);
            } catch (Exception e) {
                json_err += "Gson error: " + e.toString() + "\n";
                log.i(json_err);
            }
        }


        if (!json_err.equals("")) {
            ret = json_err + ret;
        }

        log.i("out object_2_string, obj is " + obj);
        return ret;
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
