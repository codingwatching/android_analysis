package com.plug.xposed;

import android.app.Application;
import android.content.Context;

import com.plug.base.plug_config;
import com.plug.xposed.app_sub_plug.sub_plug_work;
import com.plug.xposed.base.sub_plug_base;
import com.plug.xposed.base.xposed_plug_base;
import com.plug.xposed.base.xposed_plug_common;
import com.plug.xposed.tool.tool_fangtianxia;
import com.plug.xposed.tool.tool_inject_jar;
import com.plug.xposed.tool.tool_okhttp_analyse;
import com.plug.xposed.tool.tool_pass_root;
import com.plug.xposed.tool.tool_socket_analyse;
import com.plug.xposed.tool.tool_sslunpinning;
import com.units.log;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class xposed_sub_plug_loader extends xposed_plug_base implements IXposedHookLoadPackage {
    List<sub_plug_base> tools = new ArrayList<>();

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        plug_config config = plug_config.load_plug_config();

        tools.add(new tool_inject_jar(config, lpparam));
        tools.add(new sub_plug_work(config, lpparam));

        if (config == null || lpparam.packageName.contains(config.analyse_packet_name)) {
            log.i("analyse inject process " + lpparam.processName + "!");

            Class ActivityThread = XposedHelpers.findClass("android.app.ActivityThread", lpparam.classLoader);
            XposedBridge.hookAllMethods(ActivityThread, "performLaunchActivity", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Application mInitialApplication = (Application) XposedHelpers.getObjectField(param.thisObject, "mInitialApplication");
                    ClassLoader class_loader = (ClassLoader) XposedHelpers.callMethod(mInitialApplication, "getClassLoader");
                    log.i("find class loader: " + class_loader.toString());
                    init_plug(xposed_plug_common.plug_name.plug_name_sub_plug_loader, mInitialApplication.getApplicationContext());
                    for (sub_plug_base analyse : tools) {
                        try {
                            analyse.on_application_class_loader(mInitialApplication, class_loader);
                            log.i("on_application_class_loader " + analyse.name);
                        } catch (Exception | Error e) {
                            log.i("on_application_class_loader error!" + e);
                        }
                    }
                }
            });
        }
    }
}
