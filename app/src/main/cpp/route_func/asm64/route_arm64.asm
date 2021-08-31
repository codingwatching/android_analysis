.text
.arch arm64v8a
.global mod_for_asm
.global route_jni_func
.type route_jni_func,%function
route_jni_func:
//int64_t route_jni_func(   int64_t x0,
//                          int64_t x1,
//                          int64_t x2,
//                          int64_t x3 ,
//                          int64_t x4 ,
//                          int64_t x5 ,
//                          int64_t x6 ,
//                          int64_t x7 ,
//                          int64_t argc, //0x0
//                          void* stack_params_start, //0x8
//                          void* pcall);//0x10
stp x19,x30,[sp,-0x10]!

//申请栈必须对其
sub sp,sp,0x40
mov x19,sp
//保存参数
str x0,[x19,0x40-0x8]
str x1,[x19,0x40-0x10]
str x2,[x19,0x40-0x18]
str x3,[x19,0x40-0x20]
str x4,[x19,0x40-0x28]
str x5,[x19,0x40-0x30]
str x6,[x19,0x40-0x38]
str x7,[x19,0x40-0x40]

//保存栈指针
//栈对齐
ldr x0,[x19,0x40+0x10+0x0]
mov x1,2
#adr x9,mod_for_asm
#blr x9
bl mod_for_asm
cmp w0,0
beq nopadding
mov x0,0
str x0,[sp,0-0x8]
sub sp,sp,8
nopadding:

//读栈参数
ldr x0,[x19,0x40+0x10+0x0]
ldr x1,[x19,0x40+0x10+0x8]
ldr x9,[x19,0x40+0x10+0x10]

//计算参数尾 x1=(x1-1)*4
sub x0,x0,1
lsl x0,x0,3


mov x10,x19
loop:
//x0 < 0
cmp x0,0
blt call

//参数重压栈
ldr x2,[x1,x0]
sub x10,x10,8
sub sp,sp,8
str x2,[x10]
sub x0,x0,8
b loop

call:
//调用pcall
ldr x0,[x19,0x40-0x8]
ldr x1,[x19,0x40-0x10]
ldr x2,[x19,0x40-0x18]
ldr x3,[x19,0x40-0x20]
ldr x4,[x19,0x40-0x28]
ldr x5,[x19,0x40-0x30]
ldr x6,[x19,0x40-0x38]
ldr x7,[x19,0x40-0x40]
blr x9

ret:
//恢复堆栈

mov sp,x19
add sp,sp,0x40

ldp x19,x30,[sp],0x10
ret
.end







