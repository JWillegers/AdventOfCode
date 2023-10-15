#include "../inc/test_readFile.h"


int main() {
    char *buf;
    buf = (char *) malloc(100 * sizeof(char));
    getcwd(buf, 100);
    printf("Current directory: %s \n", buf);
    char **txt = createCharArray(4, 4);
    line_char("test.txt", txt, 4);
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            printf("%c", txt[i][j]);
        }
        printf("\n");
    }
    destroyCharArray(txt);
}