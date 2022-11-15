package com.plug.xposed.app_sub_plug;

import android.app.Application;
import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.plug.base.plug_config;
import com.plug.export.frida_helper;
import com.plug.xposed.base.sub_plug_base;
import com.units.log;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.util.Base64;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class sub_plug_work extends sub_plug_base {
    public sub_plug_work(plug_config config, XC_LoadPackage.LoadPackageParam xposed_param) {
        super(config, xposed_param);
        name = "work jar";
    }

    @Override
    public void on_loadclass(String class_name, Class<?> cls, ClassLoader class_loader, XC_MethodHook.MethodHookParam param) {

    }

    @Override
    public void on_360_reinforce(Context context, ClassLoader class_loader) {
        ByteArrayOutputStream a;

    }

    public static String byteToHex(byte b) {
        String hexString = Integer.toHexString(b & 0xFF);
        if (hexString.length() < 2) {
            hexString = new StringBuilder(String.valueOf(0)).append(hexString).toString();
        }
        return hexString.toUpperCase();
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        if (bytes != null && bytes.length > 0) {
            for (int i = 0; i < bytes.length; i++) {
                String hex = byteToHex(bytes[i]);
                sb.append(hex);
            }
        }
        return sb.toString();
    }

    @Override
    public void on_application_class_loader(Application app, ClassLoader class_loader) {
//        XposedHelpers.findAndHookMethod("com.facebook.ads.internal.dynamicloading.DynamicLoaderFactory",
//                class_loader,
//                "makeLoader",
//                Context.class, new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        ClassLoader fbClassLoader = param.getResult().getClass().getClassLoader();
//                        log.i("find class load" + fbClassLoader);
//                        XposedHelpers.findAndHookMethod("com.facebook.ads.redexgen.X.5F",
//                                fbClassLoader,
//                                "getBidderToken", Context.class, new XC_MethodHook() {
//                                    @Override
//                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                        super.afterHookedMethod(param);
//                                        log.i("getBidderToken: " + param.getResult());
//                                    }
//                                });
//                    }
//                });


//        p360
//        XposedHelpers.findAndHookMethod("com.appsflyer.internal.c", class_loader, "AFInAppEventType", String.class, int.class, new XC_MethodHook() {
//
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                char[] encode = ((String) param.args[0]).toCharArray();
//                byte[] encodeb = new byte[encode.length * 2];
//                for (int i = 0; i < encode.length; i++) {
//                    byte low = (byte) (encode[i] & 0x00ff);
//                    byte hight = (byte) ((encode[i] & 0xff00) >> 8);
//                    log.i("dd: " + (encode[i] & 0x00ff) + "  " + ((encode[i] & 0xff00) >> 8));
//                    encodeb[i] = hight;
//                    encodeb[i + 1] = low;
//                }
//                log.i("encodestr_cksm: " + bytesToHex(encodeb));
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                char[] encode = ((String) param.args[0]).toCharArray();
//                byte[] encodeb = new byte[encode.length * 2];
//                for (int i = 0; i < encode.length; i++) {
//                    byte low = (byte) (encode[i] & 0x00ff);
//                    byte hight = (byte) ((encode[i] & 0xff00) >> 8);
//                    encodeb[i] = hight;
//                    encodeb[i + 1] = low;
//                }
//
//                log.i("encodestr_cksm: " + bytesToHex(encodeb) + "\t" + param.getResult());
//            }
//        });
////        private static String AFKeystoreWrapper(char p0,String p1,String p2,int p3,String p4){
////        ;
//        XposedHelpers.findAndHookMethod("com.appsflyer.internal.ai", class_loader, "AFKeystoreWrapper", char.class, String.class, String.class, int.class, String.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                log.i("encodestr_af: " + param.getResult());
//            }
//        });
//
////        com.appsflyer.internal.c$a
////        private static String values(String p0,int p1){
//        XposedHelpers.findAndHookMethod("com.appsflyer.internal.c$a", class_loader, "values", String.class, int.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                char[] encode = ((String) param.args[0]).toCharArray();
//                byte[] encodeb = new byte[encode.length * 2];
//                for (int i = 0; i < encode.length; i++) {
//                    byte low = (byte) (encode[i] & 0x00ff);
//                    byte hight = (byte) ((encode[i] & 0xff00) >> 8);
//                    encodeb[i] = hight;
//                    encodeb[i + 1] = low;
//                }
//                log.i("encodestr_kef: " + bytesToHex(encodeb) + "\t" + param.getResult());
//            }
//        });

    }
}
