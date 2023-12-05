#ifndef ADVENTOFCODE_MATH_H
#define ADVENTOFCODE_MATH_H

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

int min(int a, int b);
int max(int a, int b);
long long minLL(long long a, long long b);
long long maxLL(long long a, long long b);
bool isInBetween(int min, int max, int toCheck);
bool isInBetweenLL(long long min, long long max, long long toCheck);
long long charArrayToLong(char *arr);

#endif //ADVENTOFCODE_MATH_H
