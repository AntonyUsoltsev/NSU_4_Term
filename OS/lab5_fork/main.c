#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int global_var = 42;

int main() {
    int local_var = 10;

    printf("global var:   %p (%d)\n", &global_var, global_var);
    printf("local var:    %p (%d)\n", &local_var, local_var);

    printf("Parent pid before fork %d\n", getpid());
    printf("Parent pid: %d, parent pid: %d\n", getpid(), getppid());

    int pid = fork();
    if (pid == -1) {
        perror("fork");
        exit(1);
    } else if (pid == 0) {
        printf("Child pid: %d, parent pid: %d\n", getpid(), getppid());
        sleep(40);
        printf("global var from child:   %p (%d)\n", &global_var, global_var);
        printf("local var from child:    %p (%d)\n", &local_var, local_var);

        global_var += 5;
        local_var += 4;

        printf("global var from child after change:   %p (%d)\n", &global_var, global_var);
        printf("local var from child after change:    %p (%d)\n", &local_var, local_var);

        exit(5);
    } else if (pid > 0) {
        sleep(30);
        printf("Parent pid after fork %d\n", getpid());
        printf("global var from parent:   %p (%d)\n", &global_var, global_var);
        printf("local var from parent:    %p (%d)\n", &local_var, local_var);


        int status;
        int child_pid = waitpid(pid, &status, 0);
        // Если убрать wait то дочерний процесс станет зомби
        if (child_pid == -1) {
            perror("waitpid");
            exit(1);
        }
        if (WIFEXITED(status)) {
            printf("Child process exited with code %d\n", WEXITSTATUS(status));
        } else if (WIFSIGNALED(status)) {
            printf("Child process exited due to signal %d\n", WTERMSIG(status));
        }
        sleep(10);
    }
    return 0;
}
