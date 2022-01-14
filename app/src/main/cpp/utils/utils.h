#pragma once

#include <string>
#include <mutex>

using std::string;
using std::mutex;

struct auto_lock {
    mutex *lock;

    auto_lock(mutex *lock) {
        this->lock = lock;
        this->lock->lock();
    }

    ~auto_lock() {
        this->lock->unlock();
    }
};

string get_uuid();


int64_t get_time();

string time_to_string(int64_t tick);

int64_t string_to_time(const string &time_str, const string &fmt);

string format_string(const string &fmt, ...);