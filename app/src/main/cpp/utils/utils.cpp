#include <fcntl.h>
#include <unistd.h>
#include <sstream>
#include <iomanip>
#include <cstdlib>
#include <cstdio>
#include <ctime>

#include "utils.h"

using std::stringstream;

string get_uuid() {
    return "2aea3c86-e5e9-4930-a5d7-183dcaba007b";
    char uuid[37] = {0};
    int fd = open("/proc/sys/kernel/random/uuid", O_RDONLY);
    if (fd >= 0) {
        read(fd, uuid + 1, 36);
    }
    close(fd);
    return uuid;
}

int64_t get_time() {
    time_t timep;
    time(&timep);
    return timep;
}

string time_to_string(int64_t tick) {
    tm tm{};
#ifdef _WIN32
    localtime_s(&tm, &tick);
#else
    localtime_r((time_t *) &tick, &tm);
#endif

    std::stringstream stm;
    stm << std::put_time(&tm, "%Y-%m-%d %H:%M:%S");
    return stm.str();
}

int64_t string_to_time(const string &time_str, const string &fmt = "%Y-%m-%d %H:%M:%S") {
    tm tm{};
    std::stringstream stm;
    stm << time_str;
    stm >> std::get_time(&tm, "%Y-%m-%d %H:%M:%S");
    return mktime(&tm);
}

string format_string(const string &fmt, ...) {
    string buffer;
    buffer.resize(1024);
    va_list va;
    va_start(va, fmt);
    vsnprintf((char *) buffer.c_str(), buffer.size(), fmt.c_str(), va);
    va_end(va);
    return std::move(buffer);
}

