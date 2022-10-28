package com.plug.xposed.tool;

import android.app.Application;
import android.content.Context;

import com.plug.base.plug_config;
import com.plug.xposed.base.sub_plug_base;
import com.units.log;

import java.util.Locale;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class tool_bugly_analyse extends sub_plug_base {
    public tool_bugly_analyse(plug_config config, XC_LoadPackage.LoadPackageParam xposed_param) {
        super(config, xposed_param);
        name = "bugly_analyse";
    }

    @Override
    public void on_loadclass(String class_name, Class<?> cls, ClassLoader class_loader, XC_MethodHook.MethodHookParam param) {

    }

    @Override
    public void on_360_reinforce(Context context, ClassLoader class_loader) {

    }

    // .overload()
//        .overload('android.content.Context')
//        .overload('com.tencent.bugly.proguard.u')
//        .overload('int')
//        .overload('boolean')
//        .overload('java.util.Map')
//        .overload('[B')
//        .overload('com.tencent.bugly.proguard.u', 'int')
//        .overload('java.lang.Runnable', 'long')
//        .overload('com.tencent.bugly.proguard.u', 'boolean')
//        .overload('java.lang.Runnable', 'boolean')
//        .overload('com.tencent.bugly.proguard.u', '[B')
//        .overload('int', 'long')
//        .overload('int', 'com.tencent.bugly.proguard.an')
//        .overload('long', 'boolean')
//        .overload('com.tencent.bugly.proguard.u', 'java.lang.Runnable', 'long')
//        .overload('java.lang.Runnable', 'boolean', 'boolean', 'long')
//        .overload('int', 'com.tencent.bugly.proguard.am', 'java.lang.String', 'java.lang.String', 'com.tencent.bugly.proguard.t', 'boolean')
//        .overload('int', 'com.tencent.bugly.proguard.am', 'java.lang.String', 'java.lang.String', 'com.tencent.bugly.proguard.t', 'long', 'boolean')
//        .overload('int', 'int', '[B', 'java.lang.String', 'java.lang.String', 'com.tencent.bugly.proguard.t', 'int', 'int', 'boolean', 'java.util.Map')
//
    @Override
    public void on_application_class_loader(Application app, ClassLoader class_loader) {
        XposedHelpers.findAndHookMethod("com.tencent.bugly.proguard.x", class_loader, "a", int.class, String.class, Object[].class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        String str = (String) param.args[1];
                        Object[] objArr = (Object[]) param.args[2];
                        if (str == null) {
                            str = "null";
                        } else if (!(objArr == null || objArr.length == 0)) {
                            str = String.format(Locale.US, str, objArr);
                        }
                        log.i("bugly_analyse: " + String.valueOf(param.args[0]) + str);
                    }
                }
        );

    }
}

