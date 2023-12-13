def solution(input):
    starting_index = 0
    index_next_empty_line = 0
    sumPart1 = 0
    while index_next_empty_line < len(input) and starting_index < len(input):
        #  find index_next_empty_line
        for i in range(starting_index + 1, len(input)):
            if len(input[i]) == 0:
                index_next_empty_line = i
                break
        if starting_index > index_next_empty_line:
            index_next_empty_line = len(input)

        mirror_found = False
        # check vertical mirror
        for i in range(len(input[starting_index]) - 1):
            did_break = False
            for j in range(starting_index, index_next_empty_line - 1):
                if did_break:
                        break
                for k in range(min(i, len(input[starting_index]) - 2 - i) + 1):
                    if input[j][i - k] != input[j][i + k + 1]:
                        did_break = True
                        break
            if not did_break:
                mirror_found = True
                sumPart1 += i + 1
                break

        # check horizontal mirror
        if not mirror_found:
            for i in range(starting_index, index_next_empty_line - 1):
                did_break = False
                for j in range(len(input[starting_index])):
                    if did_break:
                        break
                    for k in range(min(i - starting_index, index_next_empty_line - i - 2) + 1):
                        if input[i - k][j] != input[i + k + 1][j]:
                            did_break = True
                            break
                if not did_break:
                    mirror_found = True
                    sumPart1 += (i - starting_index + 1) * 100
                    break

        if not mirror_found:
            print("ERROR mirror not found")
            exit(1)

        starting_index = index_next_empty_line + 1                   

    print("Part1: " ,sumPart1)


if __name__ == '__main__':
    new_grid = []
    with open('inputs/day13.txt', 'r') as file:
        lines = file.read().split('\n')
        for l in lines:
            new_line = []
            for c in l:
                new_line.append(c)
            new_grid.append(new_line)
    solution(new_grid)