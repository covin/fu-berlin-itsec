.section .data
# 992312423124
x: .quad 0xe70a6df2d4
line: .asciz "y: %lx\n"
.extern printf
.global main
main:
# used as bit pointer, start at highest bit
mov $0x8000000000000000,%rax
# register containing y
mov $0x00,%rbx
loop:
    or %rax,%rbx
    cmp x(%rip),%rbx
    jge foo
        xor %rax,%rbx
    foo:
    shr $0x01,%rax
cmp $0x00,%rax
jne loop
# print y
mov  %rbx, %rsi
mov  $0x00, %rax
mov  $line, %rdi
call printf
mov $0x00, %rax
ret
