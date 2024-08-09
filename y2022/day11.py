import copy
import math
import re
from readFile import *


def solution(n_of_monkeys, monkey_items, monkey_data, needed_rounds, factor_3, lcm):
    inspections = [0 for monkey in range(n_of_monkeys)]
    for throw_round in range(needed_rounds):
        for index in range(len(monkey_data)):
            monkey = monkey_data[index]
            # Structure of if-statements only works because 'Operation: new = old + old' doesn't exists
            if monkey[1]:
                monkey_items, inspections = monkey_factor(monkey_items, inspections, index, monkey[3], monkey[4], monkey[5], factor_3, lcm)
            elif monkey[0]:
                monkey_items, inspections = monkey_multiplication(monkey_items, inspections, index, monkey[2], monkey[3], monkey[4], monkey[5], factor_3, lcm)
            else:
                monkey_items, inspections = monkey_addition(monkey_items, inspections, index, monkey[2], monkey[3], monkey[4], monkey[5], factor_3, lcm)
    highest_value = max(inspections)
    inspections.remove(highest_value)
    return highest_value * max(inspections)


def monkey_factor(monkey_items, inspections, monkey_index, test, monkey_true, monkey_false, factor_3, lcm):
    while len(monkey_items[monkey_index]) > 0:
        inspections[monkey_index] += 1
        old = monkey_items[monkey_index].pop(0)
        if factor_3:
            item = math.floor((old * old) / 3)
        else:
            item = (old * old) % lcm
        if item % test == 0:
            monkey_items[monkey_true].append(item)
        else:
            monkey_items[monkey_false].append(item)
    return monkey_items, inspections


def monkey_multiplication(monkey_items, inspections, monkey_index, factor, test, monkey_true, monkey_false, factor_3, lcm):
    while len(monkey_items[monkey_index]) > 0:
        inspections[monkey_index] += 1
        if factor_3:
            item = math.floor((monkey_items[monkey_index].pop(0) * factor) / 3)
        else:
            item = (monkey_items[monkey_index].pop(0) * factor) % lcm
        if item % test == 0:
            monkey_items[monkey_true].append(item)
        else:
            monkey_items[monkey_false].append(item)
    return monkey_items, inspections


def monkey_addition(monkey_items, inspections, monkey_index, add, test, monkey_true, monkey_false, factor_3, lcm):
    while len(monkey_items[monkey_index]) > 0:
        inspections[monkey_index] += 1
        if factor_3:
            item = math.floor((monkey_items[monkey_index].pop(0) + add) / 3)
        else:
            item = (monkey_items[monkey_index].pop(0) + add) % lcm
        if item % test == 0:
            monkey_items[monkey_true].append(item)
        else:
            monkey_items[monkey_false].append(item)
    return monkey_items, inspections


def parse_file(filez, n_monkeys):
    monkey_items = []
    ''''
    global_monkey_data structure:
    [
    multiplication: bool
    self: bool
    operation value: int
    test value: int
    throw true: int
    throws false: int
    ]
    '''
    monkey_data = [[] for monkey in range(n_monkeys)]
    for i in range(1, len(filez)):
        global_index = i // 7
        line = filez[i]
        if i % 7 == 1:
            # starting items
            numbers = line.split(':')[1][1:].split(', ')
            m = []
            for j in numbers:
                m.append(int(j))
            monkey_items.append(m)
        elif i % 7 == 2:
            # operation
            monkey_data[global_index].append('*' in line)
            monkey_data[global_index].append(not bool(re.search(r'\d', line)))  # check digit in string
            if not monkey_data[global_index][0] and monkey_data[global_index][1]:
                print('\'Operation: new = old + old\' is not supported')
                exit(0)
            if monkey_data[global_index][1]:
                monkey_data[global_index].append(None)
            else:
                monkey_data[global_index].append(int(line.split(' ')[-1]))
        elif 0 < i % 7 < 6:
            # test, if true, if false
            monkey_data[global_index].append(int(line.split(' ')[-1]))
    lcm = 1
    for m in monkey_data:
        lcm = math.lcm(lcm, m[3])
    return monkey_items, monkey_data, lcm


if __name__ == '__main__':
    test_file = line_str(11, True)
    test_monkey_items, test_monkey_data, test_lcm = parse_file(test_file, 4)
    assert(solution(4, copy.deepcopy(test_monkey_items), test_monkey_data, 20, True, test_lcm) == 10605)
    print(solution(4, copy.deepcopy(test_monkey_items), test_monkey_data, 10000, False, test_lcm))
    assert(solution(4, copy.deepcopy(test_monkey_items), test_monkey_data, 10000, False, test_lcm) == 2713310158)
    file = line_str(11)
    global_monkey_items, global_monkey_data, global_lcm = parse_file(file, 8)
    print('solution:', solution(8, copy.deepcopy(global_monkey_items), global_monkey_data, 20, True, global_lcm))
    print('part2:', solution(8, copy.deepcopy(global_monkey_items), global_monkey_data, 10000, False, global_lcm))
