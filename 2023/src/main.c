#include "../inc/main.h"

#ifdef _WIN32
#include <Windows.h>
#else
#include <unistd.h>
#endif

void customSleep();

int main() {
    //test();
    day01();
    customSleep();
    day02();
    customSleep();
    day03();
    customSleep();
    day04();
    customSleep();
    day05();
    customSleep();
    day06();
    customSleep();
}

/**
 * https://stackoverflow.com/questions/14818084/what-is-the-proper-include-for-the-function-sleep
 */
void customSleep() {
    //sleep:
    #ifdef _WIN32
    Sleep(100);
    #else
    usleep(100*1000);  /* sleep for 100 milliSeconds */
    #endif
}