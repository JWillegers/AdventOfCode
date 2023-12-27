from copy import deepcopy


def solution(lines):
    rules = {}
    index = 0
    sumPart1 = 0

    # parse rules
    while len(lines[index]) > 0:
        split = lines[index].split('{')
        split[1] = split[1][:len(split[1]) - 1]
        rules[split[0]] = split[1].split(',')

        index += 1

    # check parts
    index += 1

    while index < len(lines):
        split = lines[index].replace(',', '=').replace('}','=').split('=')
        x = int(split[1])
        m = int(split[3])
        a = int(split[5])
        s = int(split[7])

        map_ = {'x': x, 'm': m, 'a': a, 's': s}

        workflow = 'in'

        while workflow not in ['A', 'R']:
            for instruction in rules[workflow]:
                if ':' in instruction:
                    letter = instruction[0]
                    sign = instruction[1]
                    split = instruction.split(':')
                    limit = int(split[0][2:])

                    if (sign == '<' and map_[letter] < limit) or (sign == '>' and map_[letter] > limit):
                        workflow = split[1]
                        break
                else:
                    workflow = instruction
                    break

        if workflow == 'A':
            sumPart1 += x + m + a + s

        index += 1
    print("Part 1:", sumPart1)

    p2 = 0
    queue = [['in', 1, 4000, 1, 4000, 1, 4000, 1, 4000]]
    index_map = {'x': 1, 'm': 3, 'a': 5, 's': 7}
    while len(queue) > 0:
        current = queue.pop(0)
        workflow = current[0]

        if workflow == 'R':
            continue
        elif workflow == 'A':
            p = 1
            for i in range(1, len(current), 2):
                p *= (current[i + 1] - current[i] + 1)
            p2 += p
            continue

        for instruction in rules[workflow]:
            if ':' in instruction:
                letter = instruction[0]
                sign = instruction[1]
                split = instruction.split(':')
                limit = int(split[0][2:])
                if sign == '>' and current[index_map[letter]] <= limit:
                    new_item = deepcopy(current)
                    current[index_map[letter] + 1] = limit
                    new_item[index_map[letter]] = limit + 1
                    new_item[0] = split[1]
                    queue.append(new_item)
                elif sign == '<' and current[index_map[letter] + 1] >= limit:
                    new_item = deepcopy(current)
                    current[index_map[letter]] = limit
                    new_item[index_map[letter] + 1] = limit - 1
                    new_item[0] = split[1]
                    queue.append(new_item)
            elif instruction == 'R':
                break
            elif instruction == 'A':
                p = 1
                for i in range(1, len(current), 2):
                    p *= (current[i + 1] - current[i] + 1)
                p2 += p
                break
            else:
                current[0] = instruction
                queue.append(current)

    print("Part 2:", p2)


if __name__ == '__main__':
    with open('inputs/day19.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)