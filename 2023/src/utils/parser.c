#include "../../inc/utils/parser.h"

/**
 * Read a .txt document
 * @param file filename
 * @param chr 2D array of characters
 * @param lng List of long
 * @param max_row amount of rows in the file
 * @param max_col max amount of characters in a row
 */
void readFile(char file[], char** chr, long *lng, int max_row, int max_col) {
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
        buffer[strcspn(buffer, "\n")] = '\0';
        if (strlen(buffer) > 0) {
            if (chr != NULL) {
                chr[i] = strdup(buffer);
            }

            if (lng != NULL) {
                lng[i] = strtol(buffer, &overflow, 10);
            }
        }
    }

    // close the file
    fclose(fp);
}
