#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int global_init = 100;
int global_not_init;
const int global_const = 2;

void f_a() {
    // локальных не будет в nm
    int func_local_not_init;
    int func_local_inited = 1;
    static int func_static_not_init;
    static int func_static_init = 10;
    const int func_const = 25;
    printf("func local not init:  %p (%d)\n", &func_local_not_init, func_local_not_init);
    printf("func local init:      %p (%d)\n", &func_local_inited, func_local_inited);
    printf("func static not init: %p (%d)\n", &func_static_not_init, func_static_not_init);
    printf("func static init:     %p (%d)\n", &func_static_init, func_static_init);
    printf("func const init:      %p (%d)\n", &func_const, func_const);

}

int *f_d() {
    int return_var = 10;
    int *p = &return_var;
    return p;
}
int *f_d2() {
    int return_var = 45;
    int *p = &return_var;
    return p;
}

void f_e() {
    char *buff = malloc(100);
    if (buff == NULL) {
        puts("malloc error");
    }
    sprintf(buff, "%s", "Hello world!");
    puts(buff);
    free(buff);
    puts(buff);

    char *buff2 = malloc(100);
    if (buff2 == NULL) {
        puts("malloc error");
    }
    sprintf(buff2, "%s", "Hello world!");
    puts(buff2);
    buff2 += 50;
    free(buff2);
    puts(buff2);
}

int main() {
    printf("Pid: %d\n", getpid());

    puts("Global variables:");
    printf("global init:          %p (%d)\n", &global_init, global_init);
    printf("global not init:      %p (%d)\n", &global_not_init, global_not_init);
    printf("global const:         %p (%d)\n", &global_const, global_const);
    printf("Function f :          %p\n", f_a);
    f_a();
    sleep(5);

    int *a = f_d();
    int *b = f_d2();

   // int b = 8, h = 1008;
//    printf("helo hellp %d, %d\n", b, h);
    printf("Return from f_d value: %p %d\n ", a, *a);
    sleep(10);

  //  f_e();
    //sleep(10);

    if (setenv("MY_ENV_VAR", "this is my env var", 0) != 0) {
        puts("Bad env var");
    }
    puts(getenv("MY_ENV_VAR"));
    if (setenv("MY_ENV_VAR", "this is my second env var", 1) != 0) {
        puts("Bad env var");
    }
    puts(getenv("MY_ENV_VAR"));
    int i = 0;
    while (__environ[i] != NULL) {
        printf("%s\n", __environ[i]);
        i++;
    }
    return 0;
}
// про nm:
// локальные переменные не отображаются в nm
// R - rodata - read only data - R Большая => глобальной видимости
// B - bss - неинициализированные данные
// D - инициализированные данные