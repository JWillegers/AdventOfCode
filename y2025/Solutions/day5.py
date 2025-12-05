def parse() -> tuple[list[tuple[int, int]], list[int]]:
    fresh = []
    available = []
    with open('y2025/Input/day5.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        lines = file_body.split('\n')
        newline = False
        for line in lines:
            if line == "":
                newline = True
                continue
            
            if newline:
                available.append(int(line))
            else:
                split = line.split('-')
                fresh.append((int(split[0]), int(split[1])))

    fresh = sorted(fresh, key=lambda x: x[0])
    return fresh, available 

def solvePart1(fresh: list[tuple[int, int]], available: list[int]) -> str:
    fresh_and_available = 0
    for ingredient in available:
        for low, high in fresh:
            if ingredient < low:
                break
            if low <= ingredient <= high:
                fresh_and_available += 1
                break

    return str(fresh_and_available)

def solvePart2(fresh: list[tuple[int, int]]) -> str:
    count = 0
    last_low = 0
    last_high = 0
    for low, high in fresh:
        if high <= last_high:
            continue
        if last_low == low:
            if high > last_high:
                count += high - last_high 
        elif last_high >= low:
            count += high - last_high
        else:
            count += high - low + 1
        last_high = high
        last_low = low

    return str(count)

if __name__ == '__main__':
    fresh, available = parse()
    print('part1:', solvePart1(fresh, available))
    print('part2:', solvePart2(fresh))