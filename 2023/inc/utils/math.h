#ifndef ADVENTOFCODE_MATH_H
#define ADVENTOFCODE_MATH_H

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

bool iIsInBetween(int min, int max, int toCheck);
bool llIsInBetween(long long min, long long max, long long toCheck);
#define isInBetween(x, y, z) _Generic((x), int: iIsInBetween, long long: llIsInBetween, default: llIsInBetween)(x, y, z)

long long charArrayToLong(char *arr);

int imin(int a, int b);
long long llmin(long long a, long long b);
#define min(x, y) _Generic((x), int: imin, long long: llmin, default: llmin)(x, y)

int imax(int a, int b);
long long llmax(long long a, long long b);
#define max(x, y) _Generic((x), int: imax, long long: llmax, default: llmax)(x, y)

#endif //ADVENTOFCODE_MATH_H
