def parse() -> tuple[any, any]:
    ret = []
    part2 = []
    with open('y2025/Input/day6.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        part2 = file_body.split('\n')
        for i in range(len(part2) - 1):
            sp = part2[i].split(' ')
            lst = []
            for num in sp:
                if len(num) > 0:
                    lst.append(int(num))
            ret.append(lst)
        sp = part2[-1].split(' ')
        lst = []
        for c in sp:
                if len(c) > 0:
                    lst.append(c)
        ret.append(lst)
    return ret, part2

def solvePart1() -> tuple[str, any]:
    input, inputPart2 = parse()
    total = 0
    for col in range(len(input[0])):
        subtotal = 0
        if input[-1][col] == '*':
            subtotal = 1
            for row in range(len(input) - 1):
                subtotal *= input[row][col]
        else:
            for row in range(len(input) - 1):
                subtotal += input[row][col]
        total += subtotal

    return str(total), inputPart2

def solvePart2(input: any) -> str:
    total = 0
    nums = []
    for col in range(len(input[0])-1, -1, -1):
        num = ""
        for row in range(len(input)):
            if input[row][col] == ' ':
                continue

            if input[row][col] == '*' or input[row][col] == '+':
                nums.append(int(num))
                subtotal = 1 if input[row][col] == '*' else 0
                for n in nums:
                    if input[row][col] == '*':
                        subtotal *= n
                    else:
                        subtotal += n
                total += subtotal
                nums.clear()
                num = ""
            else:
                num += input[row][col]
        if num != "":
            nums.append(int(num))

    return str(total)

if __name__ == '__main__':
    answerPart1, inputPart2 = solvePart1()
    print('part1:', answerPart1)
    print('part2:', solvePart2(inputPart2))