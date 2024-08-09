from copy import deepcopy
import time

def solutionP1(input14):
    for col in range(len(input14[0])):
        changes_made = True
        first_empty_space = find_next_empty_space(input14, 0, col)
        index = first_empty_space + 1
        while index < len(input14):
            if input14[index][col] == 'O':
                input14[index][col] = '.'
                input14[first_empty_space][col] = 'O'
                first_empty_space += 1
            if input14[index][col] == '#':
                first_empty_space = find_next_empty_space(input14, index, col)
                if first_empty_space == -1:
                    break
                index = first_empty_space
            index += 1
    
    p1 = 0
    for row in range(len(input14)):
        p1 += input14[row].count('O') * (len(input14) - row)

    print("Part 1:", p1)


def find_next_empty_space(input14, starting_index, col):
    for index in range(starting_index, len(input14)):
        if input14[index][col] == '.':
            return index
    return -1


def solutionP2(input14):
    limit = 75
    north = {}
    east = {}
    south = {}
    west = {}
    round_rocks = []
    round_rocks_history = []
    changes_made_history = []
    record_mode = False
    record_mode_start = -1

    for row in range(len(input14)):
        for col in range(len(input14[0])):
            if input14[row][col] == 'O':
                round_rocks.append((row, col))

            if input14[row][col] == '#':
                continue

            #  north
            if row == 0:
                north[(row, col)] = (row, col)
            else:
                r = row - 1
                while r >= 0 and input14[r][col] != '#':
                    r -= 1
                north[(row, col)] = (r + 1, col)
            
            #  east
            if col == len(input14[0]) - 1:
                east[(row, col)] = (row, col)
            else:
                c = col + 1
                while c <= len(input14[0]) - 1 and input14[row][c] != '#':
                    c += 1
                east[(row, col)] = (row, c - 1)

            #  south
            if row == len(input14) - 1:
                south[(row, col)] = (row, col)
            else:
                r = row + 1
                while r <= len(input14[0]) - 1 and input14[r][col] != '#':
                    r += 1
                south[(row, col)] = (r - 1, col)

            #  west
            if col == 0:
                west[(row, col)] = (row, col)
            else:
                c = col - 1
                while c >= 0 and input14[row][c] != '#':
                    c -= 1
                west[(row, col)] = (row, c + 1)

    round_rocks = sorted(round_rocks, key=lambda x: (x[1], x[0]))
    for a in range(1, 1000):
        #  north
        after_north = []
        for rock in round_rocks:
            new_dest = north[rock]
            while new_dest in after_north:
                new_dest = (new_dest[0] + 1, new_dest[1])
            after_north.append(new_dest)

        #  west
        after_north = sorted(after_north, key=lambda x: (x[0], x[1]))
        after_west = []
        for rock in after_north:
            new_dest = west[rock]
            while new_dest in after_west:
                new_dest = (new_dest[0], new_dest[1] + 1)
            after_west.append(new_dest)

        #  south
        after_west = sorted(after_west, key=lambda x: (x[1], x[0]), reverse=True)
        after_south = []
        for rock in after_west:
            new_dest = south[rock]
            while new_dest in after_south:
                new_dest = (new_dest[0] - 1, new_dest[1])
            after_south.append(new_dest)

        #  east
        after_south = sorted(after_south, key=lambda x: (x[0], x[1]), reverse=True)
        after_east = []
        for rock in after_south:
            new_dest = east[rock]
            while new_dest in after_east:
                new_dest = (new_dest[0], new_dest[1] - 1)
            after_east.append(new_dest)

        after_east = sorted(after_east, key=lambda x: (x[1], x[0]))

        if not record_mode:
            changes_made = 0
            for ae in after_east:
                if ae not in round_rocks:
                    changes_made += 1
            changes_made_history.append(changes_made)
            if len(changes_made_history) > limit:
                if changes_made_history[-1] > changes_made_history[-2]:
                    print(changes_made_history)
                    time.sleep(1)
                if len(set(changes_made_history)) == 1:
                    record_mode = True
                    record_mode_start = a
                    changes_made_history.clear()
                else:
                    changes_made_history.pop(0)
        if record_mode:
            changes_made = []
            for ae in after_east:
                if ae not in round_rocks:
                    changes_made.append(ae)
            changes_made_history.append(changes_made)
            round_rocks_history.append(deepcopy(after_east))
            if len(changes_made_history) > limit:
                print("ERROR CYCLE TOO BIG")
                exit(1)
            if len(changes_made_history) > 1 and changes_made_history[-1] == changes_made_history[0]:
                round_rocks = round_rocks_history[1000000000 % (len(changes_made_history) - 1) - record_mode_start % (len(changes_made_history) - 1)]
                break

        round_rocks = deepcopy(after_east)

    p2 = 0
    for rock in round_rocks:
        p2 += len(input14) - rock[0]

    print("Part 2:", p2)
    # 90182 too high         


if __name__ == '__main__':
    new_grid = []
    with open('inputs/day14.txt', 'r') as file:
        lines = file.read().split('\n')
        for l in lines:
            new_line = []
            for c in l:
                new_line.append(c)
            new_grid.append(new_line)
    solutionP1(deepcopy(new_grid))
    solutionP2(deepcopy(new_grid))
