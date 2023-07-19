from copy import deepcopy
from readFile import *

expedition = 'E'
temporary_expedition = 't' + expedition


def part1(input_map):
    # each route consists of 4 elements: the map, expedition x-cord, expedition y-cord, time
    input_map[0][1] = [expedition]
    #print_map(input_map)
    time = 0
    while True:
        time += 1
        input_map = move_expedition(move_blizzards(input_map))
        #print_map(input_map)
        #print(time)
        if input_map[len(input_map) - 1][len(input_map[0]) - 2] == [expedition]:
            return time


'''
move the blizzards to next field
to prevent double movement we append a temporary string
'''
def move_blizzards(current_map):
    for i in range(1, len(current_map) - 1):
        for j in range(1, len(current_map[0]) - 1):
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
            if len(current_map[i][j]) >= 1 and current_map[i][j][0] == 'E':
                while len(current_map[i][j]) >= 2 and 't' not in current_map[i][j][1]:
                    current_map[i][j].pop(1)
            else:
                temp = current_map[i][j]
                while len(current_map[i][j]) >= 1 and 't' not in current_map[i][j][0]:
                    current_map[i][j].pop(0)
                    temp = current_map[i][j]

    # replace temporary by actual
    for i in range(1, len(current_map) - 1):
        for j in range(1, len(current_map[0]) - 1):
            for k in range(len(current_map[i][j])):
                current_map[i][j][k] = current_map[i][j][k].replace('t', '')

    return current_map

'''
There can be multiple expeditions at the same time on a map
At time t, each expedition indicates that there could be an expedition on that tile
'''
def move_expedition(current_map):
    # place temporary expeditions
    for i in range(0, len(current_map) - 1):
        for j in range(1, len(current_map[0]) - 1):
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
                if len(current_map[i + 1][j]) == 0 and \
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
    ptf1 = part1(deepcopy(parsed_test_file))
    print(ptf1)
    assert(ptf1 == 18)
    file = grid(24)
    parsed_file = parse(file)
    print("part1:", part1(deepcopy(parsed_file)))

