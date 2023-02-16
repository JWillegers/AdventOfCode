from readFile import *
import numpy as np


def part1(tree_map):
    tree_map = np.array(tree_map)
    rows = find_visible_tree(tree_map)
    column = find_visible_tree(tree_map.T).T
    return np.logical_or(rows, column).sum()


def find_visible_tree(tree_map):
    visible_tree_cords = np.zeros((len(tree_map), len(tree_map[0])))
    for i in range(len(tree_map)):
        row = tree_map[i]
        highest_left_to_right = row[0]
        highest_right_to_left = row[-1]
        visible_tree_cords[i][0] = 1
        visible_tree_cords[i][- 1] = 1
        for column in range(1, len(row)):
            if highest_right_to_left == 9 and highest_left_to_right == 9:
                break
            if row[column] > highest_left_to_right:
                visible_tree_cords[i][column] = 1
                highest_left_to_right = row[column]
            if row[- column - 1] > highest_right_to_left:
                visible_tree_cords[i][- column - 1] = 1
                highest_right_to_left = row[- column - 1]

    return visible_tree_cords


def part2(tree_map):
    tree_map = np.array(tree_map)
    highest_scenic_score = 0
    # loop over all trees that are not on the edge
    for row in range(1, len(tree_map) - 1):
        for column in range(1, len(tree_map[1]) - 1):
            tree = tree_map[row][column]
            # left
            left = 0
            for i in range(column - 1, -1, -1):
                left += 1
                if tree_map[row][i] >= tree:
                    break

            # right
            right = 0
            for i in range(column + 1, len(tree_map[1])):
                right += 1
                if tree_map[row][i] >= tree:
                    break

            # up
            up = 0
            for i in range(row - 1, -1, -1):
                up += 1
                if tree_map[i][column] >= tree:
                    break

            # down
            down = 0
            for i in range(row + 1, len(tree_map)):
                down += 1
                if tree_map[i][column] >= tree:
                    break

            highest_scenic_score = max(highest_scenic_score, left * right * up * down)

    return highest_scenic_score


if __name__ == '__main__':
    test_file = grid(8, True, True)
    file = grid(8, integer=True)
    assert(21 == part1(test_file))
    assert(8 == part2(test_file))
    print('part1:', part1(file))
    print('part2:', part2(file))
