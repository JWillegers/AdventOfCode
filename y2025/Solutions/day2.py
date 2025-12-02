from copy import deepcopy
import re

def parse() -> str:
    ret = str
    with open('y2025/Input/day2.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        ret = file_body.split('\n')
    return ret[0]

def solvePart1(input: str) -> str:
    sum = 0
    pattern = re.compile('^([0-9]+)\\1$')
    for idpair in input.split(','):
        lower, upper = idpair.split('-')
        for id in range(int(lower), int(upper)+1):
            if bool(pattern.match(str(id))):
                sum += id
    return str(sum)

def solvePart2(input: any) -> str:
    sum = 0
    pattern = re.compile('^([0-9]+)(\\1+)$')
    for idpair in input.split(','):
        lower, upper = idpair.split('-')
        for id in range(int(lower), int(upper)+1):
            if bool(pattern.match(str(id))):
                sum += id
    return str(sum)

if __name__ == '__main__':
    input = parse()
    print('part1:', solvePart1(deepcopy(input)))
    print('part2:', solvePart2(input))