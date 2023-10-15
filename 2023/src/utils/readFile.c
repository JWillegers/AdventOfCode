#include "../../inc/utils/readFile.h"


void line_char(char file[], char **txt, int max_col) {
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

    while (fgets(buffer, max_col, fp)) {
        printf("%s", buffer);
        //TODO copy buffer into txt
        i += 1;
    }

    // close the file
    fclose(fp);
}
