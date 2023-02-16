from readFile import *
from copy import deepcopy


def part1(cubes_to_check):
    surface_area = 0
    visited = []
    while len(cubes_to_check) > 0:
        cube = cubes_to_check.pop()
        visited.append(cube)
        cubes_to_check, neighbors_to_check, open_sides = check_neighbors(cube, visited, cubes_to_check, [])
        surface_area += open_sides
        while len(neighbors_to_check) > 0:
            cube = neighbors_to_check.pop()
            visited.append(cube)
            cubes_to_check, ntc, open_sides = check_neighbors(cube, visited, cubes_to_check, neighbors_to_check)
            surface_area += open_sides
            neighbors_to_check = neighbors_to_check.union(ntc)

    return surface_area


def check_neighbors(cube, visited, cubes_to_check, ntc):
    new_neighbors_to_check = set()
    new_open_sides = 0
    for i in range(3):
        for d in [-1, 1]:
            nx = cube[0] + d if i == 0 else cube[0]
            ny = cube[1] + d if i == 1 else cube[1]
            nz = cube[2] + d if i == 2 else cube[2]
            nc = (nx, ny, nz)
            if nc in cubes_to_check:
                new_neighbors_to_check.add(nc)
                cubes_to_check.remove(nc)
            elif nc not in visited and nc not in ntc:
                new_open_sides += 1
    return cubes_to_check, new_neighbors_to_check, new_open_sides


def part2(all_droplets):
    minx = 1e6
    maxx = -1e6
    miny = 1e6
    maxy = -1e6
    minz = 1e6
    maxz = -1e6
    for drop in all_droplets:
        minx = min(minx, drop[0])
        maxx = max(maxx, drop[0])
        miny = min(miny, drop[1])
        maxy = max(maxy, drop[1])
        minz = min(minz, drop[2])
        maxz = max(maxz, drop[2])

    surface_area = 0

    for i in range(len(all_droplets)):
        cube = all_droplets[i]
        surface_area += try_edge_walk(cube, all_droplets, maxx, minx, maxy, miny, maxz, minz)
    return surface_area


def try_edge_walk(cube, all_droplets, maxx, minx, maxy, miny, maxz, minz):
    edges = []
    for i in range(3):
        for d in [-1, 1]:
            nx = cube[0] + d if i == 0 else cube[0]
            ny = cube[1] + d if i == 1 else cube[1]
            nz = cube[2] + d if i == 2 else cube[2]
            nc = (nx, ny, nz)
            if nc not in all_droplets:
                edges.append(nc)

    open_edges = 0
    for start_field in edges:
        visited = [start_field]
        queue = [start_field]
        run = True
        while len(queue) > 0 and run:
            field = queue.pop(0)
            for i in range(3):
                if not run:
                    break
                for d in [-1, 1]:
                    nx = field[0] + d if i == 0 else field[0]
                    ny = field[1] + d if i == 1 else field[1]
                    nz = field[2] + d if i == 2 else field[2]
                    nc = (nx, ny, nz)
                    if nx < minx or nx > maxx or ny < miny or ny > maxy or nz < minz or nz > maxz:
                        open_edges += 1
                        run = False
                        break
                    if nc not in all_droplets and nc not in visited:
                        visited.append(nc)
                        queue.append(nc)

    return open_edges



def parse(f):
    cubes = set()
    for line in f:
        s = line.split(',')
        cubes.add((int(s[0]), int(s[1]), int(s[2])))
    return list(cubes)


# Part 2 can take a while to run
if __name__ == '__main__':
    test_file = line_str(18, True)
    test_cubes = parse(test_file)
    assert(part1(deepcopy(test_cubes)) == 64)
    assert(part2(deepcopy(test_cubes)) == 58)
    file = line_str(18)
    global_cubes = parse(file)
    print('part1:', part1(deepcopy(global_cubes)))
    print('part2:', part2(deepcopy(global_cubes)))
