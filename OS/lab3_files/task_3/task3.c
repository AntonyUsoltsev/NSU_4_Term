#include <stdio.h>
#include <stdlib.h>
//#include <sys/ptrace.h>
//include <sys/wait.h>
#include <unistd.h>
#include <string.h>
//#include <sys/user.h>

int main() {
    int pid = getpid();
    printf("%d", pid);
    char *proc = "/proc/";
    char *pagemap = "/pagemap";
    char *pid_buff = (char *) calloc(10, sizeof(char));
    if (pid_buff == NULL) {
        perror("bad calloc");
        exit(1);
    }
    sprintf(pid_buff, "%d", pid);
    int pid_buff_len = strlen(pid_buff);
    char *path = (char *) calloc(6 + 8 + pid_buff_len, sizeof(char));
    if (path == NULL) {
        perror("bad calloc");
        exit(1);
    }
    strcpy(path, proc);
    strcpy(path + 6, pid_buff);
    strcpy(path + 6 + pid_buff_len, pagemap);

    puts(path);
    FILE *file = fopen(path, "r");
    char *data = (char *) calloc(128, sizeof(char));
    if (data == NULL) {
        perror("bad calloc");
        exit(1);
    }
    while (fread(data, sizeof(char), 128, file) > 0) {
        puts(data);
    }
    //sleep(10);
    return 0;
}
//proc/pid/pagemap — это файл, который предоставляет подробную информацию об использовании виртуальной памяти процессом
// с определенным идентификатором процесса (PID). Это специальный файл в файловой системе /proc, который позволяет
// получить доступ к номерам фреймов страниц (PFN) каждой страницы виртуальной памяти процесса.
//
//Каждая строка в файле представляет собой страницу виртуальной памяти процесса и содержит 64-битное значение,
// представляющее номер кадра физической страницы этой страницы. Содержимое файла можно использовать для определения того,
// какие кадры физических страниц сопоставляются с какими страницами виртуальной памяти, и может предоставить
// информацию об использовании памяти и шаблонах доступа процесса.
//
//Для доступа к /proc/pid/pagemap требуются привилегии root или членство в группе proc. Формат и содержимое файла
// специфичны для ядра Linux и могут различаться в разных версиях и конфигурациях ядра.