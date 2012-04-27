.cstring
_hello: .asciz "Hello, world\n"
.text
.globl _main
_main:
sub $8, %rsp
lea _hello(%rip), %rdi
call _printf
add $8, %rsp
ret
