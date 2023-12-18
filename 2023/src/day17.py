from copy import deepcopy
import networkx as nx



def solution(input17):
    directions = {-10: 'U', 10: 'D', -1: 'L', 1: 'R'}
    G = nx.Graph()
    G.add_node((0, 0, 'O', 0), score = 0)
    queue = [(0, 0, 'O', 0)]
    while len(queue) > 0:
        current = queue[0]
        for i in range(1, len(queue)):
            if G.nodes[queue[i]]['score'] < G.nodes[current]['score']:
                current = queue[i]

        if current[0] == len(input17) - 1 and current[1] == len(input17[0]) - 1:
            print(G.nodes[current]['score'])
            return
        
        queue.remove(current)
        for offset_row in [-1, 0, 1]:
            for offset_col in [-1, 0, 1]:
                if abs(offset_row) + abs(offset_col) != 1:
                    continue

                if current[0] == 0 and offset_row == -1:
                    continue

                if current[0] == len(input17) - 1 and offset_row == 1:
                    continue

                if current[1] == 0 and offset_col == -1:
                    continue

                if current[1] == len(input17[0]) - 1 and offset_col == 1:
                    continue

                new_direction = directions[10 * offset_row + offset_col]
                opposite_direction = directions[-1 * (10 * offset_row + offset_col)]

                # dont go back
                if opposite_direction == current[2]:
                    continue

                next_count = current[3] + 1 if new_direction == current[2] else 1
                if next_count > 3:
                    continue
                next_score = G.nodes[current]['score'] + input17[current[0] + offset_row][current[1] + offset_col]

                next = (current[0] + offset_row, current[1] + offset_col, new_direction, next_count)
                if next in G.nodes():
                    if next_score < G.nodes[next]['score']:
                        for edge in G.edges(next):
                            G.remove_edge(*edge)
                        G.add_edge(current, next)
                        G.nodes[next]['score'] = next_score
                else:
                    G.add_node(next)
                    G.add_edge(current, next)
                    G.nodes[next]['score'] = next_score
                    queue.append(next)


if __name__ == '__main__':
    new_grid = []
    with open('inputs/day17.txt', 'r') as file:
        lines = file.read().split('\n')
        for l in lines:
            new_line = []
            for c in l:
                new_line.append(int(c))
            new_grid.append(new_line)
    solution(new_grid)