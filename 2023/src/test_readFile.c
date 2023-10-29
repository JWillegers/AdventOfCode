#include "../inc/utils/parser.h"


int main() {
    char *buf;
    buf = (char *) malloc(100 * sizeof(char));
    getcwd(buf, 100);
    printf("Current directory: %s \n", buf);

    printf("Now starting reading .txt as 2D char array:\n");
    char **txt = createCharArray(4, 4);
    line_char("test.txt", txt, 4, 4);
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            printf("%c", txt[i][j]);
        }
        printf("\n");
    }
    destroyCharArray(txt);
    sleep(1);

    printf("\nNow starting reading .txt as long ints:\n");
    long numbers[4];
    line_int("test.txt", numbers, 4, 4);
    for (int i = 0; i < 4; i++) {
        printf("%ld\n", numbers[i]);
    }
    return 0;
}