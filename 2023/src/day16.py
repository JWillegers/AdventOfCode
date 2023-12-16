from copy import deepcopy


def solution(input16):
    energized = set()
    energized.add((0, 0))
    checked = []
    queue = []
    match input16[0][0]:
        case '.':
            queue.append([0, 0, 'R'])
        case '-':
            queue.append([0, 0, 'R'])
        case '|':
            queue.append([0, 0, 'U'])
            queue.append([0, 0, 'D'])
        case '/':
            queue.append([0, 0, 'U'])
        case '\\':
            queue.append([0, 0, 'D'])
    while len(queue) > 0:
        current = queue.pop(0)
        if current in checked:
            continue
        checked.append(deepcopy(current))
        direction = current.pop(2)
        energized.add(tuple(current))
        if direction == 'R' and current[1] < len(input16[current[0]]) - 1:
            current[1] += 1
        elif direction == 'D' and current[0] < len(input16) - 1:
            current[0] += 1
        elif direction == 'L' and current[1] > 0:
            current[1] -= 1
        elif direction == 'U' and current[0] > 0:
            current[0] -= 1
        else:
            # trying to leave the input
            continue

        char = input16[current[0]][current[1]]
        match char:
            case '.':
                current.append(direction)
                queue.insert(0, current)
            case '|':
                if direction == 'U' or direction == 'D':
                    current.append(direction)
                    queue.insert(0, current)
                else:
                    up = deepcopy(current)
                    up.append('U')
                    queue.insert(0, up)
                    down = deepcopy(current)
                    down.append('D')
                    queue.insert(0, down)
            case '-':
                if direction == 'L' or direction == 'R':
                    current.append(direction)
                    queue.insert(0, current)
                else:
                    left = deepcopy(current)
                    left.append('L')
                    queue.insert(0, left)
                    right = deepcopy(current)
                    right.append('R')
                    queue.insert(0, right)
            case '/':
                if direction == 'L':
                    current.append('D')
                elif direction == 'R':
                    current.append('U')
                elif direction == 'D':
                    current.append('L')
                elif direction == 'U':
                    current.append('R')
                queue.insert(0, current)
            case '\\':
                if direction == 'L':
                    current.append('U')
                elif direction == 'R':
                    current.append('D')
                elif direction == 'D':
                    current.append('R')
                elif direction == 'U':
                    current.append('L')
                queue.insert(0, current)
    print("Part 1:", len(energized))


if __name__ == '__main__':
    new_grid = []
    with open('inputs/day16.txt', 'r') as file:
        lines = file.read().split('\n')
        for l in lines:
            new_line = []
            for c in l:
                new_line.append(c)
            new_grid.append(new_line)
    solution(new_grid)
