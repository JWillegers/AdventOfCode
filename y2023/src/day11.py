def solution(lines):
    empty_rows = []
    for i in range(len(lines)):
        if '#' not in lines[i]:
            empty_rows.append(i)
            
    empty_cols = []
    for i in range(len(lines[0])):
        galaxy_found = False
        for l in lines:
            if l[i] == '#':
                galaxy_found = True
                break
        if not galaxy_found:
            empty_cols.append(i)

    # find all galaxies
    galaxies = []
    for i in range(len(lines)):
        for j in range(len(lines[i])):
            if lines[i][j] == '#':
                galaxies.append([i, j])

    sumP1 = 0
    sumP2 = 0
    for a in range(len(galaxies)):
        for b in range(a + 1, len(galaxies)):
            sumP1 += abs(galaxies[a][0] - galaxies[b][0]) + abs(galaxies[a][1] - galaxies[b][1])
            sumP2 += abs(galaxies[a][0] - galaxies[b][0]) + abs(galaxies[a][1] - galaxies[b][1])

            # do the way the galaxies are found: galaxies[a][0] <= galaxies[b][0]
            for i in range(galaxies[a][0], galaxies[b][0]):
                if i in empty_rows:
                    sumP1 += 1
                    sumP2 += 1000000 - 1

            for i in range(min(galaxies[a][1], galaxies[b][1]), max(galaxies[a][1], galaxies[b][1])):
                if i in empty_cols:
                    sumP1 += 1
                    sumP2 += 1000000- 1


    print("Part1:", sumP1)
    print("Part2:", sumP2)


if __name__ == '__main__':
    with open('inputs/day11.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)
