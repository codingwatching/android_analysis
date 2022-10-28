#include "log.h"

FILE *log::log_file = nullptr;
mutex log::lock;
log_adapt log::adapt = log_adapt::use_adb;
lstring log::log_file_path;
lstring log::tag = _T("hook_jni");
lstring log::level_string[] = { _T("debug"),_T("warn"),_T("info"),_T("error") };
func_log log::flog;


class log auto_init;