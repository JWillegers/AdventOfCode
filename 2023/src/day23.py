import networkx as nx


def solution(maze, part1):
    if part1:
        G = nx.DiGraph()
    else:
        G = nx.Graph()
    start = (0, 1)
    G.add_node(start, pos=start, index = 0)
    end = (len(maze) - 1, len(maze[0]) - 2)
    G.add_node(end, pos=end, index=1)
    index = 2

    # first find all intersections
    for row in range(1, len(maze) - 1):
        for col in range(1, len(maze[0]) - 1):
            if maze[row][col] != '.':
                continue

            # how many neighbours are not walls?
            count = 0
            if maze[row + 1][col] != '#':
                count += 1
            if maze[row - 1][col] != '#':
                count += 1
            if maze[row][col + 1] != '#':
                count += 1
            if maze[row][col - 1] != '#':
                count += 1

            if count > 2:
                G.add_node((row, col), pos=(row, col), index=index)
                index += 1

    # 10 * offset_row + offset_col
    slope = {-10: '^', 10: 'v', -1: '<', 1: '>'}

    # for each pair of nodes, find the shortest path without going to any other nodes
    for n1 in G.nodes:
        for n2 in G.nodes:
            if n1 == n2 or (not part1 and G.nodes[n1]['index'] < G.nodes[n2]['index']):
                continue

            visited = [n1]
            queue = [[n1, 0]]
            while len(queue) > 0:
                current = queue.pop(0)
                row = current[0][0]
                col = current[0][1]

                for offset_row in [-1, 0, 1]:
                    for offset_col in [-1, 0, 1]:
                        # check for valid direction
                        if abs(offset_row) + abs(offset_col) != 1:
                            continue

                        # check if within bounds
                        if not (0 <= row + offset_row <= len(maze) - 1 and 0 <= col + offset_col <= len(maze[0]) - 1):
                            continue

                        # if next is not wall
                        if maze[row + offset_row][col + offset_col] == '#':
                            continue

                        # check if it next is not another intersection
                        if (row + offset_row, col + offset_col) in G.nodes():
                            if row + offset_row == n2[0] and col + offset_col == n2[1]:
                                G.add_edge(n1, n2, weight=current[1])
                            continue

                        # check if not already visited
                        if (row + offset_row, col + offset_col) in visited:
                            continue

                        # check for slopes
                        if part1 and maze[row][col] != '.':
                            num = 10 * offset_row + offset_col
                            if maze[row][col] != slope[num]:
                                continue
                        if part1 and maze[row + offset_row][col + offset_col] != '.':
                            num = 10 * offset_row + offset_col
                            if maze[row + offset_row][col + offset_col] == slope[-1 * num]:
                                continue

                        visited.append((row + offset_row, col + offset_col))
                        queue.append([(row + offset_row, col + offset_col), current[1] + 1])

    # find all paths between start and end and determine the longest one
    m = 0
    for p in nx.all_simple_paths(G, start, end):
        m = max(m, nx.path_weight(G, p, "weight") + len(p) - 1)
    return m


if __name__ == '__main__':
    new_grid = []
    with open('inputs/day23.txt', 'r') as file:
        lines = file.read().split('\n')
        for l in lines:
            new_line = []
            for c in l:
                new_line.append(c)
            new_grid.append(new_line)
    print("Part 1:", solution(new_grid, True))
    print("Part 2:", solution(new_grid, False))
