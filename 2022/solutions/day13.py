from readFile import *
import ast


def part1(data):
    matching_indices = []
    for index in range(0, len(data), 3):
        # method 7 in https://www.geeksforgeeks.org/python-program-convert-string-list/
        row_left = ast.literal_eval(data[index])
        row_right = ast.literal_eval(data[index + 1])
        if part1_recursion(row_left, row_right)[1]:
            matching_indices.append(index // 3 + 1)

    return sum(matching_indices)


def part1_recursion(row_left, row_right):
    try:
        for index in range(len(row_left)):
            left = row_left[index]
            right = row_right[index]
            if type(left) == int and type(right) == int:
                if left != right:
                    return True, left < right
            else:
                if type(left) == int:
                    left = [left]
                if type(right) == int:
                    right = [right]
                found_something, right_order = part1_recursion(left, right)
                if found_something:
                    return found_something, right_order
    except IndexError:
        # row right has more elements than row left
        return True, False
    if len(row_left) < len(row_right):
        # row left has more elements than row right
        return True, True
    return False, True


def part2(data):
    parsed_data = []

    # remove empty lines
    for i in range(len(data)):
        if len(data[i]) > 0:
            parsed_data.append(ast.literal_eval(data[i]))

    # add divider packets
    parsed_data.append([[2]])
    parsed_data.append([[6]])

    # bubble sort
    for i in range(len(parsed_data)):
        for j in range(len(parsed_data)-i-1):
            row_left = parsed_data[j]
            row_right = parsed_data[j + 1]
            if not part1_recursion(row_left, row_right)[1]:
                parsed_data[j], parsed_data[j + 1] = parsed_data[j + 1], parsed_data[j]

    # find divider packets
    index2 = parsed_data.index([[2]]) + 1
    index6 = parsed_data.index([[6]]) + 1

    return index2 * index6


if __name__ == '__main__':
    test_file = line_str(13, True)
    assert(part1(test_file) == 13)
    assert(part2(test_file) == 140)
    file = line_str(13)
    print('part1:', part1(file))
    print('part2:', part2(file))
