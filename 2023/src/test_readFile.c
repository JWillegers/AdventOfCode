#include "../inc/test_readFile.h"
#include "../inc/utils/readFile.h"
#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>


int main() {
    char *buf;
    buf=(char *)malloc(100*sizeof(char));
    getcwd(buf,100);
    printf("Current directory: %s \n",buf);
    line_char("test.txt", 4, 4);22
    return 0;
}