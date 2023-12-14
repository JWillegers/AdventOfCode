def solution(input):
    for col in range(len(input[0])):
        changes_made = True
        first_empty_space = find_next_empty_space(input, 0, col)
        index = first_empty_space + 1
        while index < len(input):
            if input[index][col] == 'O':
                input[index][col] = '.'
                input[first_empty_space][col] = 'O'
                first_empty_space += 1
            if input[index][col] == '#':
                first_empty_space = find_next_empty_space(input, index, col)
                if first_empty_space == -1:
                    break
                index = first_empty_space
            index += 1
    
    p1 = 0
    for row in range(len(input)):
        p1 += input[row].count('O') * (len(input) - row)

    print("Part1", p1)


def find_next_empty_space(input, starting_index, col):   
    for index in range(starting_index, len(input)):
        if input[index][col] == '.':
            return index
    return -1


if __name__ == '__main__':
    new_grid = []
    with open('inputs/day14.txt', 'r') as file:
        lines = file.read().split('\n')
        for l in lines:
            new_line = []
            for c in l:
                new_line.append(c)
            new_grid.append(new_line)
    solution(new_grid)