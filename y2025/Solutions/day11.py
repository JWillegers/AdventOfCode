from copy import deepcopy

def parse() -> any:
    ret = any
    with open('y2025/Input/day11.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        ret = file_body.split('\n')
    return ret

def solvePart1(input: any) -> str:
    return "No solution found"

def solvePart2(input: any) -> str:
    return "No solution found"

if __name__ == '__main__':
    input = parse()
    print('part1:', solvePart1(deepcopy(input)))
    print('part2:', solvePart2(input))