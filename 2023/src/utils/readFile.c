#include "../../inc/utils/readFile.h"


void line_char(char file[], char **txt, int max_row, int max_col) {
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
        txt[i] = strdup(buffer);
    }
    sleep(1);

    // close the file
    fclose(fp);
}
