#include "../inc/day04.h"

void day04() {
    char filename[] = "day04.txt";
    Struct dimensions = findFileDimensions(filename);
    if (dimensions.col == -1) { return; }
    char **input = createCharArray(dimensions.row, dimensions.col);
    readFile(filename, input, NULL, dimensions.row, dimensions.col);

    int sumPart1 = 0;
    int copies[200] = {0};

    char *overflow;

    for (int i = 0; i < dimensions.row; i++) {
        int card[11]; //10 numbers + 1
        int index = 0;
        int winningNumbers = 0;
        int j = 10; //index of first needed digit
        bool foundSeparator = false;
        while (j < dimensions.col) {
            char c = input[i][j];
            if(isdigit(c)) {
                char num[] = "0";
                num[0] = c;
                if (j + 1 < dimensions.col && isdigit(input[i][j + 1])) {
                    num[1] = input[i][j + 1];
                    j += 2;
                } else {
                    j += 1;
                }
                int number = strtol(num, &overflow, 10);
                if (foundSeparator) {
                    if (valueinarray(number, card, 10)) {
                        winningNumbers += 1;
                    }
                } else {
                    card[index] = number;
                    index += 1;
                }
            } else {
                if (c == '|') {
                    foundSeparator = true;
                }
                j += 1;
            }
        }
        copies[i] += 1;
        if (winningNumbers > 0) {
            sumPart1 += pow(2, winningNumbers - 1);
            for (int k = i + 1; k <= min(winningNumbers + i, dimensions.row); k++) {
                copies[k] += copies[i];
            }
        }
    }

    int sumPart2 = 0;
    for (int a = 0; a < dimensions.row; a++) {
        sumPart2 += copies[a];
    }
    formatAnswer(4, sumPart1, sumPart2);
    destroyCharArray(input);
}
