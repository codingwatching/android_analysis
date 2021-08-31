//
// Created by Administrator on 2021/8/12 0012.
//
#include <jni.h>

#include "../utils/jni_helper.h"
#include "../utils/log.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_caller_test_1jni_test_1args(JNIEnv *env, jobject thiz, jstring a1, jstring a2, jstring a3,
                                     jstring a4, jstring a5, jstring a6, jstring a7, jstring a8) {
    // TODO: implement test_args()

    logi("a1 %s", jstring2str(env, a1).c_str());
    logi("a2 %s", jstring2str(env, a2).c_str());
    logi("a3 %s", jstring2str(env, a3).c_str());
    logi("a4 %s", jstring2str(env, a4).c_str());
    logi("a5 %s", jstring2str(env, a5).c_str());
    logi("a6 %s", jstring2str(env, a6).c_str());
    logi("a7 %s", jstring2str(env, a7).c_str());
    logi("a8 %s", jstring2str(env, a8).c_str());
    return string2jstring(env, "success");
}

extern "C" JNIEXPORT jint JNICALL
Java_com_caller_test_1jni_test_1args2(JNIEnv *env, jclass clazz, jint a1, jint a2, jint a3, jint a4,
                                      jint a5, jint a6, jint a7, jint a8) {
    // TODO: implement test_args2()
    logi("a1 %p", env);
    logi("a1 %p", clazz);
    logi("a1 %d", a1);
    logi("a2 %d", a2);
    logi("a3 %d", a3);
    logi("a4 %d", a4);
    logi("a5 %d", a5);
    logi("a6 %d", a6);
    logi("a7 %d", a7);
    logi("a8 %d", a8);
    return 123;
}

JavaVM *gJavaVM;
extern "C" JNIEXPORT jint JNICALL
Java_com_caller_test_1jni_test_1args3(JNIEnv *env, jclass clazz, jint a1, jint a2, jint a3,
                                      jint a4) {
    // TODO: implement test_args3()
    JNIEnv *env2;
    gJavaVM->AttachCurrentThread(&env2, nullptr);
    return 1;
}

extern "C"
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    gJavaVM = vm;
    vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_4);
    return JNI_VERSION_1_4;
}

extern "C" void test() {
    JNIEnv *env;
    gJavaVM->GetEnv((void **) &env, JNI_VERSION_1_4);
    Java_com_caller_test_1jni_test_1args3(env, 0, 1, 2, 3, 4);
    Java_com_caller_test_1jni_test_1args2(env, 0, 1, 2, 3, 4, 5, 6, 7, 8);
}