#ifndef ADVENTOFCODE_2DARRAY_H
#define ADVENTOFCODE_2DARRAY_H

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

char** createCharArray(int row, int col);
void destroyCharArray(char** arr);
int** createIntArray(int row, int col);
void destroyIntArray(int** arr);

#endif //ADVENTOFCODE_2DARRAY_H
