def parse() -> tuple[any, any]:
    splitters = list()
    start = any
    with open('y2025/Input/day7'
    '.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        ret = file_body.split('\n')
        for row in range(len(ret)):
            for col in range(len(ret[row])):
                if ret[row][col] == '^':
                    splitters.append((row, col))
                elif ret[row][col] == 'S':
                    start = (row, col)
                

    return start, splitters

def solvePart1(input: any) -> tuple[str, any]:
    start = input[0]
    splitters = input[1]
    found = set()
    for curr in splitters:
        col = curr[1]
        for r in range(curr[0], 0, -1):
            # there is a splitter above the current splitter
            if (r, col) in found:
                break

            if (r, col + 1) in found or (r, col - 1) in found:
                found.add(curr)
                break

        if (0, col) == start:
            found.add(curr)


    return str(len(found)), (start, found)

def solvePart2(input: any) -> str:
    queue = dict()
    queue[input[0]] = 1
    splitters = input[1]
    max_row = 0
    for i in splitters:
        max_row = max(max_row, i[0])

    for i in range(1, max_row+1):
        new_queue = dict()
        for loc, amount in queue.items():
            if (loc[0]+1, loc[1]) in splitters:
                new_queue = add_to_queue(new_queue, (loc[0]+1, loc[1]+1), amount)
                new_queue = add_to_queue(new_queue, (loc[0]+1, loc[1]-1), amount)
            else:
                new_queue = add_to_queue(new_queue, (loc[0]+1, loc[1]), amount)

        queue = new_queue

    return str(sum(queue.values()))

def add_to_queue(new_queue, loc, amount):
    if loc in new_queue.keys():
        new_queue[loc] += amount
    else:
        new_queue[loc] = amount

    return new_queue

if __name__ == '__main__':
    input = parse()
    answerPart1, input = solvePart1(input)
    print('part1:', answerPart1)
    print('part2:', solvePart2(input))