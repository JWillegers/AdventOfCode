import copy
import math
from readFile import *
import numpy as np
from enum import Enum


class Mineral(Enum):
    ORE = 1
    CLAY = 2
    OBSIDIAN = 3
    GEODE = 4


def part1(all_blueprints):
    total_quality_levels = 0
    for blueprint in all_blueprints:
        current_score, best_seen = simulated_annealing(blueprint)
        print(current_score, best_seen)
        total_quality_levels += blueprint[0] * current_score
    return total_quality_levels


# https://en.wikipedia.org/wiki/Simulated_annealing and day16
def simulated_annealing(blueprint):
    # setup
    temp_start = 5e3  # starting temp
    temp_stop = 1.0  # stopping temp
    k = 1000  # iteration per temp level
    time_total = 1e6  # total allowed computation time
    time_iter = 1  # time per iteration
    t = time_total / (k * time_iter)  # iteration per temp level
    alpha = pow(temp_stop / temp_start, 1 / t)  # decreasing factor
    current_temp = temp_start
    counter = 1

    min_ore = 0
    min_clay = 1
    min_obsidian = 1
    max_all = 10
    current_order = [Mineral.ORE]
    for x in range(5):
        current_order.append(Mineral.CLAY)
    for x in range(3):
        current_order.append(Mineral.OBSIDIAN)
    current_score = calculate_score(current_order, blueprint)
    max_seen = current_score

    '''
    possible actions and chances
    1 less ore robot        7.5%
    1 more ore robot        7.5%
    1 less clay robot       7.5%
    1 more clay robot       7.5%
    1 less obsidian robot   7.5%
    1 more obsidian robot   7.5%
    swap 2 robots order     55%
    if current is on min and -1 is called, it will become +1 (and vice versa)
    '''
    change_one_robot_more_ore_less = 0.075

    while current_temp > temp_stop:
        # select action to perform
        random = np.random.uniform()  # default is between 0.0 and 1.0
        new_order = copy.deepcopy(current_order)

        if random < change_one_robot_more_ore_less and new_order.count(Mineral.ORE) > min_ore or\
                random < 2 * change_one_robot_more_ore_less and new_order.count(Mineral.ORE) >= max_all:
            new_order.remove(Mineral.ORE)  # removes first occurrence
        elif random < 2 * change_one_robot_more_ore_less:
            new_order.insert(0, Mineral.ORE)  # insert at index 0
        elif random < 3 * change_one_robot_more_ore_less and new_order.count(Mineral.CLAY) > min_clay or\
                random < 4 * change_one_robot_more_ore_less and new_order.count(Mineral.CLAY) >= max_all:
            new_order.remove(Mineral.CLAY)  # removes first occurrence
        elif random < 4 * change_one_robot_more_ore_less:
            new_order.insert(0, Mineral.CLAY)  # insert at index 0
        elif random < 5 * change_one_robot_more_ore_less and new_order.count(Mineral.OBSIDIAN) > min_obsidian or\
                random < 6 * change_one_robot_more_ore_less and new_order.count(Mineral.OBSIDIAN) >= max_all:
            new_order.remove(Mineral.OBSIDIAN)  # removes first occurrence
        elif random < 6 * change_one_robot_more_ore_less:
            new_order.insert(0, Mineral.OBSIDIAN)  # insert at index 0
        else:
            item1 = np.random.randint(0, len(new_order) - 1)
            item2 = np.random.randint(item1 + 1, len(new_order))
            new_order = swap(new_order, item1, item2)

        new_score = calculate_score(new_order, blueprint)

        if len(new_order) >= min_ore + min_clay + min_obsidian:
            # if new better than current, current = new
            if new_score > current_score:
                current_score = new_score
                max_seen = max(max_seen, current_score)
                current_order = new_order
            # if new equal or worse than current
            else:
                # pick uniform between 0 and 1
                random = np.random.uniform()  # default is between 0.0 and 1.0
                delta = new_score - current_score
                if math.exp(delta / current_temp) >= random:
                    current_score = new_score
                    max_seen = max(max_seen, current_score)
                    current_order = new_order

        counter += 1
        if counter == k:  # reach iterations at this temperature
            current_temp *= alpha
            counter = 1
    return current_score, max_seen


