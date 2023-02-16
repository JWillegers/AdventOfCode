from readFile import *


def part1(data):
    cave, lowest_point = create_rocks(data)
    sand_units = 0
    abyss_filled = False
    while not abyss_filled:
        # create new unit of sand
        sand = (500, 0)
        sand_comes_to_rest = False
        # sand falling
        while not abyss_filled and not sand_comes_to_rest:
            # possible fall options
            sand_down = (sand[0], sand[1] + 1)
            sand_down_left = (sand[0] - 1, sand[1] + 1)
            sand_down_right = (sand[0] + 1, sand[1] + 1)
            if sand_down not in cave:
                sand = sand_down
            elif sand_down_left not in cave:
                sand = sand_down_left
            elif sand_down_right not in cave:
                sand = sand_down_right
            else:  # cannot fall anymore
                cave.add(sand)
                sand_comes_to_rest = True
                sand_units += 1
            if sand[1] > lowest_point:  # check if below the rocks
                abyss_filled = True

    return sand_units


def create_rocks(data):
    lowest_point = 0
    cave = set()
    # go over all lines
    for line in data:
        corners = line.split(' -> ')
        corners_parsed = []
        # parse each cord
        for corner in corners:
            cord = corner.split(',')
            corners_parsed.append((int(cord[0]), int(cord[1])))
            lowest_point = max(lowest_point, int(cord[1]))
        # loop over 2 consecutive corners
        for i in range(len(corners) - 1):
            corner1 = corners_parsed[i]
            corner2 = corners_parsed[i + 1]
            dx = 1 if corner1[0] <= corner2[0] else -1
            dy = 1 if corner1[1] <= corner2[1] else -1
            for x in range(corner1[0], corner2[0] + dx, dx):
                cave.add((x, corner1[1]))
            for y in range(corner1[1], corner2[1] + dy, dy):
                cave.add((corner1[0], y))
    return cave, lowest_point


def part2(data):
    cave, lowest_point = create_rocks(data)
    floor = lowest_point + 2
    sand_units = 0
    while True:
        # create new unit of sand
        sand = (500, 0)
        if sand in cave:
            return sand_units
        sand_comes_to_rest = False
        # sand falling
        while not sand_comes_to_rest:
            # possible fall options
            sand_down = (sand[0], sand[1] + 1)
            sand_down_left = (sand[0] - 1, sand[1] + 1)
            sand_down_right = (sand[0] + 1, sand[1] + 1)
            if sand[1] + 1 == floor:
                cave.add(sand)
                sand_comes_to_rest = True
                sand_units += 1
            elif sand_down not in cave:
                sand = sand_down
            elif sand_down_left not in cave:
                sand = sand_down_left
            elif sand_down_right not in cave:
                sand = sand_down_right
            else:  # cannot fall anymore
                cave.add(sand)
                sand_comes_to_rest = True
                sand_units += 1


if __name__ == '__main__':
    test_file = line_str(14, True)
    assert(part1(test_file) == 24)
    assert(part2(test_file) == 93)
    file = line_str(14)
    print('part1:', part1(file))
    print('part2:', part2(file))
