#include <stdio.h>
#include <unistd.h>
#include <stdbool.h>
#include <malloc.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <signal.h>

#define BUFF_SIZE 4096


void f_a() {
    printf("Pid: %d\n", getpid());

    sleep(1);

    char *args[] = {NULL}; // массив аргументов для exec, здесь нет аргументов

    execvp("./task2", args); // вызываем exec, передавая имя исполняемого файла (task2)

    printf("Hello world\n"); // этот код уже не выполнится после вызова exec
}

void recv_mem() {
    char buff[BUFF_SIZE];
    printf("Адрес  массива на стеке: %p\n", buff);
    sleep(1);
    recv_mem();
}

void sigsegv_handler() {
    printf("Caught SIGSEGV signal\n");
    exit(1);
}

void f_c() {
    printf("Pid: %d\n", getpid());
   // sleep(15);

   //  recv_mem();

//    for (int i = 0; i < 500; ++i) {
//        char *buff = malloc(BUFF_SIZE);
//        if (buff == NULL){
//            printf("bad malloc");
//        }
//        printf("Адрес массива в куче: %p\n", buff);
//        sleep(1);
//    }


    char *buff = mmap(NULL, BUFF_SIZE * 10, PROT_READ | PROT_WRITE | PROT_EXEC, MAP_ANONYMOUS | MAP_PRIVATE, -1, 0);
    printf("mmap: %p\n", buff);
    if (buff == MAP_FAILED) {
        printf("bad mmap");
    }
 //   sleep(5);
//    int ret = mprotect(buff, BUFF_SIZE * 10, PROT_READ);
//    if (ret == -1) {
//        perror("mprotect");
//    }
    //char *data1 = (char *) buff;
    signal(SIGSEGV, sigsegv_handler);
    buff[0] = 'A';

   int ret = mprotect(buff, BUFF_SIZE * 10, PROT_WRITE);
    if (ret == -1) {
        perror("mprotect");
    }

    char var = buff[0];
    printf("%c",var);


    sleep(10);

    ret = munmap(buff + 3 * BUFF_SIZE, 3 * BUFF_SIZE);
    if (ret == -1) {
        perror("munmap");
    }
    sleep(10);
}


int main() {
    //  f_a();

    char *buff = malloc(BUFF_SIZE);
    if (buff == NULL){
        printf("bad malloc");
    }
    printf("Адрес массива в куче: %p\n", buff);

    f_c();
    return 0;
}
