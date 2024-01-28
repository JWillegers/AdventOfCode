from copy import deepcopy


def solution(input21, srow, scol):
    n_even = 0
    n_odd = 0
    last = [(srow, scol)]
    step = 0
    while step <= 26501365:
        step += 1
        new_last = set()
        while len(last) > 0:
            current = last.pop(0)

            for offset_row in [-1, 0, 1]:
                for offset_col in [-1, 0, 1]:
                    if abs(offset_row) + abs(offset_col) != 1:
                        continue

                    new_row = current[0] + offset_row
                    new_col = current[1] + offset_col

                    if new_row == -1:
                        new_row = len(input21) - 1
                    elif new_col == -1:
                        new_col = len(input21[0]) - 1
                    elif new_row == len(input21):
                        new_row = 0
                    elif new_col == len(input21[0]):
                        new_col = 0

                    if input21[new_row][new_col][0] == '#':
                        continue

                    if not input21[new_row][new_col][1 + step % 2]:
                        input21[new_row][new_col][1 + step % 2] = True
                        new_last.add((new_row, new_col))
                        if step % 2 == 0:
                            n_even += 1
                        else:
                            n_odd += 1
        last = deepcopy(list(new_last))

        if step == 64:
            print("part1:", n_even)

    print("part2:", n_odd)


if __name__ == '__main__':
    new_grid = []
    S_row = 0
    S_col = 0
    with open('inputs/day21.txt', 'r') as file:
        lines = file.read().split('\n')
        row = 0
        for l in lines:
            new_line = []
            col = 0
            for c in l:
                new_line.append([c, False, False])
                if c == 'S':
                    S_row = row
                    S_col = col
                col += 1
            new_grid.append(new_line)
            row += 1
    solution(new_grid, S_row, S_col)
