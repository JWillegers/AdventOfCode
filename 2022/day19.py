from readFile import *

'''
setup of model
each row is a blueprint:
[[robots], [storage], [blueprints], highest_quality_level] where
each inner-array is setup as follows:
[ore, clay, obsidian, geode]
blueprints example:
1 obsidian robot costs [3, 4, 0, 0]

'''


def part1(model):
    for max_ore_robots in range(1, 10):
        for max_clay_robots in range(1, 10):
            for max_obsidian_robots in range(1, 10):
                time = 1
                model = reset_model(model)
                while time <= 24:
                    building = start_building(model, max_ore_robots, max_clay_robots, max_obsidian_robots)
                    model = collect_ores(model)
                    model = finish_building(model, building)
                    time += 1
                for row_index in range(len(model)):
                    model[row_index][3] = max(model[row_index][3], (row_index + 1) * model[row_index][1][3])

    score = 0
    print(model)
    for row in model:
        score += row[3]
    print(score)
    return score


def collect_ores(model):
    '''
    for each row, add 1 ore to storage for each robot
    '''
    for row in model:
        for i in range(4):
            row[1][i] += row[0][i]
    return model


def start_building(model, max_ore_robots, max_clay_robots, max_obsidian_robots):
    limits = [max_ore_robots, max_clay_robots, max_obsidian_robots, 9999]
    '''
    WRONG ASSUMPTION: first all ore robots are built, then all clay robots, 
    then all obsidian robots, then geode robots
    '''
    build = [[0, 0, 0, 0] for x in range(len(model))]

    for row in range(len(model)):
        for robot_type in range(4):
            # check if limit robots this met
            if model[row][0][robot_type] >= limits[robot_type]:
                continue

            # check if there are enough resources to build this robot
            enough = True
            for ore_type in range(4):
                if model[row][1][ore_type] < model[row][2][robot_type][ore_type]:
                    enough = False
                    break

            if enough:
                build[row][robot_type] += 1
                for ore_type in range(4):
                    model[row][1][ore_type] -= model[row][2][robot_type][ore_type]
                break

    return build


def finish_building(model, building):
    for row in range(len(model)):
        for robot_type in range(4):
            model[row][0][robot_type] += building[row][robot_type]
    return model


def reset_model(model):
    for row in model:
        row[0] = [1, 0, 0, 0]
        row[1] = [0, 0, 0, 0]
    return model


def parse(f):
    model = []
    for blueprint in f:
        row = []
        row.append([1, 0, 0, 0])
        row.append([0, 0, 0, 0])
        split = blueprint.split(' ')
        ore_for_ore = int(split[6])
        ore_for_clay = int(split[12])
        ore_for_obsidian = int(split[18])
        clay_for_obsidian = int(split[21])
        ore_for_geode = int(split[27])
        obsidian_for_geode = int(split[30])
        blueprint_row = []
        blueprint_row.append([ore_for_ore, 0, 0, 0])
        blueprint_row.append([ore_for_clay, 0, 0, 0])
        blueprint_row.append([ore_for_obsidian, clay_for_obsidian, 0, 0])
        blueprint_row.append([ore_for_geode, 0, obsidian_for_geode, 0])
        row.append(blueprint_row)
        row.append(0)
        model.append(row)
    return model


if __name__ == '__main__':
    test_file = line_str(19, True)
    parsed_test_file = parse(test_file)
    assert(part1(parsed_test_file) == 33)
    exit(0)
    file = line_str(19)
    parsed_file = parse(file)
    print('part1:', part1(parsed_file))

