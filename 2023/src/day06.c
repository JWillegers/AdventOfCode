#include "../inc/day06.h"

int parseRaceTime(Struct dimensions, char **input, long long *race_time);
int parseRaceDistance(Struct dimensions, char **input, long long *race_distance);

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

    int rt_pointer = parseRaceTime(dimensions, input, race_time);
    int rdtb_pointer = parseRaceDistance(dimensions, input, race_distance_to_beat);


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
        if (i < rdtb_pointer) {
            productPart1 *= numberOfWinningDistances;
        } else {
            sumPart2 = numberOfWinningDistances;
        }
    }

    formatAnswerLongLong(6, productPart1, sumPart2);
    destroyCharArray(input);
}

int parseRaceTime(Struct dimensions, char **input, long long *race_time) {
    int rt_pointer = 0;
    long long p2 = 0;
    int t = 0;
    while (t < dimensions.col) {
        if (isdigit(input[0][t])) {
            int l = 1;
            int num = input[0][t] - '0';
            p2 = p2 * 10 + (input[0][t] - '0');
            for (int i = t + 1; i <= dimensions.col; i++) {
                if (i != dimensions.col && isdigit(input[0][i])) {
                    num = num * 10 + (input[0][i] - '0');
                    p2 = p2 * 10 + (input[0][i] - '0');
                    l += 1;
                } else {
                    race_time[rt_pointer] = num;
                    rt_pointer += 1;
                    break;
                }
            }
            t += l;
        } else {
            t += 1;
        };
    }
    race_time[rt_pointer] = p2;
    return rt_pointer;
}

int parseRaceDistance(Struct dimensions, char **input, long long *race_distance) {
    int rd_pointer = 0;
    long long p2 = 0;
    int t = 0;
    while (t < dimensions.col) {
        if (isdigit(input[1][t])) {
            int l = 1;
            int num = input[1][t] - '0';
            p2 = p2 * 10 + (input[1][t] - '0');
            for (int i = t + 1; i <= dimensions.col; i++) {
                if (i != dimensions.col && isdigit(input[1][i])) {
                    num = num * 10 + (input[1][i] - '0');
                    p2 = p2 * 10 + (input[1][i] - '0');
                    l += 1;
                } else {
                    race_distance[rd_pointer] = num;
                    rd_pointer += 1;
                    break;
                }
            }
            t += l;
        } else {
            t += 1;
        };
    }
    race_distance[rd_pointer] = p2;
    return rd_pointer;
}
