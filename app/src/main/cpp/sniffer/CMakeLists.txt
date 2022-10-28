cmake_minimum_required(VERSION 3.10.2)
project("sniffer")
set(EXECUTABLE_OUTPUT_PATH "${CMAKE_CURRENT_SOURCE_DIR}/../../../../libs/${ANDROID_ABI}")


find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

file(GLOB sniffer_src
        "./*.cpp"
        "./*.h"
        "../utils/log.h"
        "../utils/log.cpp"
        "../utils/utils.h"
        "../utils/utils.cpp"
        "../tinyxml2/*.h"
        "../tinyxml2/*.cpp"
        )

add_executable( # Sets the name of the library.
        sniffer.so
        # Provides a relative path to your source file(s).
        ${sniffer_src}
        )

target_link_libraries( # Specifies the target library.
        sniffer.so

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})