def solution(input13):
    starting_index = 0
    index_next_empty_line = 0
    sumPart1 = 0
    while index_next_empty_line < len(input13) and starting_index < len(input13):
        #  find index_next_empty_line
        for i in range(starting_index + 1, len(input13)):
            if len(input13[i]) == 0:
                index_next_empty_line = i
                break
        if starting_index > index_next_empty_line:
            index_next_empty_line = len(input13)

        t = -1

        mirror_found = False
        # check vertical mirror
        for col in range(len(input13[starting_index]) - 1):
            did_break = False
            for row in range(starting_index, index_next_empty_line - 1):
                if did_break:
                    break
                for offset in range(min(col, len(input13[starting_index]) - 2 - col) + 1):
                    if input13[row][col - offset] != input13[row][col + offset + 1]:
                        did_break = True
                        break
            if not did_break:
                mirror_found = True
                t = col + 1
                sumPart1 += col + 1
                break

        # check horizontal mirror
        #if not mirror_found:
        for row in range(starting_index, index_next_empty_line - 1):
            did_break = False
            for col in range(len(input13[starting_index])):
                if did_break:
                    break
                for offset in range(min(row - starting_index, index_next_empty_line - row - 2) + 1):
                    if input13[row - offset][col] != input13[row + offset + 1][col]:
                        did_break = True
                        break
            if not did_break:
                if (mirror_found):
                    print(50*'=')
                    print("start:", starting_index)
                    print("vertical:", t)
                    print("horizonatal:", row - starting_index + 1)
                    for i in range(starting_index, index_next_empty_line):
                        print(input13[i])
                    print(50 * '=')
                mirror_found = True
                sumPart1 += (row - starting_index + 1) * 100
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