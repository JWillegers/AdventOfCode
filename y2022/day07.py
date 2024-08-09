import copy
from readFile import *


def part1(system):
    total = 0
    for i in range(len(system)):
        name, path, size, folder = system[i]
        parent_path = copy.deepcopy(path)
        parent_name = parent_path.pop()
        if folder and size <= 100000:
            total += size
        for j in range(i + 1, len(system)):
            if system[j][0] == parent_name and system[j][1] == parent_path:
                system[j] = (system[j][0], system[j][1], system[j][2] + size, system[j][3])  # += operator gave error
                break
    return total


def part2(system):
    smallest_size = 70 * (10 ** 6)
    max_allowed_space_used = 40 * (10 ** 6) # 70mil - 30mil
    current_used = 0
    # find how much space is currently used
    for i in range(len(system) - 1, -1, -1):
        name, path, size, folder = system[i]
        if path == ['/']:
            current_used += size
        else:
            break

    # find the smallest dir that satisfies puzzle conditions
    for i in range(len(system)):
        name, path, size, folder = system[i]
        if folder and current_used - size <= max_allowed_space_used:
            smallest_size = min(smallest_size, size)

    return smallest_size



def create_directories(file):
    # every element in system looks like this: (name, path, size, folder)
    # where name is a string
    # where path is a list with the path, excluding current file. Example with current file: ['AdventOfCode2022', 'solutions']
    # where path is an integer
    # where folder is a boolean
    system = []
    current_path = []
    for line in file:
        if line == '$ cd /':
            current_path = ['/']
        elif line == '$ cd ..':
            current_path.pop()
        elif line.startswith('$ cd'):
            current_path.append(line.split(' ')[2])
        elif line == '$ ls':
            continue
        elif line.startswith('dir'):
            folder = line.split(' ')[1]
            copy_cp = copy.deepcopy(current_path)
            assert((folder, copy_cp, 0, True) not in system)
            system.append((folder, copy_cp, 0, True))
        else:
            size, name = line.split(' ')
            copy_cp = copy.deepcopy(current_path)
            assert((name, copy_cp, int(size), False) not in system)
            system.append((name, copy_cp, int(size), False))
    system = sorted(system, key=lambda x: len(x[1]), reverse=True)  # sort by path length decreasing
    return system


if __name__ == '__main__':
    file = line_str(7)
    directories = create_directories(file)
    print('part1:', part1(directories))
    print('part2:', part2(directories))
