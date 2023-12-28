def solution(input13):
    starting_index = 0
    index_next_empty_line = 0
    sumPart1 = 0
    sumPart2 = 0

    while index_next_empty_line < len(input13) and starting_index < len(input13):
        #  find index_next_empty_line
        for i in range(starting_index + 1, len(input13)):
            if len(input13[i]) == 0:
                index_next_empty_line = i
                break
        if starting_index > index_next_empty_line:
            index_next_empty_line = len(input13)

        # check vertical mirror
        for col in range(len(input13[starting_index]) - 1):
            inconsistencies_found = 0
            for row in range(starting_index, index_next_empty_line):
                for offset in range(min(col, len(input13[starting_index]) - 2 - col) + 1):
                    if input13[row][col - offset] != input13[row][col + offset + 1]:
                        inconsistencies_found += 1
            if inconsistencies_found == 0:
                sumPart1 += col + 1
            elif inconsistencies_found == 1:
                sumPart2 += col + 1

        # check horizontal mirror
        for row in range(starting_index, index_next_empty_line - 1):
            inconsistencies_found = 0
            for col in range(len(input13[starting_index])):
                for offset in range(min(row - starting_index, index_next_empty_line - row - 2) + 1):
                    if input13[row - offset][col] != input13[row + offset + 1][col]:
                        inconsistencies_found += 1
            if inconsistencies_found == 0:
                sumPart1 += (row - starting_index + 1) * 100
            elif inconsistencies_found == 1:
                sumPart2 += (row - starting_index + 1) * 100

        starting_index = index_next_empty_line + 1

    print("Part1: ", sumPart1)
    print("Part2: ", sumPart2)


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
