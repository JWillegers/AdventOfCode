#include "../inc/utils/parser.h"


int test() {
    char *buf;
    buf = (char *) malloc(100 * sizeof(char));
    getcwd(buf, 100);
    printf("Current directory: %s \n", buf);

    int rows = 5;
    int col = 4;

    printf("Now starting reading .txt as 2D char array:\n");
    char **txt = createCharArray(rows, col);
    readFile("test.txt", txt, NULL, rows, col);
    for (int i = 0; i < rows; i++) {
        printf("%d: ", i);
        for (int j = 0; j < col; j++) {
            printf("%c", txt[i][j]);
        }
        printf("\n");
    }
    destroyCharArray(txt);
    sleep(1);

    printf("\nNow starting reading .txt as long ints:\n");
    long numbers[rows];
    readFile("test.txt", NULL, numbers, rows, col);
    for (int i = 0; i < rows; i++) {
        printf("%ld\n", numbers[i]);
    }
    return 0;
}