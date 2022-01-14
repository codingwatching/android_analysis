#include "log.h"

FILE* log::log_file;
mutex log::lock;
log_adapt log::adapt = log_adapt::use_printf;
lstring log::log_file_path;
lstring log::tag = _T("custom log");
lstring log::level_string[] = { _T("debug"),_T("warn"),_T("info"),_T("error") };
func_log log::flog;


class log auto_init;