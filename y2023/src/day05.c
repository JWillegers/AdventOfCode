#include "../inc/day05.h"

#define MAX_MAP_SIZE 500


void day05() {
    char filename[] = "day05.txt";
    Struct dimensions = findFileDimensions(filename);
    if (dimensions.col == -1) { return; }
    char **input = createCharArray(dimensions.row, dimensions.col);
    readFile(filename, input, NULL, dimensions.row, dimensions.col);


    long long numbers[21];
    char *seeds = strtok(input[0], " ");
    int numSeeds = 0;
    char *overflow;

    //parse seeds
    while (seeds != NULL) {
        if (isdigit(seeds[0])) {
            numbers[numSeeds] = charArrayToLong(seeds);
            numSeeds++;
        }
        seeds = strtok(NULL, " ");
    }

    //parse maps
    long long src[MAX_MAP_SIZE];
    long long dest[MAX_MAP_SIZE];
    long long range[MAX_MAP_SIZE];

    int map_size = 0;
    int start_new_map[8];
    int start_size = 1;

    for (int i = 3; i < dimensions.row; i++) {
        if (isalpha(input[i][0])) {
            start_new_map[start_size] = map_size;
            start_size += 1;
        } else if (isdigit(input[i][0])) {
            char * split = strtok(input[i], " ");
            bool destDone = false;
            bool srcDone = false;
            while (split != NULL) {
                long long ll = charArrayToLong(split);
                if (ll < 0) {
                    printf("[day05] Unexpected negative number");
                    exit(1);
                }
                if (!destDone) {
                    dest[map_size] = ll;
                    destDone = true;
                } else if (!srcDone) {
                    src[map_size] = ll;
                    srcDone = true;
                } else {
                    range[map_size] = ll;
                    map_size += 1;
                }
                if (map_size == MAX_MAP_SIZE) {
                    printf("[day05] MAX_MAP_SIZE too small");
                    exit(1);
                }
                split = strtok(NULL, " ");
            }
        }
    }
    //add max map_size as well
    start_new_map[start_size] = map_size;

    for (int map = 0; map < 7; map++) {
        for (int i = 0; i < numSeeds; i++) {
            long long item = numbers[i];
            for (int j = start_new_map[map]; j < start_new_map[map + 1]; j++) {
                if (isInBetweenLL(src[j], src[j] + range[j] - 1, item)) {
                    numbers[i] = dest[j] + item - src[j];
                }
            }
        }
    }

    formatAnswerLongLong(5, minLongInArray(numbers, numSeeds), 0);

    //Destroy everything
    destroyCharArray(input);
}