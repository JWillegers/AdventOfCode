from copy import deepcopy
import networkx as nx



def solution(input17):
    directions = {-10: 'U', 10: 'D', -1: 'L', 1: 'R'}
    G = nx.Graph()
    for row in range(len(input17)):
        for col in range(len(input17[0])):
            for direction in directions.values():
                for count in range(1, 4):
                    G.add_node((row, col, direction, count), score = 0)
    G.add_node((0, 0, 'O', 0), score = 0)
    queue = [(0, 0, 'O', 0)]
    while len(queue) > 0:
        current = queue[0]
        for i in range(1, len(queue)):
            if G.nodes[queue[i]] < G.nodes[current]:
                current = queue[i]
        
        queue.remove(current)
        for offset_row in [-1, 0, 1]:
            for offset_col in [-1, 0, 1]:
                
        


        

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