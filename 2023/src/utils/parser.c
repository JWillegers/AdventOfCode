#include "../../inc/utils/parser.h"

void line_char(char file[], char **src, int max_row, int max_col) {
    //file is run from /AdventOfCode/cmake-build-debug
    char filename[] = "../2023/src/inputs/";
    strcat_s(filename, 128, file);
    FILE *fp = fopen(filename, "r");

    if (fp == NULL) {
        printf("Error: could not open file \"%s\" \n", filename);
        return;
    }

    char buffer[max_col];

    for (int i = 0; i < max_row; i++) {
        fgets(buffer, max_col + 2, fp);
        src[i] = strdup(buffer);
    }

    // close the file
    fclose(fp);
}

void line_int(char file[], long *src, int max_row, int max_col) {
    //file is run from /AdventOfCode/cmake-build-debug
    char filename[] = "../2023/src/inputs/";
    strcat_s(filename, 128, file);
    FILE *fp = fopen(filename, "r");

    if (fp == NULL) {
        printf("Error: could not open file \"%s\" \n", filename);
        return;
    }

    char buffer[max_col];
    char *overflow;

    for (int i = 0; i < max_row; i++) {
        fgets(buffer, max_col + 2, fp);
        src[i] = strtol(buffer, &overflow, 10);
    }

    // close the file
    fclose(fp);
}
