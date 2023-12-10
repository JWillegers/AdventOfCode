def solution(lines):
    # parse
    maze = []  # [pipe, visited]
    queue = []  # [x, y, length]

    for i in range(len(lines)):
        row = []
        for j in range(len(lines[i])):
            if lines[i][j] == 'S':
                north = i > 0 and has_south_connection(lines[i - 1][j])
                east = j < len(lines[i]) and has_west_connection(lines[i][j + 1])
                south = i < len(lines) and has_north_connection(lines[i + 1][j])
                if north:
                    if south:
                        row.append(['|', True])
                    elif east:
                        row.append(['L', True])
                    else:  # west
                        row.append(['J', True])
                elif east:
                    if south:
                        row.append(['F', True])
                    else:  # west
                        row.append(['-', True])
                else:  # south and west
                    row.append(['7', True])

                queue.append([i, j, 0])
            else:
                row.append([lines[i][j], False])
        maze.append(row)

    # filter junk pipes (needed for part 2)
    changes_made = True
    while changes_made:
        changes_made = False
        for i in range(len(maze)):
            for j in range(len(maze[i])):
                c = maze[i][j][0]
                if c == '.':
                    continue

                count = 0
                count += 1 if has_north_connection(c) and i > 0 and has_south_connection(maze[i - 1][j][0]) else 0
                count += 1 if has_east_connection(c) and j < len(maze[i]) - 1 and has_west_connection(
                    maze[i][j + 1][0]) else 0
                count += 1 if has_south_connection(c) and i < len(maze) - 1 and has_north_connection(
                    maze[i + 1][j][0]) else 0
                count += 1 if has_west_connection(c) and j > 0 and has_east_connection(maze[i][j - 1][0]) else 0

                if count < 2:
                    maze[i][j][0] = '.'
                    changes_made = True

    # part 1
    longest_path_seen = queue[0]
    while len(queue) > 0:
        current = queue.pop(0)
        row = current[0]
        col = current[1]
        accepted = False

        # north
        if row > 0 and has_north_connection(maze[row][col][0]) and \
                has_south_connection(maze[row - 1][col][0]) and not maze[row - 1][col][1]:
            queue.append([row - 1, col, current[2] + 1])
            maze[row - 1][col][1] = True
            accepted = True

        # east
        if col < len(maze[row]) - 1 and has_east_connection(maze[row][col][0]) and \
                has_west_connection(maze[row][col + 1][0]) and not maze[row][col + 1][1]:
            queue.append([row, col + 1, current[2] + 1])
            maze[row][col + 1][1] = True
            accepted = True

        # south
        if row < len(maze) - 1 and has_south_connection(maze[row][col][0]) and \
                has_north_connection(maze[row + 1][col][0]) and not maze[row + 1][col][1]:
            queue.append([row + 1, col, current[2] + 1])
            maze[row + 1][col][1] = True
            accepted = True

        # west
        if col > 0 and has_west_connection(maze[row][col][0]) and \
                has_east_connection(maze[row][col - 1][0]) and not maze[row][col - 1][1]:
            queue.append([row, col - 1, current[2] + 1])
            maze[row][col - 1][1] = True
            accepted = True

        if accepted and queue[-1][2] > longest_path_seen[2]:
            longest_path_seen = queue[-1]

    print("Part1:", longest_path_seen[2])

    # part 2
    '''
    I decided to first "unsqueeze" everything by either adding an extra pipe between existing pipes or
    adding 'x' as filler
    '''
    # parse
    expanded_maze = []  # [shape, visited]
    for i in range(len(maze)):
        row = []
        expension_row = []
        for j in range(len(maze[i])):
            c = maze[i][j][0]
            if j > 0:
                if has_west_connection(c):
                    row.append(['-', True])
                else:
                    row.append(['x', False])
            if i > 0:
                if has_north_connection(c):
                    expension_row.append(['|', True])
                else:
                    expension_row.append(['x', False])
                if j < len(maze[i]) - 1:
                    expension_row.append(['x', False])
            if c == '.':
                row.append([c, False])
            else:
                row.append([c, True])
        if i > 0:
            expanded_maze.append(expension_row)
        expanded_maze.append(row)

    # run
    i_counter = 0
    for row in range(len(expanded_maze)):
        for col in range(len(expanded_maze[row])):
            if not expanded_maze[row][col][0] == '.':
                continue

            queue = [[row, col]]
            expanded_maze[row][col][1] = True
            outside_found = False
            found_tiles = set()
            found_tiles.add((row, col))
            while len(queue) > 0:
                current = queue.pop(0)

                r = current[0]
                c = current[1]
                if r == 0 or r == len(expanded_maze) - 1 or c == 0 or c == len(expanded_maze[r]) - 1:
                    outside_found = True

                # north
                if r > 0:
                    n = expanded_maze[r - 1][c]
                    # check if already visited
                    if not n[1]:
                        expanded_maze[r - 1][c][1] = True
                        queue.append([r - 1, c])
                        if n[0] == '.':
                            found_tiles.add((r - 1, c))

                # east
                if c < len(expanded_maze[r]) - 1:
                    n = expanded_maze[r][c + 1]
                    # check if already visited
                    if not n[1]:
                        expanded_maze[r][c + 1][1] = True
                        queue.append([r, c + 1])
                        if n[0] == '.':
                            found_tiles.add((r, c + 1))
                # south
                if r < len(expanded_maze) - 1:
                    n = expanded_maze[r + 1][c]
                    # check if already visited
                    if not n[1]:
                        expanded_maze[r + 1][c][1] = True
                        queue.append([r + 1, c])
                        if n[0] == '.':
                            found_tiles.add((r + 1, c))

                # west
                if c > 0:
                    n = expanded_maze[r][c - 1]
                    # check if already visited
                    if not n[1]:
                        expanded_maze[r][c - 1][1] = True
                        queue.append([r, c - 1])
                        if n[0] == '.':
                            found_tiles.add((r, c - 1))

            if not outside_found:
                i_counter += len(found_tiles)

            for tile in found_tiles:
                expanded_maze[tile[0]][tile[1]][0] = 'O' if outside_found else 'I'

    print("Part2:", i_counter)


def has_north_connection(char):
    return char == '|' or char == 'L' or char == 'J'


def has_east_connection(char):
    return char == '-' or char == 'L' or char == 'F'


def has_south_connection(char):
    return char == '|' or char == 'F' or char == '7'


def has_west_connection(char):
    return char == '-' or char == 'J' or char == '7'


if __name__ == '__main__':
    with open('inputs/day10.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)
