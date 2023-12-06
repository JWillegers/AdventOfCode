#ifndef ADVENTOFCODE_ARRAY_H
#define ADVENTOFCODE_ARRAY_H

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "math.h"

bool valueInArray(int val, int *arr, size_t n);
int iMinInArray(int *arr, size_t n);
int iMaxInArray(int *arr, size_t n);
long long lMinInArray(long long *arr, size_t n);
long long lMaxInArray(long long *arr, size_t n);
#define minInArray(x, y) _Generic((x), int: iMinInArray, long long: lMinInArray, default: lMinInArray)(x, y)
#define maxInArray(x, y) _Generic((x), int: iMaxInArray, long long: lMaxInArray, default: lMaxInArray)(x, y)

#endif //ADVENTOFCODE_ARRAY_H
