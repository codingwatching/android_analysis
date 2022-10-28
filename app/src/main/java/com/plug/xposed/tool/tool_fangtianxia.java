package com.plug.xposed.tool;

import android.app.Application;
import android.content.Context;

import com.plug.base.plug_config;
import com.plug.export.frida_helper;
import com.plug.xposed.base.sub_plug_base;
import com.units.log;
import com.units.units;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class tool_fangtianxia extends sub_plug_base {
    public tool_fangtianxia(plug_config config, XC_LoadPackage.LoadPackageParam xposed_param) {
        super(config, xposed_param);
        name = "tool_fangtianxia";
    }

    @Override
    public void on_loadclass(String class_name, Class<?> cls, ClassLoader class_loader, XC_MethodHook.MethodHookParam param) {

    }

    @Override
    public void on_360_reinforce(Context context, ClassLoader class_loader) {

    }

    @Override
    public void on_application_class_loader(Application app, ClassLoader class_loader) {
        XposedHelpers.findAndHookMethod("com.amap.api.col.3sl.nj", class_loader, "a", byte[].class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        log.i("location call -> " + frida_helper.byte_2_hex_str((byte[]) param.args[0]));
                        log.i("location ret -> " + frida_helper.object_2_string(param.getResult()));
                        units.log_stack();
                    }
                }
        );
    }
}
