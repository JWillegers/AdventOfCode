#ifndef ADVENTOFCODE_PARSER_H
#define ADVENTOFCODE_PARSER_H

#include <stdio.h>
#include <string.h>
#include "2DArray.h"
#include "math.h"

struct dimensions {
    int row, col;
};

typedef struct dimensions Struct;

//PUBLIC FUNCTIONS
Struct findFileDimensions(char file[]);
void readFile(char file[], char** chr, long *lng, int max_row, int max_col);
int numberAtChar(char* str, int index);
void formatAnswer(int day, long part1, long part2);
void formatAnswerLongLong(int day, long long part1, long long part2);

#endif //ADVENTOFCODE_PARSER_H
