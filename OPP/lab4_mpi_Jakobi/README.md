# OPP_lab4_mpi_Jakobi
About:

    Realization of Jakobi iteration method to solve differntial equation using MPI

Report:

    https://docs.google.com/document/d/11aMtec5YEDpIQDcyUih40o39EUOl0r8CIfaJxFjYHR8/edit

Compile command:

    mpicxx main.c -o main -Wpedantic -Werror -Wall -O3 --std=c++11

MPE compile command:

    mpecxx -mpilog  main.c -o main -Wpedantic -Werror -Wall -O3 --std=c++11

Run command:

    mpirun -oversubscribe -np $proc_count ./main
