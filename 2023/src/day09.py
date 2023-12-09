
def solution(lines):
    # parse
    inputs = []
    for line in lines:
        split = line.split(" ")
        numbers = []
        for num in split:
            numbers.append(int(num))
        inputs.append(numbers)

    p1 = 0
    p2 = 0
    for sequence in inputs:
        p = extrapolate(sequence)
        p1 += p[1]
        p2 += p[0]

    print("Part 1:", p1)
    print("Part 2:", p2)


def extrapolate(seq):
    lower_level = []
    all0 = True
    for i in range(len(seq) - 1):
        n = seq[i + 1] - seq[i]
        lower_level.append(n)
        if n != 0:
            all0 = False
    if all0:
        return [seq[0], seq[-1]]
    else:
        r = extrapolate(lower_level)
        return [seq[0] - r[0], seq[-1] + r[1]]


if __name__ == '__main__':
    with open('inputs/day09.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)
