
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int foo()
{
    int a = 1;
    int b = 2;
    return a + 2*b;
}

int bar() {}

char* dump_fun() {
    unsigned char* start = (char*) &foo;
    unsigned char* stop =  (char*) &bar;
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

void main()
{
    char* buff = dump_fun();
    int x = ((int (*)()) buff)();
    printf("x=%d\n", x);
    exit(EXIT_SUCCESS);
}
