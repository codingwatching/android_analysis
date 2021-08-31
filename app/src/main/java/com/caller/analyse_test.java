package com.caller;

public class analyse_test {
    static {
//        System.loadLibrary("native_hook");
    }

    native static public boolean load_test();

    native static public boolean init_jni_hook(String tar_name);

}
