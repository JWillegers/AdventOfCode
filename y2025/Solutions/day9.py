from copy import deepcopy

def parse() -> any:
    ret = []
    with open('y2025/Input/day9.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        lines = file_body.split('\n')
        for line in lines:
            s = line.split(',')
            ret.append((int(s[0]), int(s[1])))
    return ret

def solvePart1(input: any) -> str:
    input = sorted(input, key=lambda x:x[0])
    answer = 0
    for i in range(int(len(input)/2)):
        for j in range(len(input)-1,int(len(input)/2),-1):
            xdif = input[j][0] - input[i][0] + 1
            ydif = abs(input[j][1] - input[i][1]) + 1
            answer = max(answer, xdif*ydif)
    return str(answer)

def solvePart2(input: any) -> str:
    return "No solution found"

if __name__ == '__main__':
    input = parse()
    print('part1:', solvePart1(deepcopy(input)))
    print('part2:', solvePart2(input))