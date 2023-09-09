# OPP_lab2_OpenMP
About:

    Parallelization of a program using OpenMp
    
Report:

https://docs.google.com/document/d/1RP5A9RwmzARODITTQWsnVq6IRJck7PWlM8tHO-O0m04/edit

Compile command

    Task 1) gcc -fopenmp OpenMP_task1.c -o task1 -Wpedantic -Werror -Wall -O3 --std=c99
    Task 2) gcc -fopenmp OpenMP_task2.c -o task1 -Wpedantic -Werror -Wall -O3 --std=c99 
    Task schedule) gcc -fopenmp OpenMP_task_schedule.c -o task_sch -Wpedantic -Werror -Wall -O3 --std=c99
    
Run command:
    
    OMP_NUM_THREADS=num ./task1
    OMP_NUM_THREADS=num ./task2
    OMP_NUM_THREADS=4 ./task_sch
    *num - number of threads on which need to run the program
    

