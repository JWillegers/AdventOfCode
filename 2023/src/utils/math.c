#include "../../inc/utils/math.h"

/**
 * Return the lowest value of two numbers
 * @param a
 * @param b
 * @return
 */
int min(int a, int b) {
    if (a < b) { return a; }
    return b;
}

/**
 * Return the highest value of two numbers
 * @param a
 * @param b
 * @return
 */
int max(int a, int b) {
    if (a > b) { return a; }
    return b;
}

/**
 * Return the lowest value of two numbers
 * @param a
 * @param b
 * @return
 */
long long minLL(long long a, long long b) {
    if (a < b) { return a; }
    return b;
}

/**
 * Return the highest value of two numbers
 * @param a
 * @param b
 * @return
 */
long long maxLL(long long a, long long b) {
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
bool isInBetween(int min, int max, int toCheck) {
    return toCheck >= min && toCheck <= max;
}

bool isInBetweenLL(long long min, long long max, long long toCheck) {
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