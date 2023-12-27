from copy import deepcopy


def solution(lines):
    # parse
    seeds = lines[0].split(" ")
    current = []
    for i in range(1, len(seeds), 2):
        current.append([int(seeds[i]), int(seeds[i + 1])])

    maps = [[] for i in range(7)]
    index = 0
    for i in range(3, len(lines)):
        if len(lines[i]) == 0:
            index += 1
            continue

        if not lines[i][0].isdigit():
            continue

        maps[index].append(lines[i].split(" "))
        for a in range(len(maps[index][-1])):
            maps[index][-1][a] = int(maps[index][-1][a])

    # run
    for index in range(7):
        new_current = []
        for item in current:
            did_break = False
            for dest, src, range_ in maps[index]:
                # case 0: intersection = none
                if item[0] + item[1] - 1 < src or item[0] > src + range_ - 1:
                    continue

                # case 1: item fully within [src, src + range)
                if item[0] >= src and item[0] + item[1] - 1 <= src + range_ - 1:
                    new_current.append([dest + (item[0] - src), item[1]])
                    did_break = True
                    break

                # case 2: item is partially within [src, src + range) and partially in front of it
                if item[0] < src <= item[0] + item[1] - 1 <= src + range_ - 1:
                    new_current.append([dest, item[1] - (src - item[0])])
                    item[1] = src - item[0]
                    continue

                # case 3: item is partially within [src, src + range) and partially in after of it
                if src <= item[0] <= src + range_ - 1 < item[0] + item[1] - 1:
                    new_current.append([dest + (item[0] - src), src + range_ - item[0]])
                    item[1] = item[1] - (src + range_ - item[0])
                    item[0] = src + range_
                    continue

                # case 4: [src, src + range) fully falls within item
                new_current.append([dest, range_])
                current.append([src + range_, item[0] + item[1] - (src + range_)])
                item[1] = src - item[0]

            if not did_break:
                new_current.append(item)

        current = deepcopy(new_current)
        current = sorted(current, key=lambda x: x[0])

    print("day 5 part 2:", current[0][0])


if __name__ == '__main__':
    with open('inputs/day05.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)
