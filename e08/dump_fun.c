
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int foo()
{
    int a = 1;
    int b = 2;
    return a + 2*b;
}

void bar() {}

char* dump_fun() {
    unsigned char* start = (unsigned char*) &foo;
    unsigned char* stop =  (unsigned char*) &bar;
    unsigned int size = stop - start;
    unsigned int i;

    char* fun = malloc(size * sizeof(char));

    for (i = 0; i < size; i++) {
        if (i % 8 == 0) {
            printf("\n");
        }
        printf("%02x ", start[i]);
    }
    printf("\n\n");
    memcpy(fun, start, size*sizeof(char));
    return fun;
}

int main()
{
    char* buff = dump_fun();
    int x = ((int (*)()) buff)();
    printf("x=%d\n", x);
    exit(EXIT_SUCCESS);
}
