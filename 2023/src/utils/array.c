#include "../../inc/utils/array.h"

/**
 * Check if value is in array
 * @param val
 * @param arr
 * @param n
 * @return
 */
bool valueinarray(int val, int *arr, size_t n) {
    for(size_t i = 0; i < n; i++) {
        if(arr[i] == val)
            return true;
    }
    return false;
}

/**
 * Find minimum value in array
 * @param arr
 * @param n
 * @return
 */
int minIntInArray(int *arr, size_t n) {
    int r = INT_MAX;
    for(size_t i = 0; i < n; i++) {
        min(r, arr[i]);
    }
    return r;
}

/**
 * Find maximum value in array
 * @param arr
 * @param n
 * @return
 */
int maxIntInArray(int *arr, size_t n) {
    int r = INT_MIN;
    for(size_t i = 0; i < n; i++) {
        max(r, arr[i]);
    }
    return r;
}