# https://en.wikipedia.org/wiki/2-opt
def swap(order, v1, v2):
    new_order = []
    for i in range(v1 + 1):
        new_order.append(order[i])
    for i in range(v2, v1, -1):
        new_order.append(order[i])
    for i in range(v2 + 1, len(order)):
        new_order.append(order[i])
    assert(len(new_order) == len(order))
    return new_order


def calculate_score(order, blueprint):
    ore_robots = 1
    ore_storage = 0
    clay_robots = 0
    clay_storage = 0
    obsidian_robots = 0
    obsidian_storage = 0
    geode_robots = 0
    geode_storage = 0
    time = 1
    new_order = copy.deepcopy(order)

    while time <= 24:
        new_order, build_new_robot, ore_storage, clay_storage, obsidian_storage = factory(new_order, blueprint, ore_storage, clay_storage, obsidian_storage)
        ore_storage += ore_robots
        clay_storage += clay_robots
        obsidian_storage += obsidian_robots
        geode_storage += geode_robots
        ore_robots += build_new_robot[0]
        clay_robots += build_new_robot[1]
        obsidian_robots += build_new_robot[2]
        geode_robots += build_new_robot[3]
        time += 1

    return geode_storage


def factory(order, blueprint, ore_storage, clay_storage, obsidian_storage):
    if len(order) == 0:
        if ore_storage >= blueprint[5] and obsidian_storage >= blueprint[6]:
            return order, [0, 0, 0, 1], ore_storage - blueprint[5], clay_storage, obsidian_storage - blueprint[6]
        return order, [0, 0, 0, 0], ore_storage, clay_storage, obsidian_storage
    elif order[0] == Mineral.ORE and ore_storage >= blueprint[1]:
        order.pop(0)
        return order, [1, 0, 0, 0], ore_storage - blueprint[1], clay_storage, obsidian_storage
    elif order[0] == Mineral.CLAY and ore_storage >= blueprint[2]:
        order.pop(0)
        return order, [0, 1, 0, 0], ore_storage - blueprint[2], clay_storage, obsidian_storage
    elif order[0] == Mineral.OBSIDIAN and ore_storage >= blueprint[3] and clay_storage >= blueprint[4]:
        order.pop(0)
        return order, [0, 0, 1, 0], ore_storage - blueprint[3], clay_storage - blueprint[4], obsidian_storage

    return order, [0, 0, 0, 0], ore_storage, clay_storage, obsidian_storage


def parse(f):
    bp = []
    for blueprint in f:
        split = blueprint.split(' ')
        blueprint_index = int(split[1].replace(':', ''))    # 0
        ore_for_ore = int(split[6])                         # 1
        ore_for_clay = int(split[12])                       # 2
        ore_for_obsidian = int(split[18])                   # 3
        clay_for_obsidian = int(split[21])                  # 4
        ore_for_geode = int(split[27])                      # 5
        obsidian_for_geode = int(split[30])                 # 6
        bp.append((blueprint_index, ore_for_ore, ore_for_clay, ore_for_obsidian, clay_for_obsidian, ore_for_geode, obsidian_for_geode))
    return bp


if __name__ == '__main__':
    test_file = line_str(19, True)
    parsed_test_file = parse(test_file)
    assert(calculate_score([Mineral.CLAY, Mineral.CLAY, Mineral.CLAY, Mineral.OBSIDIAN, Mineral.CLAY, Mineral.OBSIDIAN], parsed_test_file[0]) == 9)
    assert(part1(parsed_test_file) == 33)
    exit(0)
    file = line_str(19)
    parsed_file = parse(file)
    print('part1:', part1(parsed_file))

