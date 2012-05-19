.section .data
src: .ascii ".section .data\nsrc: .ascii \n.set len, . - src\n.global main\nmain:\npush $0x22    # quote\npush $0x6e5c  # newline\n# print data section block until initialization\nmov $0x4,%eax\nmov $0x1,%ebx\nmov $src,%ecx\nmov $27,%edx\nint $0x80\n# print quotes\nmov $0x4,%eax\nmov $0x1,%ebx\nlea 4(%esp),%ecx\nmov $0x01,%edx\nint $0x80\n# print src initialization string\nmov $0,%esi\nnext:\n    mov $0x4,%eax\n    mov $0x1,%ebx\n    mov $0x1,%edx\n    mov $src,%ecx\n    add %esi,%ecx\n    cmpb $0x0a,(%ecx)\n    jne not_new_line\n    mov %esp,%ecx\n    mov $0x02,%edx\nnot_new_line:\n    int $0x80\n    inc %esi\n    cmp $len, %esi\n    jl  next\n# close quotes\nmov $0x04,%eax\nmov $0x01,%ebx\nlea 4(%esp),%ecx\nmov $0x01,%edx\nint $0x80\n# straight forward print\nmov $0x4,%eax\nmov $0x1,%ebx\nmov $src,%ecx\nadd $27, %ecx\nmov $len,%edx\nsub $27, %edx\nint $0x80\n# clean up the stack\nadd $0x08,%esp\nmov $0,%eax\nret\n"
.set len, . - src
.global main
main:
push $0x22    # quote
push $0x6e5c  # newline
# print data section block until initialization
mov $0x4,%eax
mov $0x1,%ebx
mov $src,%ecx
mov $27,%edx
int $0x80
# print quotes
mov $0x4,%eax
mov $0x1,%ebx
lea 4(%esp),%ecx
mov $0x01,%edx
int $0x80
# print src initialization string
mov $0,%esi
next:
    mov $0x4,%eax
    mov $0x1,%ebx
    mov $0x1,%edx
    mov $src,%ecx
    add %esi,%ecx
    cmpb $0x0a,(%ecx)
    jne not_new_line
    mov %esp,%ecx
    mov $0x02,%edx
not_new_line:
    int $0x80
    inc %esi
    cmp $len, %esi
    jl  next
# close quotes
mov $0x04,%eax
mov $0x01,%ebx
lea 4(%esp),%ecx
mov $0x01,%edx
int $0x80
# straight forward print
mov $0x4,%eax
mov $0x1,%ebx
mov $src,%ecx
add $27, %ecx
mov $len,%edx
sub $27, %edx
int $0x80
# clean up the stack
add $0x08,%esp
mov $0,%eax
ret
