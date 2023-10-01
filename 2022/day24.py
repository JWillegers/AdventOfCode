from copy import deepcopy
from readFile import *

expedition = 'E'
temporary_expedition = 't' + expedition


def part1(input_map):
    input_map[0][1].append(expedition)
    time = 0
    while True:
        time += 1
        input_map = move_expedition(move_blizzards(input_map))
        if input_map[len(input_map) - 1][len(input_map[0]) - 2] == [expedition]:
            return time, input_map


'''
move the blizzards to next field
to prevent double movement we append a temporary string
'''
def move_blizzards(current_map):
    for i in range(1, len(current_map)):
        for j in range(1, len(current_map[0])):
            for blizzard in current_map[i][j]:
                match blizzard:
                    case '>':
                        if j < len(current_map[0]) - 2:
                            current_map[i][j + 1].append('t>')
                        else:
                            current_map[i][1].append('t>')
                    case '<':
                        if j > 1:
                            current_map[i][j - 1].append('t<')
                        else:
                            current_map[i][len(current_map[0]) - 2].append('t<')
                    case '^':
                        if i > 1:
                            current_map[i - 1][j].append('t^')
                        else:
                            current_map[len(current_map) - 2][j].append('t^')
                    case 'v':
                        if i < len(current_map) - 2:
                            current_map[i + 1][j].append('tv')
                        else:
                            current_map[1][j].append('tv')
            # delete old blizzards
            if i < len(current_map) - 1 and j < len(current_map[0]) - 1:
                if len(current_map[i][j]) >= 1 and current_map[i][j][0] == 'E':
                    while len(current_map[i][j]) >= 2 and 't' not in current_map[i][j][1]:
                        current_map[i][j].pop(1)
                else:
                    temp = current_map[i][j]
                    while len(current_map[i][j]) >= 1 and 't' not in current_map[i][j][0]:
                        current_map[i][j].pop(0)
                        temp = current_map[i][j]

    # replace temporary by actual
    for i in range(len(current_map) - 1):
        for j in range(len(current_map[0]) - 1):
            for k in range(len(current_map[i][j])):
                current_map[i][j][k] = current_map[i][j][k].replace('t', '')

    return current_map

'''
There can be multiple expeditions at the same time on a map
At time t, each expedition indicates that there could be an expedition on that tile
'''
def move_expedition(current_map):
    # place temporary expeditions
    for i in range(0, len(current_map)):
        for j in range(1, len(current_map[0])):
            if expedition in current_map[i][j]:
                # check if we can wait. If not, remove expedition
                if len(current_map[i][j]) > 1:
                    current_map[i][j].remove(expedition)
                # move left
                if len(current_map[i][j + 1]) == 0 and \
                        (expedition not in current_map[i][j + 1] or temporary_expedition not in current_map[i][j + 1]):
                    current_map[i][j + 1].append(temporary_expedition)
                # move right
                if len(current_map[i][j - 1]) == 0 and \
                        (expedition not in current_map[i][j - 1] or temporary_expedition not in current_map[i][j - 1]):
                    current_map[i][j - 1].append(temporary_expedition)
                # move down
                if i < len(current_map) - 1 and len(current_map[i + 1][j]) == 0 and \
                        (expedition not in current_map[i + 1][j] or temporary_expedition not in current_map[i + 1][j]):
                    current_map[i + 1][j].append(temporary_expedition)
                # move up. An extra check to know if the expedition is at (i, 0) is not needed at
                # (i, -1) == (i, len(current_map)) is ['#'] if (i, 0) has an expedition
                if len(current_map[i - 1][j]) == 0 and \
                        (expedition not in current_map[i - 1][j] or temporary_expedition not in current_map[i - 1][j]):
                    current_map[i - 1][j].append(temporary_expedition)

    # replace temporary by actual
    for i in range(1, len(current_map)):
        for j in range(1, len(current_map[0])):
            for k in range(len(current_map[i][j])):
                current_map[i][j][k] = current_map[i][j][k].replace('t', '')

    return current_map


def part2(input_part2):
    time = input_part2[0]
    current_map = clear_expeditions(input_part2[1])
    current_map[len(current_map) - 1][len(current_map[0]) - 2].append(expedition)
    while expedition not in current_map[0][1]:
        time += 1
        current_map = move_expedition(move_blizzards(current_map))

    current_map = clear_expeditions(current_map)
    current_map[0][1].append(expedition)
    while expedition not in current_map[len(current_map) - 1][len(current_map[0]) - 2]:
        time += 1
        current_map = move_expedition(move_blizzards(current_map))
    return time


def clear_expeditions(input_map):
    for i in range(len(input_map)):
        for j in range(len(input_map[0])):
            if expedition in input_map[i][j]:
                input_map[i][j].remove(expedition)
    return input_map


def parse(f):
    for i in range(len(f)):
        for j in range(len(f[0])):
            if f[i][j] == '.':
                f[i][j] = []
            else:
                f[i][j] = [f[i][j]]

    return f


'''
Used for debugging purposes
'''
def print_map(my_map):
    print(' ')
    for row in my_map:
        line = ""
        for i in row:
            if len(i) == 0:
                line += '.'
            elif i == ['#']:
                line += '#'
            elif len(i) > 1:
                line += str(len(i))
            else:
                line += i[0]
        print(line)


if __name__ == "__main__":
    test_file = grid(24, test=True)
    parsed_test_file = parse(test_file)
    ptf1 = part1(parsed_test_file)
    assert(ptf1[0] == 18)
    ptf2 = part2(ptf1)
    assert(ptf2 == 54)
    file = grid(24)
    parsed_file = parse(file)
    map_after_part1 = part1(parsed_file)
    print("part1:", map_after_part1[0])
    print("part2:", part2(map_after_part1))

