cmake_minimum_required(VERSION 3.25)
project(AdventOfCode)

set(CMAKE_CXX_STANDARD 23)

add_executable(AdventOfCode
        2023/src/utils/parser.c 2023/src/test_readFile.c 2023/inc/test_readFile.h 2023/src/utils/2DArray.c 2023/inc/utils/2DArray.h 2023/inc/utils/parser.h 2023/src/main.c 2023/inc/main.h 2023/src/day01.c 2023/inc/day01.h 2023/inc/utils/math.h 2023/src/utils/math.c 2023/src/day02.c 2023/inc/day02.h 2023/src/day03.c 2023/inc/day03.h 2023/src/utils/dictionary.c 2023/inc/utils/dictionary.h 2023/src/day04.c 2023/inc/day04.h 2023/src/utils/array.c 2023/inc/utils/array.h 2023/src/day05.c 2023/src/utils/dictionary.c)
