package com.caller;

public class test_jni {
    static {
//        System.loadLibrary("test_jni");
    }

    static native public String test_args(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8);

    static native public int test_args2(int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8);

    static native public int test_args3(int a1, int a2, int a3, int a4);
}