from readFile import *


def rope_bridge(instructions, rope_length):
    visited = set()
    visited.add((0, 0))
    rope = [(0, 0) for x in range(rope_length)]
    for motion in instructions:
        direction, amount_of_steps = motion.split(' ')
        for step in range(int(amount_of_steps)):
            # change position head
            if direction == 'R':
                rope[0] = (rope[0][0], rope[0][1] + 1)
            elif direction == 'L':
                rope[0] = (rope[0][0], rope[0][1] - 1)
            elif direction == 'U':
                rope[0] = (rope[0][0] + 1, rope[0][1])
            elif direction == 'D':
                rope[0] = (rope[0][0] - 1, rope[0][1])
            else:
                exit(9)

            # check if we need to change tail position
            for i in range(1, len(rope)):
                if rope[i - 1][0] - rope[i][0] > 1:
                    if rope[i - 1][1] - rope[i][1] > 1:
                        rope[i] = (rope[i - 1][0] - 1, rope[i - 1][1] - 1)
                    elif rope[i - 1][1] - rope[i][1] < -1:
                        rope[i] = (rope[i - 1][0] - 1, rope[i - 1][1] + 1)
                    else:
                        rope[i] = (rope[i - 1][0] - 1, rope[i - 1][1])
                elif rope[i - 1][0] - rope[i][0] < -1:
                    if rope[i - 1][1] - rope[i][1] > 1:
                        rope[i] = (rope[i - 1][0] + 1, rope[i - 1][1] - 1)
                    elif rope[i - 1][1] - rope[i][1] < -1:
                        rope[i] = (rope[i - 1][0] + 1, rope[i - 1][1] + 1)
                    else:
                        rope[i] = (rope[i - 1][0] + 1, rope[i - 1][1])
                elif rope[i - 1][1] - rope[i][1] > 1:
                    rope[i] = (rope[i - 1][0], rope[i - 1][1] - 1)
                elif rope[i - 1][1] - rope[i][1] < -1:
                    rope[i] = (rope[i - 1][0], rope[i - 1][1] + 1)
            visited.add(rope[-1])

    return len(visited)


if __name__ == '__main__':
    test_file_small = line_str(91, True)
    test_file_large = line_str(92, True)
    assert(13 == rope_bridge(test_file_small, 2))
    assert(1 == rope_bridge(test_file_small, 10))
    assert(36 == rope_bridge(test_file_large, 10))
    file = line_str(9)
    print('part1:', rope_bridge(file, 2))
    print('part2:', rope_bridge(file, 10))
