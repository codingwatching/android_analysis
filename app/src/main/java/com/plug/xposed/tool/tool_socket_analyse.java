package com.plug.xposed.tool;

import android.app.Application;
import android.content.Context;

import com.plug.base.plug_config;
import com.plug.export.frida_helper;
import com.plug.xposed.base.sub_plug_base;
import com.units.log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocket;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

class hook_info {
    public String class_name;
    public String method_name;

    public hook_info(String class_name, String method_name) {
        this.class_name = class_name;
        this.method_name = method_name;
    }
}

public class tool_socket_analyse extends sub_plug_base {
    List<hook_info> hook_list = new ArrayList<>();

    public tool_socket_analyse(plug_config config, XC_LoadPackage.LoadPackageParam xposed_param) {
        super(config, xposed_param);
        name = "tool_socket_analyse";

        hook_list.add(new hook_info("java.net.DatagramSocket", "send"));
        hook_list.add(new hook_info("javax.net.ssl.SSLSocket", "connect"));
        hook_list.add(new hook_info("java.net.Socket", "connect"));
        hook_list.add(new hook_info("java.net.InetSocketAddress", "$init"));
        hook_list.add(new hook_info("java.net.InetAddress", "getByAddress"));
        hook_list.add(new hook_info("java.net.InetAddress", "getAllByName"));
        hook_list.add(new hook_info("java.net.InetAddress", "getByName"));
        hook_list.add(new hook_info("java.net.Inet6Address", "getByAddress"));
        hook_list.add(new hook_info("java.net.Inet6Address", "getAllByName"));
        hook_list.add(new hook_info("java.net.Inet6Address", "getByName"));
    }

    @Override
    public void on_loadclass(String class_name, Class<?> cls, ClassLoader class_loader, XC_MethodHook.MethodHookParam param) {

    }

    @Override
    public void on_360_reinforce(Context context, ClassLoader class_loader) {

    }

    @Override
    public void on_application_class_loader(Application app, ClassLoader class_loader) {
        hook_methods(app, class_loader, hook_list);
    }

    public void print_param(XC_MethodHook.MethodHookParam param) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("this", param.thisObject);
        for (int index = 0; index < param.args.length; index++) {
            json.put("args" + index, param.args[index]);
        }
        json.put("ret", param.getResult());
        log.i("json:\t" + json.toString());
    }

    public void hook_methods(Application app, ClassLoader class_loader, List<hook_info> hook_list) {

        for (hook_info item : hook_list) {
            try {
                if (item.method_name.equals("$init")) {
                    XposedBridge.hookAllConstructors(XposedHelpers.findClass(item.class_name, class_loader), new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            log.i(item.class_name + "->$init(): ");
                            print_param(param);
                        }
                    });
                } else {
                    XposedBridge.hookAllMethods(XposedHelpers.findClass(item.class_name, class_loader), item.method_name, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            log.i(item.class_name + "->" + param.method.toString());
                            print_param(param);
                        }
                    });
                }

            } catch (Exception | Error e) {
                log.i("hook error:" + e);
            }
        }


    }

    public void hook_java_socket_init(Application app, ClassLoader class_loader) {
//        new Socket().connect();
//        new SSLSocket().getChannel();
//        new DatagramSocket().send();
//        new MulticastSocket().send();
//        new SSLSocket().getChannel();
//        ServerSocketChannel
//        XposedHelpers.findAndHookMethod("java.nio.channels.SocketChannel", class_loader, "connect", SocketAddress.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                log.i("SocketChannel->connect(): ");
//                log.i("                      args1 addr:" + param.args[0].toString());
//            }
//        });
//        DatagramSocket
        XposedHelpers.findAndHookMethod("java.net.DatagramSocket", class_loader, "send", DatagramPacket.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("DatagramSocket->send(): ");
                log.i("                      args1 addr:" + ((DatagramPacket) param.args[0]).getSocketAddress().toString());
            }
        });
//      SSLSocket
//       new  SSLSocket().connect();
//        XposedHelpers.findAndHookMethod("javax.net.ssl.SSLSocket", class_loader, "connect", SocketAddress.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                log.i("SSLSocket->connect(): ");
//                log.i("                      args1:" + param.args[0].toString());
//            }
//        });
        XposedHelpers.findAndHookMethod("javax.net.ssl.SSLSocket", class_loader, "connect", SocketAddress.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("SSLSocket->connect(): ");
                log.i("                      args1:" + param.args[0].toString());
                log.i("                      args1:" + param.args[1]);
            }
        });
