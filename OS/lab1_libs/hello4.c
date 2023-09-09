#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>

#include "hello_dynamic.h"

void load_lib_and_run_func(void) {
    void *library_pointer ;
    void (*dyn_print_hello)(void);
    char *error;
    library_pointer = dlopen("./libdyn_runtime.so", RTLD_LAZY);
    if (!library_pointer) {
        printf("dlopen() error: %s\n", dlerror());
        return;
    }

    dlerror();

    dyn_print_hello = dlsym(library_pointer, "hello_from_dyn_runtime_lib");

    error = dlerror();
    if (error != NULL) {
        printf("dlsym() failed: %s\n", dlerror());
        return;
    }

    dyn_print_hello();

    dlclose(library_pointer);
}

int main(int argc, char *argv[]) {
    load_lib_and_run_func();
    return 0;
}