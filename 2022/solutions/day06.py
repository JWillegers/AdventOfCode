from readFile import *


def solution(datastream, n_distinct_characters):
    for i in range(n_distinct_characters + 1, len(datastream)):
        if len(set(datastream[i-n_distinct_characters:i])) == n_distinct_characters:
            return i
    return 'Nothing found'


def one_line(datastream, n_distinct_characters):
    return min([(i if len(set(datastream[i-n_distinct_characters:i])) == n_distinct_characters else len(datastream) + 1) for i in range(n_distinct_characters + 1, len(datastream))])


if __name__ == '__main__':
    file = line_str(6)
    assert(solution(file[0], 4) == one_line(file[0], 4))
    assert(solution(file[0], 14) == one_line(file[0], 14))
    print('part1:', solution(file[0], 4))
    print('part2:', solution(file[0], 14))
