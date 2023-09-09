#include <stdio.h>
#include <malloc.h>

int a = 47;


void foo() {
    int *arr = (int *)calloc(12, sizeof(int));
    for (int i = 0; i < 12; ++i) {
        arr[i] = a++;
    }
    printf("%p\n", arr);
    fflush(stdout);
    foo();
}

int main() {
    foo();
//    for (int i = 0; i < 16; ++i) {
//        int a = 48;
//        char *arr = (char *) calloc(i, 1);
//        for (int j = 0; j < i; ++j) {
//            arr[j] = a++;
//        }
//        printf("%p\n", arr);
//        fflush(stdout);
//    }
//    char *arr = (char *) calloc(16, 1);
//    printf("%p\n", arr);
//        fflush(stdout);
//    char *arr2 = (char *) calloc(80, 1);
//    printf("%p\n", arr2);
//    fflush(stdout);
    return 0;
}

