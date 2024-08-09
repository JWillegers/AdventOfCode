#include "../../inc/utils/math.h"

/**
 * Return the lowest value of two numbers
 * @param a
 * @param b
 * @return
 */
int imin(int a, int b) {
    if (a < b) { return a; }
    return b;
}

/**
 * Return the highest value of two numbers
 * @param a
 * @param b
 * @return
 */
int imax(int a, int b) {
    if (a > b) { return a; }
    return b;
}

/**
 * Return the lowest value of two numbers
 * @param a
 * @param b
 * @return
 */
long long llmin(long long a, long long b) {
    if (a < b) { return a; }
    return b;
}

/**
 * Return the highest value of two numbers
 * @param a
 * @param b
 * @return
 */
long long llmax(long long a, long long b) {
    if (a > b) { return a; }
    return b;
}

/**
 * Return if toCheck lies between min (incl.) and max (incl.)
 * @param min
 * @param max
 * @param toCheck
 * @return
 */
bool iIsInBetween(int min, int max, int toCheck) {
    return toCheck >= min && toCheck <= max;
}

bool llIsInBetween(long long min, long long max, long long toCheck) {
    return toCheck >= min && toCheck <= max;
}

long long charArrayToLong(char *arr) {
    long long l = 0;
    int i = 0;
    for (;;) {
        if (isdigit(arr[i])) {
            l = l * 10 + (arr[i] - '0');
            i++;
        } else {
            break;
        }
    }
    return l;
}