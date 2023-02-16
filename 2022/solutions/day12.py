from readFile import *


def move(hill, queue, visited, x, y, path_length, dx, dy, max_x, max_y):
    if 0 <= x + dx <= max_x and 0 <= y + dy <= max_y and \
        hill[x + dx][y + dy] - hill[x][y] <= 1 and \
            (x + dx, y + dy) not in visited:
        visited.append((x + dx, y + dy))
        queue.append((x + dx, y + dy, path_length + 1))
    return visited, queue


def solution(hill, starting_points):
    route_lengths = []
    for start_x, start_y in starting_points:
        max_x = len(hill) - 1
        max_y = len(hill[0]) - 1
        visited = [(start_x, start_y)]
        queue = [(start_x, start_y, 0)]
        while len(queue) > 0:
            x, y, path_length = queue.pop(0)
            if hill[x][y] == 27:
                route_lengths.append(path_length)
                break

            visited, queue = move(hill, queue, visited, x, y, path_length, -1, 0, max_x, max_y)
            visited, queue = move(hill, queue, visited, x, y, path_length, 1, 0, max_x, max_y)
            visited, queue = move(hill, queue, visited, x, y, path_length, 0, -1, max_x, max_y)
            visited, queue = move(hill, queue, visited, x, y, path_length, 0, 1, max_x, max_y)
    return min(route_lengths)


def parse_file(f):
    part_1_starting_points = []
    part_2_starting_points = []
    for i in range(len(f)):
        for j in range(len(f[0])):
            if f[i][j] == 'S':
                part_1_starting_points.append((i, j))
                part_2_starting_points.append((i, j))
                f[i][j] = 0
            elif f[i][j] == 'E':
                f[i][j] = 27
            else:
                f[i][j] = ord(f[i][j]) - 96
                if f[i][j] == 1:
                    part_2_starting_points.append((i, j))
    # check if begin and end point in file
    assert(any(0 in x for x in f))
    assert(any(27 in x for x in f))
    return f, part_1_starting_points, part_2_starting_points


if __name__ == '__main__':
    test_file = grid(12, test=True)
    test_file, test_part1_sp, test_part2_sp = parse_file(test_file)
    assert(solution(test_file, test_part1_sp) == 31)
    assert(solution(test_file, test_part2_sp) == 29)
    file = grid(12)
    file, global_part1_sp, global_part2_sp = parse_file(file)
    print('part1:', solution(file, global_part1_sp))
    print('part2:', solution(file, global_part2_sp))
