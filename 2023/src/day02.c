#include "../inc/day02.h"

void day02() {
    char filename[] = "day02.txt";
    Struct dimensions = findFileDimensions(filename);
    if (dimensions.col == -1) { return; }
    char **input = createCharArray(dimensions.row, dimensions.col);
    readFile(filename, input, NULL, dimensions.row, dimensions.col);

    int sumPart1 = 0;
    int sumPowerPart2 = 0;

    for (int i = 0; i < dimensions.row; i++) {
        bool validPart1 = true;
        int lastSeenNumber = 0;
        int red = 1;
        int green = 1;
        int blue = 1;
        char* overflow;

        //split Game x from sets
        char * split = strtok(input[i], " ");
        while (split != NULL) {
            if (split[0] != 'G' || split[1] != ':') {
                if (isdigit(split[0])) {
                    lastSeenNumber = strtol(split, &overflow, 10);
                } else if (split[0] == 'r') {
                    if (lastSeenNumber > 12) {
                        validPart1 = false;
                    }
                    red = max(red, lastSeenNumber);
                    lastSeenNumber = 0;
                } else if (split[0] == 'g') {
                    if (lastSeenNumber > 13) {
                        validPart1 = false;
                    }
                    green = max(green, lastSeenNumber);
                    lastSeenNumber = 0;
                } else if (split[0] == 'b') {
                    if (lastSeenNumber > 14) {
                        validPart1 = false;
                    }
                    blue = max(blue, lastSeenNumber);
                    lastSeenNumber = 0;
                }
            }
            split = strtok(NULL, " ");
        }

        if (validPart1) {
            sumPart1 += i + 1;
        }
        sumPowerPart2 += red * blue * green;
    }

    formatAnswerLong(2, sumPart1, sumPowerPart2);
}
