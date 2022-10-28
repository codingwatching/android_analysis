.text
.arch arm64v8a
.global test1
.global hooking_func
.type hooking_func,%function
hooking_func:
stp x29,x30,[sp,-0x10]!
stp x8,x9,[sp,-0x10]!
#0x1111222233334444
movk x8,0x4444,lsl 0
movk x8,0x3333,lsl 16
movk x8,0x2222,lsl 32
movk x8,0x1111,lsl 48
add x9,sp,0x20
stp x30,x30,[sp,-0x10]!
stp x8,x9,[sp,-0x10]!

movk x8,0x4444,lsl 0
movk x8,0x3333,lsl 16
movk x8,0x2222,lsl 32
movk x8,0x1111,lsl 48
blr x8

add sp,sp,0x20
ldp x8,x9,[sp],0x10
ldp x29,x30,[sp],0x10
ret
.end