from readFile import *


def part1(all_blueprints):
    score = 0
    for blueprint in all_blueprints:
        ore_robot = 1
        clay_robot = 0
        obsidian_robot = 0
        geode_robot = 0
        ore = 0
        clay = 0
        obsidian = 0
        geode = 0
        time = 1
        max_ore_robot = max(blueprint[2], blueprint[3], blueprint[5])
        while time <= 24:
            # If we can make a geode robot, we make a geode robot
            building = [0, 0,]
            if ore >= blueprint[5] and obsidian >= blueprint[6]:
                geode_robot += 1
                ore -= blueprint[5]
                obsidian -= blueprint[6]

            # check if we do NOT have to save ores to make a geode robot next turn
            if ore_robot >= blueprint[5] or obsidian != blueprint[6] - obsidian_robot:
                '''
                Given the small timeframe we want to build:
                as many obsidian robots as possible
                as many clay robots as possible
                max(blueprint[2], blueprint[3], blueprint[5]) ore robots
                    until is max is reached, try to have clay and ore robots count as close as possible
                '''

                if ore >= blueprint[3] and clay >= blueprint[4]:
                    obsidian_robot += 1
                    ore -= blueprint[3]
                    clay -= blueprint[4]

                if (ore_robot == max_ore_robot or clay_robot < ore_robot) and ore > blueprint[2]:
                    clay_robot += 1
                    ore -= blueprint[2]

                if ore_robot < max_ore_robot and clay_robot >= ore_robot and ore > blueprint[1]:
                    ore_robot += 1
                    ore -= blueprint[1]


            ore += ore_robot
            clay += clay_robot
            obsidian += obsidian_robot
            geode += geode_robot

            time += 1
        print(blueprint[0], ':', geode)
        score += blueprint[0] * geode
    print(score)
    return score


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
    assert(part1(parsed_test_file) == 33)
    file = line_str(19)
    parsed_file = parse(file)
    print('part1:', part1(parsed_file))

