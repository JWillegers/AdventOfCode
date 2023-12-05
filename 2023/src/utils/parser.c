#include "../../inc/utils/parser.h"

#define BUF_SIZE 65535

/**
 * Find the dimensions of the file
 * @param file filename
 */
Struct findFileDimensions(char file[]) {
    char filename[] = "../2023/src/inputs/";
    strcat_s(filename, 128, file);
    FILE *fp = fopen(filename, "r");

    Struct s;
    s.row = 0;
    s.col = -1;

    if (fp == NULL) {
        printf("Error: could not open file \"%s\" \n", filename);
        return s;
    }
    char buf[BUF_SIZE];
    for(;;) {
        fgets(buf, BUF_SIZE + 2, fp);
        buf[strcspn(buf, "\n")] = '\0';
        s.row += 1;
        s.col = max(s.col, strlen(buf));

        if (feof(fp)) {
            break;
        }
    }

    fclose(fp);
    return s;
}


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
        } else if (lng != NULL) {
            lng[i] = 0;
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

/**
 * Standard format of answers
 * @param day
 * @param part1
 * @param part2
 */
void formatAnswer(int day, long part1, long part2) {
    printf("===== Day %d ===== \nPart1: %ld \nPart2: %ld\n", day, part1, part2);
}

/**
 * Standard format of answers
 * @param day
 * @param part1
 * @param part2
 */
void formatAnswerLongLong(int day, long long part1, long long part2) {
    printf("===== Day %d ===== \nPart1: %lld \nPart2: %lld\n", day, part1, part2);
}
