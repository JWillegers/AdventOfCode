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

/**
 * Check if there is a number written out in a array of char
 */
int numberAtChar(char* str, int index) {
    switch(str[index]) {
        case 'z':
            if (str[index + 1] == 'e' && str[index + 2] == 'r' && str[index + 3] == 'o') {
                return 0;
            }
            break;
        case 'o':
            if (str[index + 1] == 'n' && str[index + 2] == 'e') {
                return 1;
            }
            break;
        case 't':
            if (str[index + 1] == 'w' && str[index + 2] == 'o') {
                return 2;
            }
            if (str[index + 1] == 'h' && str[index + 2] == 'r' && str[index + 3] == 'e' && str[index + 4] == 'e') {
                return 3;
            }
            break;
        case 'f':
            if (str[index + 1] == 'o' && str[index + 2] == 'u' && str[index + 3] == 'r') {
                return 4;
            }
            if (str[index + 1] == 'i' && str[index + 2] == 'v' && str[index + 3] == 'e') {
                return 5;
            }
            break;
        case 's':
            if (str[index + 1] == 'i' && str[index + 2] == 'x') {
                return 6;
            }
            if (str[index + 1] == 'e' && str[index + 2] == 'v' && str[index + 3] == 'e' && str[index + 4] == 'n') {
                return 7;
            }
            break;
        case 'e':
            if (str[index + 1] == 'i' && str[index + 2] == 'g' && str[index + 3] == 'h' && str[index + 4] == 't') {
                return 8;
            }
            break;
        case 'n':
            if (str[index + 1] == 'i' && str[index + 2] == 'n' && str[index + 3] == 'e') {
                return 9;
            }
            break;
    }

    return -1;
}
