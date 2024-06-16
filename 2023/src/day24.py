def solution(input24):
    seen_hailstones = []
    valid_intersections = 0

    for line in input24:
        line = line.replace(" ", "")
        parts = line.split('@')
        starting_point = list(map(int, parts[0].split(',')))  # [px, py, pz]
        vector = list(map(int, parts[1].split(','))) # [vx, vy, vz]

        for hailstone in seen_hailstones:
            # top
            constant_top = (starting_point[0] - hailstone[0][0]) / hailstone[1][0]
            lambda_top = vector[0] / hailstone[1][0]

            # middle
            constant_middle = (starting_point[1] - hailstone[0][1]) / hailstone[1][1]
            lambda_middle = vector[1] / hailstone[1][1]

            if (lambda_middle - lambda_top) == 0:
                continue

            lambda_actual = (constant_middle - constant_top) / (lambda_top - lambda_middle)

            # intersection point
            x = starting_point[0] + lambda_actual * vector[0]
            y = starting_point[1] + lambda_actual * vector[1]

            # check if intersection point is within bounds and in the future
            if check_bounds(x) and check_bounds(y) and \
                check_in_future(x, starting_point[0], vector[0]) and check_in_future(y, starting_point[1], vector[1]) and \
                check_in_future(x, hailstone[0][0], hailstone[1][0]) and check_in_future(y, hailstone[0][1], hailstone[1][1]):
                valid_intersections += 1

        seen_hailstones.append([starting_point, vector])

    print("part1:", valid_intersections)


def check_bounds(var):
    lower_bound = 200000000000000
    upper_bound = 400000000000000
    return lower_bound <= var <= upper_bound


def check_in_future(crossing_cord, starting_point, velocity):
    if velocity > 0:
        return crossing_cord > starting_point
    else:
        return crossing_cord < starting_point


if __name__ == '__main__':
    with open('inputs/day24.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)