
#include "hello_static.h"

int main() {
    hello_from_static_lib();
    return 0;
}

// gcc -c hello_static.c
// ar rc libstatic.a hello2.o
// ranlib libstatic.a
// gcc hello2.c -L. -lstatic -o hello2
