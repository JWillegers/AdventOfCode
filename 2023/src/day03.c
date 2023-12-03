#include "../inc/day03.h"

int findSizeOfNumber(char **input, int i, int j);
void hasNeighbouringSymbol(char **input, int i, int j, int length, Struct s, char* key);

void day03() {
    char filename[] = "day03.txt";
    Struct dimensions = findFileDimensions(filename);
    if (dimensions.col == -1) { return; }
    char **input = createCharArray(dimensions.row, dimensions.col);
    readFile(filename, input, NULL, dimensions.row, dimensions.col);

    int sumPart1 = 0;
    int sumPart2 = 0;
    char* overflow;

    for (int i = 0; i < dimensions.row; i++) {
        int j = 0;
        while (j < dimensions.col) {
            char c = input[i][j];
            if (isdigit(c)) {
                int length = findSizeOfNumber(input, i, j);
                char key[] = "0000000";
                hasNeighbouringSymbol(input, i, j, length, dimensions, key);
                if (key[3] != '0') {
                    char num[] = "000";
                    switch (length) {
                        case 1:
                            num[2] = input[i][j];
                            break;
                        case 2:
                            num[1] = input[i][j];
                            num[2] = input[i][j + 1];
                            break;
                        case 3:
                            num[0] = input[i][j];
                            num[1] = input[i][j + 1];
                            num[2] = input[i][j + 2];
                            break;
                    }
                    int num_ = strtol(num, &overflow, 10);
                    sumPart1 += num_;

                    if (key[3] == '*') {
                        if (getIndex(key) == -1) {
                            insert(key, num_);
                        } else {
                            sumPart2 += num_ * get(key);
                        }
                    }
                }
                j += length;
            } else {
                j += 1;
            }
        }
    }
    formatAnswerLong(3, sumPart1, sumPart2);
    destroyCharArray(input);
}

int findSizeOfNumber(char **input, int i, int j) {
    if (!isdigit(input[i][j + 1])) {return 1;}
    if (!isdigit(input[i][j + 2])) {return 2;}
    return 3; //all numbers are smaller than 1000
}

void hasNeighbouringSymbol(char **input, int i, int j, int length, Struct s, char* key) {
    for (int a = max(0, i - 1); a <= min(i + 1, s.row - 1); a++) {
        for (int b = max(0, j - 1); b <= min(j + length, s.col - 1); b++) {
            if (!isdigit(input[a][b]) && input[a][b] != '.') {
                char n[] = "000";
                snprintf(n, 4, "%03d", a);
                char m[] = "000";
                snprintf(m,4, "%03d", b);
                for (int c = 0; c < 3; c++) {
                    key[c] = n[c];
                    key[c + 4] = m[c];
                }
                key[3] = input[a][b];
            }
        }
    }
}
