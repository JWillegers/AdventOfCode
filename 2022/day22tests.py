from readFile import *
from day22 import part2_wrap


# test if wrapping is done correctly for day 22 part 2
def tests(my_map):
    # my_map, row, col, d_row, d_col, direction

    # face 1 to face 6
    location, direction, row, col = part2_wrap(my_map, 0, 97, -1, 0, 'U')
    assert (location == [197, 0])
    assert (direction == 'R')
    assert (row == 0)
    assert (col == 1)

    location, direction, row, col = part2_wrap(my_map, 0, 89, -1, 0, 'U')
    assert (location == [0, 89])
    assert (direction == 'U')
    assert (row == -1)
    assert (col == 0)

    # face 1 to face 5
    location, direction, row, col = part2_wrap(my_map, 44, 50, 0, -1, 'L')
    assert (location == [105, 0])
    assert (direction == 'R')
    assert (row == 0)
    assert (col == 1)

    location, direction, row, col = part2_wrap(my_map, 48, 50, 0, -1, 'L')
    assert (location == [48, 50])
    assert (direction == 'L')
    assert (row == 0)
    assert (col == -1)

    # face 3 to face 5
    location, direction, row, col = part2_wrap(my_map, 52, 50, 0, -1, 'L')
    assert (location == [100, 2])
    assert (direction == 'D')
    assert (row == 1)
    assert (col == 0)

    location, direction, row, col = part2_wrap(my_map, 92, 50, 0, -1, 'L')
    assert (location == [92, 50])
    assert (direction == 'L')
    assert (row == 0)
    assert (col == -1)

    # face 5 to face 3
    location, direction, row, col = part2_wrap(my_map, 100, 45, -1, 0, 'U')
    assert (location == [95, 50])
    assert (direction == 'R')
    assert (row == 0)
    assert (col == 1)

    location, direction, row, col = part2_wrap(my_map, 100, 16, -1, 0, 'U')
    assert (location == [100, 16])
    assert (direction == 'U')
    assert (row == -1)
    assert (col == 0)

    # face 5 to face 1
    location, direction, row, col = part2_wrap(my_map, 101, 0, 0, -1, 'L')
    assert (location == [48, 50])
    assert (direction == 'R')
    assert (row == 0)
    assert (col == 1)

    location, direction, row, col = part2_wrap(my_map, 104, 0, 0, -1, 'L')
    assert (location == [104, 0])
    assert (direction == 'L')
    assert (row == 0)
    assert (col == -1)

    # face 6 to face 1
    location, direction, row, col = part2_wrap(my_map, 166, 0, 0, -1, 'L')
    assert (location == [0, 66])
    assert (direction == 'D')
    assert (row == 1)
    assert (col == 0)

    location, direction, row, col = part2_wrap(my_map, 197, 0, 0, -1, 'L')
    assert (location == [197, 0])
    assert (direction == 'L')
    assert (row == 0)
    assert (col == -1)

    # face 6 to face 2
    location, direction, row, col = part2_wrap(my_map, 199, 23, 1, 0, 'D')
    assert (location == [0, 123])
    assert (direction == 'D')
    assert (row == 1)
    assert (col == 0)

    location, direction, row, col = part2_wrap(my_map, 199, 47, 1, 0, 'D')
    assert (location == [199, 47])
    assert (direction == 'D')
    assert (row == 1)
    assert (col == 0)

    # face 6 to face 4
    location, direction, row, col = part2_wrap(my_map, 167, 49, 0, 1, 'R')
    assert (location == [149, 67])
    assert (direction == 'U')
    assert (row == -1)
    assert (col == 0)

    location, direction, row, col = part2_wrap(my_map, 155, 49, 0, 1, 'R')
    assert (location == [155, 49])
    assert (direction == 'R')
    assert (row == 0)
    assert (col == 1)

    # face 4 to face 6
    location, direction, row, col = part2_wrap(my_map, 149, 73, 1, 0, 'D')
    assert (location == [173, 49])
    assert (direction == 'L')
    assert (row == 0)
    assert (col == -1)

    location, direction, row, col = part2_wrap(my_map, 149, 93, 1, 0, 'D')
    assert (location == [149, 93])
    assert (direction == 'D')
    assert (row == 1)
    assert (col == 0)

    # face 4 to face 2
    location, direction, row, col = part2_wrap(my_map, 107, 99, 0, 1, 'R')
    assert (location == [42, 149])
    assert (direction == 'L')
    assert (row == 0)
    assert (col == -1)

    location, direction, row, col = part2_wrap(my_map, 105, 99, 0, 1, 'R')
    assert (location == [105, 99])
    assert (direction == 'R')
    assert (row == 0)
    assert (col == 1)

    # face 3 to face 2
    location, direction, row, col = part2_wrap(my_map, 69, 99, 0, 1, 'R')
    assert (location == [49, 119])
    assert (direction == 'U')
    assert (row == -1)
    assert (col == 0)

    location, direction, row, col = part2_wrap(my_map, 59, 99, 0, 1, 'R')
    assert (location == [59, 99])
    assert (direction == 'R')
    assert (row == 0)
    assert (col == 1)

    # face 2 to face 3
    location, direction, row, col = part2_wrap(my_map, 49, 129, 1, 0, 'D')
    assert (location == [79, 99])
    assert (direction == 'L')
    assert (row == 0)
    assert (col == -1)

    location, direction, row, col = part2_wrap(my_map, 49, 103, 1, 0, 'D')
    assert (location == [49, 103])
    assert (direction == 'D')
    assert (row == 1)
    assert (col == 0)

    # face 2 to face 4
    location, direction, row, col = part2_wrap(my_map, 40, 149, 0, 1, 'R')
    assert (location == [109, 99])
    assert (direction == 'L')
    assert (row == 0)
    assert (col == -1)

    location, direction, row, col = part2_wrap(my_map, 2, 149, 0, 1, 'R')
    assert (location == [2, 149])
    assert (direction == 'R')
    assert (row == 0)
    assert (col == 1)

    # face 2 to face 6
    location, direction, row, col = part2_wrap(my_map, 0, 147, -1, 0, 'U')
    assert (location == [199, 47])
    assert (direction == 'U')
    assert (row == -1)
    assert (col == 0)

    location, direction, row, col = part2_wrap(my_map, 0, 148, -1, 0, 'U')
    assert (location == [0, 148])
    assert (direction == 'U')
    assert (row == -1)
    assert (col == 0)


if __name__ == '__main__':
    file = grid(22)
    tests(file)
