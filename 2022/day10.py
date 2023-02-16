from readFile import *


def part1(program):
    cycle = 0
    register_x = 1
    signal_strength = 0
    for instruction in program:
        if instruction == 'noop':
            cycle, signal_strength = check_interesting_signal(cycle, register_x, signal_strength)
        else:
            # cycle 1
            value = int(instruction.split(' ')[1])
            cycle, signal_strength = check_interesting_signal(cycle, register_x, signal_strength)
            # cycle 2
            register_x += value
            cycle, signal_strength = check_interesting_signal(cycle, register_x, signal_strength)
    return signal_strength


def check_interesting_signal(cycle, register_x, signal_strength):
    cycle += 1
    if cycle % 20 == 0 and cycle % 40 != 0 and cycle <= 220:
        signal_strength += cycle * register_x
    return cycle, signal_strength


def part2(program):
    register_x = 1
    image = ['' for x in range(6)]
    sprite_current = '###' + '.'*37
    current_row = 0
    for instruction in program:
        index = len(image[current_row])
        if instruction == 'noop':
            image[current_row] += sprite_current[index]
            current_row, index = set_current_row(image, current_row, index)
        else:
            # cycle 1
            value = int(instruction.split(' ')[1])
            image[current_row] += sprite_current[index]
            current_row, index = set_current_row(image, current_row, index)
            # cycle 2
            image[current_row] += sprite_current[index + 1]
            current_row, index = set_current_row(image, current_row, index)
            register_x += value
            if register_x == -1:
                sprite_current = '#' + '.' * 39
            elif register_x == 0:
                sprite_current = '##' + '.' * 38
            else:
                sprite_current = sprite_current.replace('#', '.')
                sprite_current = sprite_current[:register_x - 1] + '###' + sprite_current[register_x + 2:]
                if len(sprite_current) > 40:
                    sprite_current = sprite_current[:40]
            assert(len(sprite_current) == 40)
    return image


def set_current_row(image, current_row, index):
    if len(image[current_row]) == 40:
        current_row += 1
        index = -1  # -1 because if row change for addx y cycle 1, cycle 2 needs index + 1 == 0
    return current_row, index


if __name__ == '__main__':
    file = line_str(10)
    print('part1:', part1(file))
    CRT = part2(file)
    print('part2:')
    for i in range(len(CRT)):
        line = CRT[i].replace('.', ' ')
        print(line)
