//
// Created by Administrator on 2021/8/12 0012.
//

#include "../hook_jni/jni_native_hook.h"
#include "../utils/jni_helper.h"

using func_JNI_OnLoad = jint (*)(JavaVM *vm, void *reserved);
using func_OnUnLoad = void (*)(JavaVM *jvm, void *reserved);
using func_test = void (*)();
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_caller_analyse_1test_init_1jni_1hook(JNIEnv *env, jclass clazz, jstring tar_name) {
    // TODO: implement init_jni_hook()
    // TODO: implement init_jni_hook()
    init_jni_hook(env, jstring2str(env, tar_name).c_str());
    return true;
}


extern "C"
JNIEXPORT jboolean JNICALL
Java_com_caller_analyse_1test_load_1test(JNIEnv *env, jclass clazz) {
    // TODO: implement load_test()

//    void *handle = dlopen("libtest_jni.so", RTLD_LAZY);
    init_jni_hook(env, "libtest_jni.so");

//    elf_info *fake_handle = fake_dlopen("/data/app|libtest_jni.so", RTLD_LAZY);
//    auto OnLoad = (func_JNI_OnLoad) fake_dlsym(fake_handle, "JNI_OnLoad");
////     OnUnLoad = (func_OnUnLoad) fake_dlsym(fake_handle, "JNI_OnUnLoad");
//    JavaVM *jvm;
//    env->GetJavaVM(&jvm);
//    OnLoad(jvm, nullptr);
//
//    auto test = (func_test) fake_dlsym(fake_handle, "test");
//    test();

//    fake_dlclose(fake_handle);
    return true;
}