#include <stdio.h>
#include <string.h>
#include "../../inc/utils/readFile.h"


void line_char(char file[], int max_row, int max_col) {
    //file is run from /AdventOfCode/cmake-build-debug
    char filename[] = "../2023/src/inputs/";
    strcat_s(filename, 128, file);
    FILE *fp = fopen(filename, "r");

    if (fp == NULL) {
        printf("Error: could not open file \"%s\" \n", filename);
        return;
    }

    char buffer[max_col];
    int i = 0;
    //https://www.geeksforgeeks.org/array-of-strings-in-c/

    while (fgets(buffer, max_col, fp))
        printf("%s", buffer);
        strcpy(txt[0], buffer);
        i += 1;

    // close the file
    fclose(fp);

}
