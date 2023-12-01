#include "../inc/day01.h"
#include <ctype.h>

int main() {
    int rows = 1000;
    int cols = 60;

    char **input = createCharArray(rows, cols);
    readFile("day01.txt", input, NULL, rows, cols);
    long sumPart1 = 0;
    long sumPart2 = 0;
    for (int i = 0; i < rows; i++) {
        char numPart1[] = "00";
        char numPart2[] = "00";
        bool firstPart1 = true;
        bool firstPart2 = true;
        for (int j = 0; j < cols; j++) {
            char c = input[i][j];
            if (isdigit(c)) {
                if (firstPart1) {
                    numPart1[0] = c;
                    numPart1[1] = c;
                    firstPart1 = false;
                } else {
                    numPart1[1] = c;
                }
                if (firstPart2) {
                    numPart2[0] = c;
                    numPart2[1] = c;
                    firstPart2 = false;
                } else {
                    numPart2[1] = c;
                }
            } else if (isalpha(c)) {
                int num_ = numberAtChar(input[i], j);
                if (num_ != -1) {
                    if (firstPart2) {
                        numPart2[0] = num_ + '0';
                        numPart2[1] = num_ + '0';
                        firstPart2 = false;
                    } else {
                        numPart2[1] = num_ + '0';
                    }
                }
            } else {
                break;
            }
        }
        char *output;
        sumPart1 += strtol(numPart1, &output, 10);
        sumPart2 += strtol(numPart2, &output, 10);
    }
    printf("Part1: %ld\n", sumPart1);
    printf("Part2: %ld\n", sumPart2);
}