from copy import deepcopy


def solution(input18, p2):
    map_ = {"0": "R", "1": "D", "2": "L", "3": "U"}
    corners = []
    current = [0, 0]
    u_counter = 0
    l_counter = 0
    for line in input18:
        s = line.split(" ")
        instruction = s[0]
        number = int(s[1])
        if p2:
            hex_ = s[2][2:]
            hex_ = "0x" + hex_[:5]
            number = int(hex_, 0)
            instruction = map_[s[2][7]]
        match instruction:
            case 'R':
                current[1] += number
            case 'L':
                current[1] -= number
                l_counter += number
            case 'U':
                current[0] += number
                u_counter += number
            case 'D':
                current[0] -= number
        corners.append(deepcopy(current))

    '''
    Shoelace method.
    '''
    sumBlue = 0
    sumRed = 0
    for i in range(len(corners) - 1):
        sumBlue += corners[i][0] * corners[i + 1][1]
        sumRed += corners[i][1] * corners[i + 1][0]

    sumBlue += corners[len(corners) - 1][0] * corners[0][1]
    sumRed += corners[len(corners) - 1][1] * corners[0][0]

    inner_area = int(abs((sumBlue - sumRed) / 2))

    '''
    Pick's method
    instead of inner_area + boundary_points / 2 - 1
    I use inner_area + u_counter + l_counter + 1
    
    https://www.reddit.com/r/adventofcode/comments/18l8mao/2023_day_18_intuition_for_why_spoiler_alone/
    '''
    total_area = inner_area + u_counter + l_counter + 1

    return total_area


if __name__ == '__main__':
    with open('inputs/day18.txt', 'r') as file:
        l = file.read().split('\n')
    print("Part 1:", solution(l, False))
    print("Part 2:", solution(l, True))
