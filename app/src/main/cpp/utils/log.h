#pragma once

#include <cstdarg>
#include <cstdio>
#include <mutex>
#include <time.h>
#include <string>
#include <stdio.h>
#include <iomanip>
#include <sstream>
#include "utils.h"

#define ANDROID_SYSTEM

#ifdef ANDROID_SYSTEM
#define android_log_print __android_log_print

#include <android/log.h>
#include <jni.h>

#else
#define android_log_print(x) while(0){};
#endif

#ifdef UNICODE

using std::wstring;

#ifndef lstring
#define lstring wstring
#endif

#ifndef TCHAR
#define TCHAR wchar_t
#endif // !1

#ifndef _T
#define _T(x) L##x
#endif

#ifndef  lstrlen
#define lstrlen wcslen
#endif

#ifndef lstrftime
#define lstrftime wcsftime
#endif

#ifndef lstrncpy
#define lstrncpy wcsncpy
#endif

#ifndef lvsnprintf
#define lvsnprintf _vsnwprintf
#endif

#ifndef lto_string
#define lto_string to_wstring
#endif

#ifndef lprintf
#define lprintf wprintf
#endif

#else
using std::string;

#ifndef lstring
#define lstring string
#endif


#ifndef TCHAR
#define TCHAR char
#endif // !1

#ifndef _T
#define _T(x) x
#endif

#ifndef  lstrlen
#define lstrlen strlen
#endif

#ifndef lstrftime
#define lstrftime strftime
#endif

#ifndef lstrncpy
#define lstrncpy strncpy
#endif

#ifndef lvsnprintf
#define lvsnprintf vsnprintf
#endif

#ifndef lto_string
#define lto_string to_string
#endif

#ifndef lprintf
#define lprintf printf
#endif

#endif


#define logd(fmt, ...) log::_logd(fmt, ##__VA_ARGS__)
#define logi(fmt, ...) log::_logi(fmt, ##__VA_ARGS__)
#define logw(fmt, ...) log::_logw(fmt, ##__VA_ARGS__)
#define loge(fmt, ...) log::_loge(fmt, ##__VA_ARGS__)


using func_log = void (*)(const lstring &msg);

using std::mutex;

enum class log_adapt {
    use_file,
    use_printf,
    use_custom_func,
    use_adb,
    use_none
};

enum class log_level {
    debug,
    warn,
    info,
    error
};

class log {
    static const int buffer_size = 1024;
    static FILE *log_file;
    static std::mutex lock;
    static log_adapt adapt;
    static lstring level_string[];
    static func_log flog;
    static lstring log_file_path;
    static lstring tag;

public:
    log() {}

    ~log() {
        if (log_file) {
            fclose(log_file);
        }
    }

    static void set_file_path(lstring path) {
        if (path == "") {
            return;
        }
        log::log_file_path = path;
        log_file = fopen(path.c_str(), "w");
    }

    static void set_adapt(log_adapt adapt) {
        log::adapt = adapt;
    }

    static lstring get_tag() {
        return log::tag;
    }

    static void set_tag(const lstring &tag) {
        log::tag = tag;
    }

    static void set_custom_func(func_log flog) {
        log::flog = flog;
    }

    //__FILE__, __LINE__

    static void _logd(const TCHAR *msg, ...) {
        va_list va;
        va_start(va, msg);
        output_log(format_log(log_level::debug, msg, va));
        va_end(va);
    }

    static void _logi(const TCHAR *msg, ...) {
        va_list va;
        va_start(va, msg);
        output_log(format_log(log_level::info, msg, va));
        va_end(va);
    }

    static void _logw(const TCHAR *msg, ...) {
        va_list va;
        va_start(va, msg);
        output_log(format_log(log_level::warn, msg, va));
        va_end(va);
    }

    static void _loge(const TCHAR *msg, ...) {
        va_list va;
        va_start(va, msg);
        output_log(format_log(log_level::error, msg, va));
        va_end(va);
    }

    static string format_log(log_level level, const lstring &fmt, va_list va) {
        lstring buffer;

//        buffer += time_to_string(get_time());
//        buffer += " ";

        //auto tid = std::this_thread::get_id();
        //lstring stid = std::lto_string((*(_Thrd_t*)(char*)&tid)._Id);
        //lstrncpy(&buf[pos], stid.c_str(), stid.length());
        //pos += stid.length();
        buffer += tag;
        buffer += " ";

        buffer += level_string[(int) level];
        buffer += " : ";

        int len = buffer.length();
        buffer.resize(len + buffer_size);
        lvsnprintf(&buffer[len], buffer_size, fmt.c_str(), va);

        lstrncpy(&buffer[lstrlen(buffer.c_str())], _T("\n\r\x00"), 3);
        return std::move(buffer);
    }

    static void output_log(const lstring &buf) {
        switch (adapt) {
            case log_adapt::use_none:
                return;
            case log_adapt::use_file:
                log2file(buf);
                break;
            case log_adapt::use_printf:
                lprintf(buf.c_str());
                break;
            case log_adapt::use_custom_func:
                flog(buf);
                break;
            case log_adapt::use_adb:
                android_log_print(ANDROID_LOG_VERBOSE, tag.c_str(), buf.c_str(), _T(""));
                break;
        }
    }

    static void log2file(const lstring &msg) {
        lock.lock();
        fwrite(msg.c_str(), lstrlen(msg.c_str()), 1, log_file);
        lock.unlock();
    }
};
