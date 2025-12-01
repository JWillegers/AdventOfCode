from copy import deepcopy

def parse() -> any:
    ret = any
    with open('y2025/Input/day1.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        ret = file_body.split('\n')
    return ret

def solvePart1(input: any) -> str:
    counter = 0
    dial = 50
    for instruction in input:
        if len(instruction) > 0:
            direction, amount = instruction[0], int(instruction[1:])
            if direction == 'R':
                dial = (dial + amount) % 100
            elif direction == 'L':
                dial = (dial - amount) % 100

            if dial == 0:
                counter += 1
    return str(counter)

def solvePart2(input: any) -> str:
    counter = 0
    dial = 50
    for instruction in input:
        if len(instruction) > 0:
            direction, amount = instruction[0], int(instruction[1:])

            if direction == 'R':
                new_dial = (dial + amount)
            elif direction == 'L':
                new_dial = (dial - amount)
                # offset if dial starts at 0
                if dial == 0 and direction == 'L':
                    counter -= 1
                # offset if dial ends at 0, -100, -200, ...
                if new_dial % 100 == 0:
                    counter += 1
            counter += abs(new_dial // 100)
            dial = new_dial % 100
    return str(counter) 

if __name__ == '__main__':
    input = parse()
    print(solvePart1(deepcopy(input)))
    print(solvePart2(input))