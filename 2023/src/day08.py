import math

def solution(lines):
    nodes = {}

    #parse
    for i in range(2, len(lines)):

        for c in ["(", ',', ')']:
            lines[i] = lines[i].replace(c, "")
        split = lines[i].split(" ")
        nodes[split[0]] = (split[2], split[3])

    # run
    current = "AAA"
    stepsP1 = 0
    index = 0
    while current != "ZZZ":
        if lines[0][index] == 'L':
            current = nodes[current][0]
        else:
            current = nodes[current][1]
        stepsP1 += 1
        index = (index + 1) % len(lines[0])

    print("Part1:", stepsP1)

    # find all starting points
    current = []
    for n in nodes.keys():
        if n[2] == 'A':
            current.append(n)
    # run
    first_occurrence = [0 for x in range(len(current))]
    index = 0
    stepsP2 = 0
    while 0 in first_occurrence:
        for i in range(len(current)):
            if lines[0][index] == 'L':
                current[i] = nodes[current[i]][0]
            else:
                current[i] = nodes[current[i]][1]
            if current[i][2] == 'Z' and first_occurrence[i] == 0:
                first_occurrence[i] = stepsP2 + 1
        stepsP2 += 1
        index = (index + 1) % len(lines[0])
    print("Part2:", lcm(first_occurrence))


def lcm(arr):
    r = 1
    for i in arr:
        r = math.lcm(r, i)
    return r


if __name__ == '__main__':
    with open('inputs/day08.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)