//        Socket
//        XposedHelpers.findAndHookMethod("java.net.Socket", class_loader, "connect", SocketAddress.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                log.i("Socket->connect(): ");
//                log.i("                      args1:" + param.args[0].toString());
//            }
//        });
        XposedHelpers.findAndHookMethod("java.net.Socket", class_loader, "connect", SocketAddress.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("Socket->connect(): ");
                log.i("                      args1:" + param.args[0].toString());
                log.i("                      args1:" + param.args[1]);
            }
        });
//      InetSocketAddress
        XposedHelpers.findAndHookConstructor("java.net.InetSocketAddress", class_loader, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("InetSocketAddress->$init(): ");
                log.i("                      args1:" + param.args[0]);
            }
        });
        XposedHelpers.findAndHookConstructor("java.net.InetSocketAddress", class_loader, InetAddress.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("InetSocketAddress->$init(): ");
                log.i("                      args1:" + param.args[0].toString());
                log.i("                      args1:" + param.args[1]);
            }
        });
        XposedHelpers.findAndHookConstructor("java.net.InetSocketAddress", class_loader, String.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("InetSocketAddress->$init(): ");
                log.i("                      args1:" + param.args[0]);
                log.i("                      args1:" + param.args[1]);
            }
        });
    }

    public void hook_java_dns(Application app, ClassLoader class_loader) {
//        InetAddress
//        InetAddress.getByName()[];
        XposedHelpers.findAndHookMethod("java.net.InetAddress", class_loader, "getByAddress", String.class, byte[].class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("InetAddress->getByAddress(): ");
                log.i("                      args1:" + param.args[0]);
                log.i("                      args2:" + frida_helper.byte_2_ip_str((byte[]) param.args[1]));
            }
        });
        XposedHelpers.findAndHookMethod("java.net.InetAddress", class_loader, "getByAddress", byte[].class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("InetAddress->getByAddress(): ");
                log.i("                      args1:" + frida_helper.byte_2_ip_str((byte[]) param.args[1]));
            }
        });
        XposedHelpers.findAndHookMethod("java.net.InetAddress", class_loader, "getAllByName", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("InetAddress->getAllByName(): ");
                log.i("                      args1:" + param.args[0]);
                for (InetAddress item : (InetAddress[]) param.getResult()) {
                    log.i("                      ret:" + item.toString());
                }
            }
        });
        XposedHelpers.findAndHookMethod("java.net.InetAddress", class_loader, "getByName", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("InetAddress->getByName(): ");
                log.i("                      args1:" + param.args[0]);
                log.i("                      ret:" + ((InetAddress) param.getResult()).toString());
            }
        });
//      Inet6Address
        XposedHelpers.findAndHookMethod("java.net.Inet6Address", class_loader, "getByAddress", String.class, byte[].class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("Inet6Address->getByAddress(): ");
                log.i("                      args1:" + param.args[0]);
                log.i("                      args2:" + frida_helper.byte_2_ip_str((byte[]) param.args[1]));
            }
        });
        XposedHelpers.findAndHookMethod("java.net.Inet6Address", class_loader, "getByAddress", String.class, byte[].class, NetworkInterface.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("Inet6Address->getByAddress(): ");
                log.i("                      args1:" + param.args[0]);
                log.i("                      args2:" + frida_helper.byte_2_ip_str((byte[]) param.args[1]));
                log.i("                      args2:" + ((NetworkInterface) param.args[2]).toString());
            }
        });
        XposedHelpers.findAndHookMethod("java.net.Inet6Address", class_loader, "getByAddress", String.class, byte[].class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("Inet6Address->getByAddress(): ");
                log.i("                      args1:" + param.args[0]);
                log.i("                      args2:" + frida_helper.byte_2_ip_str((byte[]) param.args[1]));
                log.i("                      args2:" + param.args[2]);
            }
        });
        XposedHelpers.findAndHookMethod("java.net.Inet6Address", class_loader, "getByAddress", byte[].class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("Inet6Address->getByAddress(): ");
                log.i("                      args2:" + frida_helper.byte_2_ip_str((byte[]) param.args[0]));
            }
        });
        XposedHelpers.findAndHookMethod("java.net.Inet6Address", class_loader, "getAllByName", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("Inet6Address->getAllByName(): ");
                log.i("                      args1:" + param.args[0]);
                for (InetAddress item : (InetAddress[]) param.getResult()) {
                    log.i("                      ret:" + item.toString());
                }
            }
        });
        XposedHelpers.findAndHookMethod("java.net.Inet6Address", class_loader, "getByName", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log.i("Inet6Address->getByName(): ");
                log.i("                      args1:" + param.args[0]);
                log.i("                      ret:" + ((InetAddress) param.getResult()).toString());
            }
        });
    }
}
