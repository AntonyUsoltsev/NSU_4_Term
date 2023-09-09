#include "hello_dynamic.h"

int main() {
    hello_from_dynamic_lib();
    return 0;
}

// gcc -fPIC -c hello_dynamic.c
// gcc -shared -o libdynamic.so hello_dynamic.o
// gcc hello3.c -L. -ldynamic -o hello3
// export LD_LIBRARY_PATH = ./