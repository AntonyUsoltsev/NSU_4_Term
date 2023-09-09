# OPP_lab3_mpi_matrix_multiply
About:

    Matrix multiply using cartesian topology in MPI

Report:

https://docs.google.com/document/d/1TlhQDuDa5APXo7tZGId_21CRTWoeirKdkfYGhnKS5n0/edit

Compile command:

    mpicc main.c -o main -O3 --std=c99
    
MPE compile command:

    mpecc -mpilog main.c -o main -Wpedantic -Werror -Wall  -O3 --std=c99

Run command:

    mpirun -oversubscribe -np $proc_count ./main
