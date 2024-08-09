cmake_minimum_required(VERSION 3.25)
project(AdventOfCode)

set(CMAKE_CXX_STANDARD 23)

add_executable(AdventOfCode
        y2023/src/utils/parser.c y2023/src/test_readFile.c y2023/inc/test_readFile.h y2023/src/utils/2DArray.c y2023/inc/utils/2DArray.h y2023/inc/utils/parser.h y2023/src/main.c y2023/inc/main.h y2023/src/day01.c y2023/inc/day01.h y2023/inc/utils/math.h y2023/src/utils/math.c y2023/src/day02.c y2023/inc/day02.h y2023/src/day03.c y2023/inc/day03.h y2023/src/utils/dictionary.c y2023/inc/utils/dictionary.h y2023/src/day04.c y2023/inc/day04.h y2023/src/utils/array.c y2023/inc/utils/array.h y2023/src/day05.c y2023/src/utils/dictionary.c y2023/src/day06.c y2023/inc/day06.h y2023/src/utils/linkedList.c y2023/inc/utils/linkedList.h)
