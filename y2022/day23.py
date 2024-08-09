from readFile import *
from copy import deepcopy


def solution(elves):
    time = 0
    solution_part_1 = 0
    changes_made = True
    preferred_directions = ['N', 'S', 'W', 'E']
    while changes_made:
        # find answer first part
        if time == 10:
            min_row = 9e9
            max_row = -9e9
            min_col = 9e9
            max_col = -9e9
            for key, items in elves.items():
                min_row = min(min_row, items['row'])
                max_row = max(max_row, items['row'])
                min_col = min(min_col, items['col'])
                max_col = max(max_col, items['col'])
            solution_part_1 = (max_row - min_row + 1) * (max_col - min_col + 1) - len(elves)
        # loop through steps
        elf_to_cord, n_elf_to_cord = first_half(elves, preferred_directions)
        elves, changes_made = second_half(elves, elf_to_cord, n_elf_to_cord)
        # sort by row and reset neighbors in direction
        list_of_items = list(elves.items())
        list_of_items = sorted(list_of_items, key=lambda x: x[1]['row'])
        for i in range(len(list_of_items)):
            elves[i] = deepcopy(list_of_items[i][1])
            elves[i]['neighbors in direction'] = {
                        'N': False,
                        'S': False,
                        'W': False,
                        'E': False
                    }
        preferred_directions.append(preferred_directions.pop(0))  # move first item to last
        time += 1

    return solution_part_1, time


def first_half(elves, preferred_directions):
    # preferred new location of elf
    elf_to_cord = dict()
    # how many elfs to want to move to cord c
    n_elf_to_cord = dict()
    for i in range(len(elves.keys())):
        current_elf = elves[i]
        current_elf_row = current_elf['row']
        current_elf_col = current_elf['col']
        for j in range(i + 1, len(elves.keys())):
            other_elf = elves[j]
            # since elfs are sorted by row, if we get to an elf who is at least 2 rows lower, we can break
            if current_elf_row + 2 <= other_elf['row']:
                break
            # other elf north of current elf
            if other_elf['row'] == current_elf_row - 1 and abs(other_elf['col'] - current_elf_col) <= 1:
                current_elf['neighbors in direction']['N'] = True
                other_elf['neighbors in direction']['S'] = True
            # other elf south of current elf
            if other_elf['row'] == current_elf_row + 1 and abs(other_elf['col'] - current_elf_col) <= 1:
                current_elf['neighbors in direction']['S'] = True
                other_elf['neighbors in direction']['N'] = True
            # other elf east of current elf
            if other_elf['col'] == current_elf_col + 1 and abs(other_elf['row'] - current_elf_row) <= 1:
                current_elf['neighbors in direction']['E'] = True
                other_elf['neighbors in direction']['W'] = True
            # other elf west of current elf
            if other_elf['col'] == current_elf_col - 1 and abs(other_elf['row'] - current_elf_row) <= 1:
                current_elf['neighbors in direction']['W'] = True
                other_elf['neighbors in direction']['E'] = True
            # if there is an elf in all 4 directions, we can break

        # find preferred direction
        counter = 0
        for d in current_elf['neighbors in direction']:
            if current_elf['neighbors in direction'][d]:
                counter += 1
        if 0 < counter < 4:
            for direction in preferred_directions:
                new_cord = None
                if direction == 'N' and not current_elf['neighbors in direction']['N']:
                    new_cord = (current_elf_row - 1, current_elf_col)
                elif direction == 'S' and not current_elf['neighbors in direction']['S']:
                    new_cord = (current_elf_row + 1, current_elf_col)
                elif direction == 'E' and not current_elf['neighbors in direction']['E']:
                    new_cord = (current_elf_row, current_elf_col + 1)
                elif direction == 'W' and not current_elf['neighbors in direction']['W']:
                    new_cord = (current_elf_row, current_elf_col - 1)

                if new_cord is not None:
                    # save findings for second half
                    elf_to_cord.update({i: new_cord})
                    if new_cord in n_elf_to_cord.keys():
                        n_elf_to_cord[new_cord] += 1
                    else:
                        n_elf_to_cord.update({new_cord: 1})
                    break
    return elf_to_cord, n_elf_to_cord


def second_half(elves, elf_to_cord, n_elf_to_cord):
    one_elf_moved = False
    for elf_id, cord in elf_to_cord.items():
        if n_elf_to_cord[cord] == 1:
            one_elf_moved = True
            elves[elf_id]['row'] = cord[0]
            elves[elf_id]['col'] = cord[1]
    return elves, one_elf_moved


def parse(f):
    elves = dict()
    elves_index = 0
    for row_index in range(len(f)):
        row = f[row_index]
        for col_index in range(len(row)):
            if row[col_index] == '#':
                elves.update({elves_index: {
                    'row': row_index,
                    'col': col_index,
                    'neighbors in direction': {
                        'N': False,
                        'S': False,
                        'W': False,
                        'E': False
                    }
                }})
                elves_index += 1
    return elves


if __name__ == '__main__':
    test_file = grid(23, test=True)
    parsed_test_file = parse(test_file)
    test_p1, test_p2 = solution(parsed_test_file)
    assert(test_p1 == 110)
    assert(test_p2 == 20)
    file = grid(23)
    parsed_file = parse(file)
    p1, p2 = solution(parsed_file)
    print('part1:', p1)
    print('part2:', p2)
