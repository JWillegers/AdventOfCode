#include "../../inc/utils/array.h"

/**
 * Check if value is in array
 * @param val
 * @param arr
 * @param n
 * @return
 */
int getFirstIndexOfValue(int val, int *arr, size_t n) {
    for(size_t i = 0; i < n; i++) {
        if(arr[i] == val)
            return i;
    }
    return -1;
}

/**
 * Find minimum value in array
 * @param arr
 * @param n
 * @return
 */
int iMinInArray(int *arr, size_t n) {
    int r = INT_MAX;
    for(size_t i = 0; i < n; i++) {
        r = min(r, arr[i]);
    }
    return r;
}

/**
 * Find maximum value in array
 * @param arr
 * @param n
 * @return
 */
int iMaxInArray(int *arr, size_t n) {
    int r = INT_MIN;
    for(size_t i = 0; i < n; i++) {
        r = max(r, arr[i]);
    }
    return r;
}

/**
 * Find minimum value in array
 * @param arr
 * @param n
 * @return
 */
long long lMinInArray(long long *arr, size_t n) {
    long long r = LONG_LONG_MAX;
    for(size_t i = 0; i < n; i++) {
        r = min(r, arr[i]);
    }
    return r;
}

/**
 * Find maximum value in array
 * @param arr
 * @param n
 * @return
 */
long long lMaxInArray(long long *arr, size_t n) {
    long long r = LONG_LONG_MIN;
    for(size_t i = 0; i < n; i++) {
        r = max(r, arr[i]);
    }
    return r;
}