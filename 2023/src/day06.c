#include "../inc/day06.h"

int parseRow(Struct dimensions, char **input, long long *arr, int row);

void day06() {
    char filename[] = "day06.txt";
    Struct dimensions = findFileDimensions(filename);
    if (dimensions.col == -1) { return; }
    char **input = createCharArray(dimensions.row, dimensions.col);
    readFile(filename, input, NULL, dimensions.row, dimensions.col);

    long long race_time[5];
    long long race_distance_to_beat[5];
    long long productPart1 = 1;
    long long sumPart2 = 0;

    int rt_pointer = parseRow(dimensions, input, race_time, 0);
    int rdtb_pointer = parseRow(dimensions, input, race_distance_to_beat, 1);


    /*
     * Optimal race distance is at (race_time / 2)
     * Distance traveled = (race length - ms button hold) * ms button hold
     */
    for (int i = 0; i <= rt_pointer; i++) {
        long long numberOfWinningDistances = 0;
        long long optimalDistance = race_time[i] / 2;
        //check lower
        for (long long j = optimalDistance; j > 0; j--) {
            if ((race_time[i] - j) * j > race_distance_to_beat[i]) {
                numberOfWinningDistances += 1;
            } else {
                break;
            }
        }
        //check higher
        for (long long j = optimalDistance + 1; j < race_time[i]; j++) {
            if ((race_time[i] - j) * j > race_distance_to_beat[i]) {
                numberOfWinningDistances += 1;
            } else {
                break;
            }
        }

        //Process findings
        if (i < rdtb_pointer) {
            productPart1 *= numberOfWinningDistances;
        } else {
            sumPart2 = numberOfWinningDistances;
        }
    }

    formatAnswerLongLong(6, productPart1, sumPart2);
    destroyCharArray(input);
}

int parseRow(Struct dimensions, char **input, long long *arr, int row) {
    int pointer = 0;
    long long p2 = 0;
    int t = 0;
    while (t < dimensions.col) {
        if (isdigit(input[row][t])) {
            int l = 1;
            int num = input[row][t] - '0';
            p2 = p2 * 10 + (input[row][t] - '0');
            for (int i = t + 1; i <= dimensions.col; i++) {
                if (i != dimensions.col && isdigit(input[row][i])) {
                    num = num * 10 + (input[row][i] - '0');
                    p2 = p2 * 10 + (input[row][i] - '0');
                    l += 1;
                } else {
                    arr[pointer] = num;
                    pointer += 1;
                    break;
                }
            }
            t += l;
        } else {
            t += 1;
        };
    }
    arr[pointer] = p2;
    return pointer;
}
