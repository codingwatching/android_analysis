# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

project("local_caller")
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/${ANDROID_ABI}")
#set(CMAKE_C_FLAGS "${CMAKE_CXX_FLAGS} -fvisibility=hidden -fvisibility-inlines-hidden -O0 -g3")
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fvisibility=hidden -fvisibility-inlines-hidden -O0 -g3")
#set(CMAKE_CXX_VISIBILITY_PRESET hidden)
#set(CMAKE_VISIBILITY_INLINES_HIDDEN 1)
#C:/Users/a/AppData/Local/Android/Sdk/ndk/21.1.6352462/toolchains/llvm/prebuilt/windows-x86_64/bin/
#aarch64-linux-android-strip.exe -s D:\desktop\xposed_project\app\libs\arm64-v8a\libtest_jni.so -o D:\desktop\xposed_project\app\libs\arm64-v8a\libtest_jni.so --strip-all
enable_language(ASM)

add_definitions(-Wno-format-security)
# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

#include_directories(
#        ${DobbyHome}/include
#        ${DobbyHome}/source
#        ${DobbyHome}/builtin-plugin
#        ${DobbyHome}/builtin-plugin/AndroidRestriction
#        ${DobbyHome}/builtin-plugin/SymbolResolver
#        ${DobbyHome}/external/logging
#)
find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)


set(DobbyHome "./dobby")
#    set(GENERATE_SHARED OFF)
#set(DOBBY_DEBUG OFF)
add_subdirectory(${DobbyHome} dobby)


if (${CMAKE_ANDROID_ARCH_ABI} STREQUAL "armeabi-v7a" OR ${CMAKE_ANDROID_ARCH_ABI} STREQUAL "arm64-v8a")
    # arch prefix
    if (${CMAKE_ANDROID_ARCH_ABI} STREQUAL "arm64-v8a")
        file(GLOB native_hook_asm_src
                "route_func/asm64/*.asm")
    else ()
        file(GLOB native_hook_asm_src
                "route_func/asm/*.asm")
    endif ()


    file(GLOB native_hook_src
            "hook_libc/*.h"
            "hook_libc/*.cpp"
            "hook_libc/*.c"

            "utils/*.h"
            "utils/*.cpp"
            "utils/*.c"

            "test/*.h"
            "test/*.cpp"
            "test/*.c"

            "xposed/*.h"
            "xposed/*.cpp"
            "xposed/*.c"

            "libxhook/*.h"
            "libxhook/*.cpp"
            "libxhook/*.c"

            "dlfc/*.h"
            "dlfc/*.cpp"
            "dlfc/*.c"

            "dobby/include/dobby.h"

            "hook_jni/*.h"
            "hook_jni/*.cpp"
            "hook_jni/*.c"

            "route_func/*.h"
            "route_func/*.cpp"

            "json/*.h"
            "json/*.cpp"
            "json/*.inl"

            "system_hook/*.h"
            "system_hook/*.cpp"

            "*.h"
            "*.cpp"
            "*.c"
            "export.def"
            )


    add_library( # Sets the name of the library.
            native_hook

            # Sets the library as a shared library.
            SHARED
            # Provides a relative path to your source file(s).
            ${native_hook_asm_src}
            ${native_hook_src})


    target_link_libraries(native_hook dobby)


    # Specifies libraries CMake should link to your target library. You
    # can link multiple libraries, such as libraries you define in this
    # build script, prebuilt third-party libraries, or system libraries.

    target_link_libraries( # Specifies the target library.
            native_hook

            # Links the target library to the log library
            # included in the NDK.
            ${log-lib})

endif ()


file(GLOB test_src
        "hook_ssl/*.h"
        "hook_ssl/*.cpp"
        "hook_ssl/*.c"

        "dlfc/*.h"
        "dlfc/*.cpp"
        "dlfc/*.c"

        "utils/*.h"
        "utils/*.cpp"
        "utils/*.c"

        "dobby/include/dobby.h"
        )


add_library( # Sets the name of the library.
        ssl2
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        ${test_src}
        )

target_link_libraries(ssl2 dobby)

target_link_libraries( # Specifies the target library.
        ssl2

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})

add_subdirectory(sniffer)
