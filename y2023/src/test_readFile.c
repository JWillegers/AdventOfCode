#include "../inc/utils/parser.h"


int test() {
    char *buf;
    buf = (char *) malloc(100 * sizeof(char));
    getcwd(buf, 100);
    printf("Current directory: %s \n", buf);


    Struct s = findFileDimensions("test.txt");

    printf("%d %d\n", s.row, s.col);

    printf("Now starting reading .txt as 2D char array:\n");
    char **txt = createCharArray(s.row, s.col);
    readFile("test.txt", txt, NULL, s.row, s.col);
    for (int i = 0; i < s.row; i++) {
        printf("%d: ", i);
        for (int j = 0; j < s.col; j++) {
            printf("%c", txt[i][j]);
        }
        printf("\n");
    }
    destroyCharArray(txt);
    sleep(1);

    printf("\nNow starting reading .txt as long ints:\n");
    long numbers[s.row];
    readFile("test.txt", NULL, numbers, s.row, s.col);
    for (int i = 0; i < s.row; i++) {
        printf("%ld\n", numbers[i]);
    }
    return 0;
}