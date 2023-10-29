#ifndef ADVENTOFCODE_PARSER_H
#define ADVENTOFCODE_PARSER_H

#include <stdio.h>
#include <string.h>
#include "2DArray.h"

//PUBLIC FUNCTIONS
void line_char(char file[], char** src, int max_row, int max_col);
void line_int(char file[], long *src, int max_row, int max_col);

#endif //ADVENTOFCODE_PARSER_H
