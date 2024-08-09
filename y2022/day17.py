from readFile import *

moving_rock = '@'
open_field = '.'
standing_rock = '#'
floor = '-'
wall = '|'


def part1(airflow):
    airflow_counter = 0
    max_airflow_counter = len(airflow)
    rock_counter = 0
    shape_counter = 0
    tower = [['+'] + [floor] * 7 + ['+']]
    for x in range(7):
        tower.insert(0, ['|'] + [open_field] * 7 + ['|'])

    while rock_counter < y2022:
        tower, n_parts = place_new_shape(tower, shape_counter)
        falling = True
        while falling:
            tower = gas_pushes_rock(airflow_counter, airflow, tower, n_parts)
            airflow_counter = (airflow_counter + 1) % max_airflow_counter
            tower, falling = rock_falling(tower, n_parts)
        shape_counter = (shape_counter + 1) % 5
        rock_counter += 1
        highest_point_with_rocks = find_highest_rock(tower)
        new_rows_needed = 7 - highest_point_with_rocks
        for i in range(new_rows_needed):
            tower.insert(0, ['|'] + [open_field] * 7 + ['|'])

    highest_point_with_rocks = find_highest_rock(tower)
    return len(tower) - highest_point_with_rocks - 1


# create a new rock
def place_new_shape(tower, shape_counter):
    if shape_counter == 0:
        n_shape = 4
        for i in range(4):
            tower[3][i + 3] = moving_rock
    elif shape_counter == 1:
        n_shape = 5
        tower[3][4] = moving_rock
        tower[1][4] = moving_rock
        for i in range(3):
            tower[2][i + 3] = moving_rock
    elif shape_counter == 2:
        n_shape = 5
        for i in range(3):
            tower[3][i + 3] = moving_rock
            tower[i + 1][5] = moving_rock
    elif shape_counter == 3:
        n_shape = 4
        for i in range(4):
            tower[i][3] = moving_rock
    elif shape_counter == 4:
        n_shape = 4
        tower[3][3] = moving_rock
        tower[3][4] = moving_rock
        tower[2][3] = moving_rock
        tower[2][4] = moving_rock
    else:
        print('Shape_counter =', shape_counter, 'is not accepted')
        exit(1)

    return tower, n_shape


# simulate jet of gas pushing on falling rock
def gas_pushes_rock(airflow_counter, airflow, tower, n_moving_rocks):
    direction = 1 if airflow[airflow_counter] == '>' else -1
    replace_with_open = []
    replace_with_moving = []
    for r in range(len(tower)):
        row = tower[r]
        if len(replace_with_open) == n_moving_rocks:
            break
        if moving_rock in row:
            for i in range(1, len(row) - 1):
                if row[i] == moving_rock:
                    if row[i + direction] == moving_rock or row[i + direction] == open_field:
                        replace_with_open.append((r, i))
                        replace_with_moving.append((r, i + direction))
                    else:
                        # it cannot move sideways
                        return tower

    for row, col in replace_with_open:
        tower[row][col] = open_field
    for row, col in replace_with_moving:
        tower[row][col] = moving_rock

    return tower


# simulate the rock falling 1 level
def rock_falling(tower, n_moving_rocks):
    replace_with_open = []
    replace_with_moving = []
    falling = True
    for r in range(len(tower)):
        row = tower[r]
        if len(replace_with_open) == n_moving_rocks or not falling:
            break
        if moving_rock in row:
            for col in range(len(row)):
                if row[col] == moving_rock:
                    row_below = tower[r + 1]
                    if row_below[col] == moving_rock or row_below[col] == open_field:
                        replace_with_open.append((r, col))
                        replace_with_moving.append((r + 1, col))
                    else:
                        falling = False

    if falling:
        for row, col in replace_with_open:
            tower[row][col] = open_field
        for row, col in replace_with_moving:
            tower[row][col] = moving_rock
    else:
        for r in range(len(tower)):
            row = tower[r]
            if moving_rock in row:
                for col in range(len(row)):
                    if row[col] == moving_rock:
                        row[col] = standing_rock

    return tower, falling


# NOTE: the highest point simulated is 0, and the floor is at len(tower) - 1
def part2(airflow, print_tower_bool):
    airflow_counter = 0
    max_airflow_counter = len(airflow)
    rock_counter = 0
    shape_counter = 0
    tower = [['+'] + [floor]*7 + ['+']]
    for x in range(7):
        tower.insert(0, ['|'] + [open_field]*7 + ['|'])

    while airflow_counter < max_airflow_counter:
        tower, n_parts = place_new_shape(tower, shape_counter)
        falling = True
        while falling:
            tower = gas_pushes_rock(airflow_counter, airflow, tower, n_parts)
            airflow_counter = (airflow_counter + 1) % max_airflow_counter
            tower, falling = rock_falling(tower, n_parts)
        shape_counter = (shape_counter + 1) % 5
        rock_counter += 1
        highest_point_with_rocks = find_highest_rock(tower)
        new_rows_needed = 7 - highest_point_with_rocks
        for i in range(new_rows_needed):
            tower.insert(0, ['|'] + [open_field] * 7 + ['|'])

    if print_tower_bool:
        print('\n')
        print('len airflow:', max_airflow_counter)
        print('shape counter:', shape_counter)
        print_tower(tower, True, True)
        print('\n')

    return -1


# find the highest point with a standing rock
def find_highest_rock(tower):
    for r in range(len(tower)):
        if standing_rock in tower[r]:
            return r

    print('Error: no standing rocks in tower')
    exit(1)


# print the tower (mainly used for debugging purposes)
def print_tower(tower, reverse_index=False, partial=False):
    for index in range(len(tower)):
        if not partial or index < 10 or index > len(tower) - 11:
            if reverse_index:
                i = str(len(tower) - index)
            else:
                i = str(index)
            print(''.join(tower[index]) + ' ' + i)


if __name__ == '__main__':
    file = line_str(17)
    print('part1:', part1(file[0]))
    # If you just want the answers, set boolean to False
    print('part2:', part2(file[0], True))
