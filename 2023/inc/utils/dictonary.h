#ifndef ADVENTOFCODE_DICTONARY_H
#define ADVENTOFCODE_DICTONARY_H


/*
 * https://www.geeksforgeeks.org/implementation-on-map-or-dictionary-data-structure-in-c/
 */

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

int getIndex(char key[]);
void insert(char key[], int value);
int get(char key[]);
void printMap();

#endif //ADVENTOFCODE_DICTONARY_H
