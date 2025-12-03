from copy import deepcopy
import numpy as np


def parse() -> list[list[int]]:
    ret = []
    with open('y2025/Input/day3.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        banks = file_body.split('\n')
        for bank in banks:
            l = [int(x) for x in bank]
            ret.append(l)
    return ret


def solvePart1(input: list[list[int]]) -> str:
    sum = 0
    for bank in input:
        index_max = np.argmax(bank)
        highest = bank[index_max]
        left = 0
        right = 0
        for i in range(0, index_max):
            left = max(left, 10*bank[i] + highest)
        highest = 10 * highest
        for i in range(index_max+1, len(bank)):
            right = max(right, highest + bank[i])
        sum += max(left, right)

    return str(sum)


def solvePart2(input: list[list[int]]) -> str:
    sum = 0
    for bank in input:
        sum += part2recursion(bank, 12)

    return str(sum)


def part2recursion(bank: list[int], digits_left: int) -> int:
    # no digits left
    if digits_left == 0:
        return 0

    # trivial case
    if len(bank) == digits_left:
        sum = 0
        for i in range(digits_left):
            sum += bank[i] * (10 ** (digits_left - i - 1))
        return sum

    index_max = np.argmax(bank)

    # highest number is first number
    if index_max == 0:
        return (bank[index_max] * (10 ** (digits_left - 1))
                + part2recursion(bank[1:], digits_left-1))

    # highest number is last number
    if index_max + 1 == len(bank):
        return 10 * part2recursion(bank[:len(bank)-1], digits_left-1) + bank[index_max]

    # highest number is not the last digits_left digits
    if index_max < len(bank) - digits_left:
        return (bank[index_max] * (10 ** (digits_left - 1))
                + part2recursion(bank[index_max+1:], digits_left-1))

    # highest number is the last digits_left digits
    digits_trailing = len(bank) - index_max
    leading = part2recursion(bank[:index_max], digits_left - digits_trailing) * \
        (10 ** (digits_trailing))
    trailing = 0
    for i in range(digits_trailing):
        trailing += bank[index_max + i] * (10 ** (digits_trailing - i - 1))

    return leading + trailing


if __name__ == '__main__':
    input = parse()
    print('part1:', solvePart1(deepcopy(input)))
    print('part2:', solvePart2(input))
