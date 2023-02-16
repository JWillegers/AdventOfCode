from readFile import *
from copy import deepcopy


def part1(monkeys):
    while type(monkeys['root']) is not int:
        for monkey, item in monkeys.items():
            if type(item) is not int:
                m1 = monkeys[item[0]]
                m2 = monkeys[item[2]]
                if type(m1) is int and type(m2) is int:
                    if item[1] == '+':
                        monkeys[monkey] = m1 + m2
                    elif item[1] == '-':
                        monkeys[monkey] = m1 - m2
                    elif item[1] == '*':
                        monkeys[monkey] = m1 * m2
                    elif item[1] == '/':
                        assert(abs(int(m1 / m2) - m1 / m2) < 0.0001)
                        monkeys[monkey] = int(m1 / m2)
                    else:
                        print('ERROR, math operation not recognized')
                        exit(1)
    return monkeys['root']


def part2(monkeys):
    monkeys['root'][1] = '=='
    monkeys['humn'] = 'humn'
    while not monkeys['root'][3]:
        for monkey, item in monkeys.items():
            if type(item) is not int and monkey != 'humn' and not item[3]:
                m1 = monkeys[item[0]]
                m2 = monkeys[item[2]]
                if type(m1) is int and type(m2) is int:
                    if item[1] == '+':
                        monkeys[monkey] = m1 + m2
                    elif item[1] == '-':
                        monkeys[monkey] = m1 - m2
                    elif item[1] == '*':
                        monkeys[monkey] = m1 * m2
                    elif item[1] == '/':
                        assert (abs(int(m1 / m2) - m1 / m2) < 0.0001)
                        monkeys[monkey] = int(m1 / m2)
                    else:
                        print('ERROR, math operation not recognized')
                        exit(1)
                elif m1 == 'humn':
                    if type(m2) == int:
                        monkeys[monkey] = [m1, item[1], m2, True]
                elif m2 == 'humn':
                    if type(monkeys[item[0]]) == int:
                        monkeys[monkey] = [m1, item[1], m2, True]
                elif type(m1) is int and type(m2) is not int and m2[3]:
                    monkeys[monkey] = [m1, item[1], m2, True]
                elif type(m1) is not int and m1[3] and type(m2) is int:
                    monkeys[monkey] = [m1, item[1], m2, True]

    expression_left = monkeys['root'][0] if type(monkeys['root'][0]) == int else monkeys['root'][2]
    expression_right = monkeys['root'][2] if type(monkeys['root'][0]) == int else monkeys['root'][0]
    while type(expression_right) is list:
        operator = expression_right[1]
        if operator == '+':
            expression_left -= expression_right[0] if type(expression_right[0]) is int else expression_right[2]
            expression_right = expression_right[2] if type(expression_right[0]) is int else expression_right[0]
        elif operator == '-':
            if type(expression_right[0]) == int:
                expression_left = expression_right[0] - expression_left
                expression_right = expression_right[2]
            else:
                expression_left = expression_left + expression_right[2]
                expression_right = expression_right[0]
        elif operator == '*':
            expression_left /= expression_right[0] if type(expression_right[0]) == int else expression_right[2]
            expression_right = expression_right[2] if type(expression_right[0]) == int else expression_right[0]
        elif operator == '/':
            assert(type(expression_right[2]) is int)
            expression_left = expression_left * expression_right[2]
            expression_right = expression_right[0]
        else:
            print('ERROR, math operation not recognized')
            exit(1)

    return round(expression_left)


def parse(f):
    data = dict()
    for line in f:
        s = line.split(' ')
        s[0] = s[0].replace(':', '')
        if len(s) == 2:
            data[s[0]] = int(s[1])
        else:
            data[s[0]] = [s[1], s[2], s[3], False]
    assert(len(data.keys()) == len(f))
    return data


if __name__ == '__main__':
    test_file = line_str(21, True)
    parsed_test_file = parse(test_file)
    assert(part1(deepcopy(parsed_test_file)) == 152)
    assert(part2(deepcopy(parsed_test_file)) == 301)
    file = line_str(21)
    parsed_file = parse(file)
    print('part1:', part1(deepcopy(parsed_file)))
    print('part2:', part2(deepcopy(parsed_file)))
