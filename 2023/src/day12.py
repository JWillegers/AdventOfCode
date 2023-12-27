import time


def solution(lines):
    sumPart1 = 0
    for line in lines:
        split = line.split(" ")
        springs = split[0]
        numbers = split[1].split(",")
        checks = []
        for i in range(len(numbers)):
            numbers[i] = int(numbers[i])
            checks.append(False)

        '''
        This puzzle seems to be inspired by a nonogram puzzle, 
        so my algorithm will resemble how I would solve such a puzzle
        1. Equality
            1a. If number damaged springs + minimum distance between them == number of springs -> 1 possible way
            1b. Do the number of '?' and '#' add up to the number of damaged springs
        2. Check the groups of damaged springs that you know for certain. 
        Take for example this line from the examples: ????.#...#... 4,1,1
        We see twice the pattern ".#.", thus we know we found the two 1s.
        We can set their boolean to true to know we have found them
        3. check instances of where '?' and '#' are next to each other, can we already draw conclusions?
            3a. is there a possibility that we already can say something about a '#?#' sequence?
            3b. is the group of '#' the largest unsolved group and should the '?' be a '.'
            3c. is the group of '#' the largest unsolved group by a margin larger than 2 and should the '?' be a '#'
                DISCLAIMER: only works for '?' at one end of the largest unsolved group, otherwise look at 3d
            3d. is the group of '#' the largest unsolved group by a margin larger than 3 and should the '?' be a '#'
            3e. for something like '???#???' with number 6, we can expand 
    
        '''

        # 1a
        if sum(numbers) + len(numbers) - 1 == len(springs):
            sumPart1 += 1
            continue

        # 1b
        count = 0
        for i in range(len(springs)):
            if not springs[i] == '.':
                count += 1
        if count == sum(numbers):
            sumPart1 += 1
            continue

        # 2
        springs, numbers, checks = step2(springs, numbers, checks)

        # 3
        springs, numbers, checks = step3(springs, numbers, checks)

        print(springs, "|", numbers, "|", checks)
        time.sleep(0.01)

    print("Part 1:", sumPart1)


def step2(springs, numbers, checks):
    m = {}  # number of '#' as key; occurrences as value
    counter = 0
    check_before = True  # checks is char before '#' is a '.'
    for i in range(len(springs)):
        if springs[i] == '#':
            counter += 1
            if counter == 1 and i > 0:
                check_before = springs[i - 1] == '.'
        elif counter > 0:
            if check_before and springs[i] == '.':
                if counter in m:
                    m[counter] += 1
                else:
                    m[counter] = 1
            counter = 0
    # processing for if last spring is damaged
    if counter > 0 and check_before:
        if counter in m:
            m[counter] += 1
        else:
            m[counter] = 1
    for key, value in m.items():
        if key not in numbers:
            print("ERROR step 2")
            exit(1)
        if value == numbers.count(key):
            for j in range(len(numbers)):
                if key == numbers[j]:
                    checks[j] = True
        #  a possibility could be that value < numbers.count(key). This is not implemented
    return springs, numbers, checks


def step3(springs, numbers, checks):
    group_size = 0
    start_group = 0
    for i in range(len(springs) + 1):
        if not i == len(springs) and springs[i] == '#':
            group_size += 1
            if i > 0 and not springs[i - 1] == '#':
                start_group = i
        elif group_size > 0:
            lug = largest_unsolved_group(numbers, checks)
            # 3a. is there a possibility that we already can say something about a '#?#' sequence?
            if (start_group >= 2 and springs[start_group - 2] == '#') or (i <= len(springs) - 2 and springs[i + 1] == '#'):
                # ##???.??????#?#?#??? 3,1,1,10
                pass
            #  3b the group of '#' the largest unsolved group and should the '?' be a '.'
            elif group_size == lug:
                if start_group >= 1:
                    springs = replacer(springs, '.', start_group - 1)
                if i <= len(springs) - 1:
                    springs = replacer(springs, '.', i)
                checks[numbers.index(lug)] = True
            # 3c the group of '#' the largest unsolved group by a margin larger than 2 and should the '?' be a '#'
            elif group_size + 1 == lug and (lug - 1) not in numbers:
                start = start_group >= 1 and springs[start_group - 1] == '?'
                end = i <= len(springs) - 1 and springs[i] == '?'
                if start and not end:
                    springs = replacer(springs, '#', start_group - 1)
                elif not start and end:
                    springs = replacer(springs, '#', i)
                checks[numbers.index(lug)] = True
            group_size = 0

    return springs, numbers, checks


def largest_unsolved_group(numbers, checks):
    largest = 0
    if False not in checks:
        print("ERROR [largest_unsolved_group] everything has been checked")
        exit(1)
    for i in range(len(numbers)):
        if not checks[i]:
            largest = max(largest, numbers[i])
    if not numbers.count(largest) == 1:
        print("ERROR [largest_unsolved_group] largest number not unique")
        exit(1)
    return largest


# https://stackoverflow.com/a/41753038
def replacer(s, newstring, index, nofail=False):
    # raise an error if index is outside of the string
    if not nofail and index not in range(len(s)):
        raise ValueError("index outside given string")

    # if not erroring, but the index is still not in the correct range..
    if index < 0:  # add it to the beginning
        return newstring + s
    if index > len(s):  # add it to the end
        return s + newstring

    # insert the new string between "slices" of the original
    return s[:index] + newstring + s[index + 1:]


if __name__ == '__main__':
    with open('inputs/day12.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)
