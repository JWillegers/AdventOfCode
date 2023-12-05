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
 * Return if toCheck lies between min (incl.) and max (incl.)
 * @param min
 * @param max
 * @param toCheck
 * @return
 */
bool isInBetween(int min, int max, int toCheck) {
    return toCheck >= min && toCheck <= max;
}