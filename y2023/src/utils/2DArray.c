#include "../../inc/utils/2DArray.h"

/*
 * Source: https://stackoverflow.com/a/5201762
 */

char** createCharArray(int row, int col) {
    char* values = calloc(row*col, sizeof(char));
    char** rows = malloc(row* sizeof(char*));

    for (int i=0; i<row; ++i) {
        rows[i] = values;
    }

    return rows;
}

void destroyCharArray(char** arr) {
    free(*arr);
    free(arr);
}

int** createIntArray(int row, int col) {
    int* values = (int*) calloc(row*(col + 1), sizeof(int));
    int** rows = (int**) malloc(row* sizeof(int*));
    for (int i=0; i<row; ++i) {
        rows[i] = values;
    }

    return rows;
}

void destroyIntArray(int** arr) {
    free(*arr);
    free(arr);
}
