from readFile import *


def solution(elves):
    time = 0
    solution_part_1 = 0
    changes_made = True
    while changes_made:
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
        elf_to_cord, n_elf_to_cord = first_half(elves)
        elves, changes_made = second_half(elves, elf_to_cord, n_elf_to_cord)
        for key, item in elves.items():
            item['lod'].append(item['lod'].pop(0))
        time += 1

    return solution_part_1, time


def first_half(elves):
    elf_to_cord = dict()
    n_elf_to_cord = dict()
    for current_key, current_item in elves.items():
        # find which direction is clear
        no_elf_north = True
        no_elf_south = True
        no_elf_west = True
        no_elf_east = True
        for key_2, item_2 in elves.items():
            if current_key != key_2:
                if item_2['row'] == current_item['row'] - 1 and abs(item_2['col'] - current_item['col']) <= 1:
                    no_elf_north = False
                if item_2['row'] == current_item['row'] + 1 and abs(item_2['col'] - current_item['col']) <= 1:
                    no_elf_south = False
                if item_2['col'] == current_item['col'] - 1 and abs(item_2['row'] - current_item['row']) <= 1:
                    no_elf_west = False
                if item_2['col'] == current_item['col'] + 1 and abs(item_2['row'] - current_item['row']) <= 1:
                    no_elf_east = False
            if not(no_elf_north or no_elf_east or no_elf_west or no_elf_south):
                break

        # find preferred direction
        direction = None
        if not (no_elf_north and no_elf_east and no_elf_west and no_elf_south):
            for d in current_item['lod']:
                if d == 'N' and no_elf_north:
                    direction = 'N'
                    break
                elif d == 'S' and no_elf_south:
                    direction = 'S'
                    break
                elif d == 'W' and no_elf_west:
                    direction = 'W'
                    break
                elif d == 'E' and no_elf_east:
                    direction = 'E'
                    break

        # save direction for second half
        if direction is not None:
            if direction == 'N':
                new_cord = (current_item['row'] - 1, current_item['col'])
            elif direction == 'S':
                new_cord = (current_item['row'] + 1, current_item['col'])
            elif direction == 'W':
                new_cord = (current_item['row'], current_item['col'] - 1)
            elif direction == 'E':
                new_cord = (current_item['row'], current_item['col'] + 1)
            elf_to_cord.update({current_key: new_cord})
            if new_cord in n_elf_to_cord.keys():
                n_elf_to_cord[new_cord] += 1
            else:
                n_elf_to_cord.update({new_cord: 1})

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
                    'lod': ['N', 'S', 'W', 'E']
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
    print('part1:', p2)
