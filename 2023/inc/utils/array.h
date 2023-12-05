#ifndef ADVENTOFCODE_ARRAY_H
#define ADVENTOFCODE_ARRAY_H

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "math.h"

bool valueinarray(int val, int *arr, size_t n);
int minIntInArray(int *arr, size_t n);
int maxIntInArray(int *arr, size_t n);

#endif //ADVENTOFCODE_ARRAY_H
