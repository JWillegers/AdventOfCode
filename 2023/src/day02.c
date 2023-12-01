#include "../inc/day02.h"

void day02() {
    Struct dimensions = findFileDimensions("day01.txt");
    if (dimensions.col == -1) { return; }
    char **input = createCharArray(dimensions.row, dimensions.col);
    readFile("day01.txt", input, NULL, dimensions.row, dimensions.col);




    formatAnswerLong(2, 0, 0);
}
