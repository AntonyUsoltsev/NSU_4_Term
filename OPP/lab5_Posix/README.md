# OPP_lab5_Posix
About:

    Realization of a multithreaded multiprocessor program for solving a list of tasks using balancing

Report:

https://docs.google.com/document/d/1TqjqTs4m1hzJhDkmf6BbuQx7FDmLr937puN-o-Yy8IM/edit

Compile command:

    mpicxx main.cpp -o main 

Run command:

    mpirun -oversubscribe -np $proc_count ./main
