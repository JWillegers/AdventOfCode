from copy import deepcopy


def solution(input24):
    seen_hailstones = []
    valid_intersections = 0

    for line in input24:
        line = line.replace(" ", "")
        parts = line.split('@')
        starting_point = list(map(int, parts[0].split(',')))  # [px, py, pz]
        vector = list(map(int, parts[1].split(','))) # [vx, vy, vz]

        for hailstone in seen_hailstones:
            x, y, z, _lambda = intersection_point(starting_point, vector, hailstone, False)
            if x is None and y is None and z is None:
                continue

            # check if intersection point is within bounds and in the future
            if check_bounds(x) and check_bounds(y) and \
                check_in_future(x, starting_point[0], vector[0]) and check_in_future(y, starting_point[1], vector[1]) and \
                check_in_future(x, hailstone[0][0], hailstone[1][0]) and check_in_future(y, hailstone[0][1], hailstone[1][1]):
                valid_intersections += 1

        seen_hailstones.append([starting_point, vector])

    print("part1:", valid_intersections)

    limit = 1000
    for x in range(-limit, limit+1):
        for y in range(-limit, limit+1):
            for z in range(-limit, limit+1):
                hailstones = []
                for i in range(4):
                    h = deepcopy(seen_hailstones[i])
                    h[1][0] = h[1][0] - x
                    h[1][1] = h[1][1] - y
                    h[1][2] = h[1][2] - z
                    hailstones.append(h)
                x0, y0, z0, _lambda0 = intersection_point(hailstones[0][0], hailstones[0][1], hailstones[1], True)
                x1, y1, z1, _lambda1 = intersection_point(hailstones[0][0], hailstones[0][1], hailstones[2], True)
                x2, y2, z2, _lambda2 = intersection_point(hailstones[0][0], hailstones[0][1], hailstones[3], True)

                if x0 is None or x1 is None or x2 is None:
                    continue

                if x0 == x1 and x0 == x2 and y0 == y1 and y0 == y2 and z0 == z1 and z0 == z2:
                    print("part2:", int(x1 + y2 + z2))
                    return

                #
                #     valid_intersections = 0
                #     for hailstone in seen_hailstones:
                #         _x, _y, _z, _lambda = intersection_point([x0, y0, z0], [x, y, z], hailstone, True)
                #         if _x is None and _y is None and _z is None:
                #             exit(101)
                #
                #         # check if intersection point is within bounds and in the future
                #         if check_in_future(_x, x0, x) and \
                #                 check_in_future(_y, y0, y) and \
                #                 check_in_future(_z, z0, z):
                #             valid_intersections += 1
                #     if valid_intersections == len(seen_hailstones):
                #         print("part2:", x1 * y2 * z2)
                #         print(x0, y0, z0)
                #         return
    print("ERROR: no solution found for part 2")


def intersection_point(starting_point, vector, hailstone, part2):

    # top
    if hailstone[1][0] != 0:
        constant_top = (starting_point[0] - hailstone[0][0]) / hailstone[1][0]
        lambda_top = vector[0] / hailstone[1][0]
    else:
        constant_top = starting_point[0]
        lambda_top = 0

    # middle
    if hailstone[1][1] != 0:
        constant_middle = (starting_point[1] - hailstone[0][1]) / hailstone[1][1]
        lambda_middle = vector[1] / hailstone[1][1]
    else:
        constant_middle = starting_point[1]
        lambda_middle = 0

    if (lambda_middle - lambda_top) == 0:
        return None, None, None, None

    lambda_actual = (constant_middle - constant_top) / (lambda_top - lambda_middle)

    # intersection point
    x = starting_point[0] + lambda_actual * vector[0]
    y = starting_point[1] + lambda_actual * vector[1]
    z = starting_point[2] + lambda_actual * vector[2]

    if part2:
        mu = constant_middle + lambda_middle * lambda_actual
        check = starting_point[2] + vector[2] * lambda_actual == hailstone[0][2] + hailstone[1][2] * mu
        if not check:
            return None, None, None, None

    return x, y, z, lambda_actual


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