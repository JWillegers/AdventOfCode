import copy
from readFile import *


def solution(list_of_elves, n):
    maximum = []
    for elf in list_of_elves:
        maximum.append(sum(elf))
        maximum.sort(reverse=True)
        if len(maximum) > n:
            del maximum[-1]
    return sum(maximum)


if __name__ == '__main__':
    file = line_str(1)
    elves = []
    calories = []
    for line in file:
        if len(line) == 0:
            elves.append(copy.deepcopy(calories))
            calories.clear()
        else:
            calories.append(int(line))
    print('part A:', solution(elves, 1))
    print('part B:', solution(elves, 3))
