from copy import deepcopy

def parse() -> any:
    ret = []
    with open('y2025/Input/day4.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        lines = file_body.split('\n')
        for line in lines:
            ret.append([[x, 0] for x in line])
    return ret

def solvePart1(input: any) -> str:
    accessible = 0
    for row in range(len(input)):
        for col in range(len(input[row])):
            if input[row][col][0] == '.':
                continue

            # south west
            if col > 0 and row < len(input) - 1 \
                    and input[row+1][col-1][0] == '@':
                input[row][col][1] += 1
                input[row+1][col-1][1] += 1

            # south
            if row < len(input) - 1 and input[row+1][col][0] == '@':
                input[row][col][1] += 1
                input[row+1][col][1] += 1

            # south east
            if col < len(input[row]) - 1 and row < len(input) - 1 \
                    and input[row+1][col+1][0] == '@':
                input[row][col][1] += 1
                input[row+1][col+1][1] += 1

            # east
            if col < len(input[row]) - 1 and input[row][col+1][0] == '@':
                input[row][col][1] += 1
                input[row][col+1][1] += 1

            if input[row][col][1] < 4:
                accessible += 1

    return str(accessible)

def solvePart2(input: any) -> str:
    removed = 0
    removed_any = True
    while removed_any:
        removed_any = False
    
        for row in range(len(input)):
            for col in range(len(input[row])):
                if input[row][col][0] in ['.', 'x']:
                    continue

                count = 0

                # north
                if row > 0 and input[row-1][col][0] == '@':
                    count += 1

                # north west
                if row > 0 and col > 0 and input[row-1][col-1][0] == '@':
                    count += 1

                # west
                if col > 0 and input[row][col-1][0] == '@':
                    count += 1

                # south west
                if col > 0 and row < len(input) - 1 \
                        and input[row+1][col-1][0] == '@':
                    count += 1

                # south
                if row < len(input) - 1 and input[row+1][col][0] == '@':
                    count += 1

                # south east
                if col < len(input[row]) - 1 and row < len(input) - 1 \
                        and input[row+1][col+1][0] == '@':
                    count += 1

                # east
                if col < len(input[row]) - 1 and input[row][col+1][0] == '@':
                    count += 1

                # north east
                if row > 0 and col < len(input[row]) - 1 and \
                        input[row-1][col+1][0] == '@':
                    count += 1

                if count < 4:
                    input[row][col][0] = 'x'
                    removed_any = True
                    removed += 1

    return str(removed)

if __name__ == '__main__':
    input = parse()
    print('part1:', solvePart1(deepcopy(input)))
    print('part2:', solvePart2(input))