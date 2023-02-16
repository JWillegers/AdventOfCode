from readFile import *
from copy import deepcopy
import itertools
import numpy as np
import math


def part1(data):
    # find all valves with flow > 0
    valves_that_we_want_open = set()
    for valve in data.keys():
        if data[valve]['flow'] > 0:
            valves_that_we_want_open.add(valve)

    # key is as follows (start, goal)
    valves_that_we_want_open.add('AA')
    steps_from_a_to_b = get_paths(data, valves_that_we_want_open)

    # assumption that I make, if False the code will break
    assert(data['AA']['flow'] == 0)
    valves_that_we_want_open.remove('AA')
    valves_that_we_want_open = list(valves_that_we_want_open)

    # greedy solution
    route = sorted(valves_that_we_want_open, key=lambda x:data[x]['flow'])
    route.insert(0, 'AA')
    max_score = calculate_score(data, route, steps_from_a_to_b, 30)

    # pairwise exchange or 2-opt
    improvement_made = True
    while improvement_made:
        improvement_made = False
        run = True
        i = 0
        while i + 1 < len(route) and run:
            j = i + 1
            while j < len(route) and run:
                new_route = swap(route, i, j)
                new_distance = calculate_score(data, new_route, steps_from_a_to_b, 30)
                if new_distance > max_score:
                    improvement_made = True
                    run = False
                    route = new_route
                    max_score = new_distance
                j += 1
            i += 1

    return max_score


# https://en.wikipedia.org/wiki/2-opt
def swap(route, v1, v2):
    new_route = []
    for i in range(v1 + 1):
        new_route.append(route[i])
    for i in range(v2, v1, -1):
        new_route.append(route[i])
    for i in range(v2 + 1, len(route)):
        new_route.append(route[i])
    assert(len(new_route) == len(route))
    return new_route


# calculate score of route, given max_time
def calculate_score(data, route, steps_from_a_to_b, max_time):
    time = 0
    index = 0
    fpm = 0
    total_flow = 0
    while time <= max_time and index + 1 < len(route):
        start = route[index]
        end = route[index + 1]
        # + 1 because we also need to open the valve
        time_needed_to_travel_and_open_valve = steps_from_a_to_b[(start, end)] + 1
        total_flow += fpm * min(max_time - time, time_needed_to_travel_and_open_valve)
        time += time_needed_to_travel_and_open_valve
        fpm += data[route[index + 1]]['flow']
        index += 1

    if time <= max_time:
        total_flow += fpm * (max_time - time)

    return total_flow


# find all paths between all pair of nodes in valves_that_we_want_open
def get_paths(data, valves_that_we_want_open):
    paths = dict()
    for x, y in itertools.combinations(valves_that_we_want_open, 2):
        paths[(x, y)] = len(find_path(x, y, data))
        paths[(y, x)] = len(find_path(y, x, data))
    return paths


# BFS between start and goal
def find_path(start, goal, data):
    visited = [start]
    queue = [[start]]
    while len(queue) > 0:
        path = queue.pop(0)
        last_location = path[-1]
        for neighbor in data[last_location]['tunnels']:
            if neighbor == goal:
                path.append(goal)
                path.remove(start)  # since we start at start, we don't have to move their anymore
                return path
            if neighbor not in visited:
                path_copy = deepcopy(path)
                path_copy.append(neighbor)
                visited.append(neighbor)
                queue.append(path_copy)
    print('ERROR: PATH NOT FOUND')
    exit(1)


# https://en.wikipedia.org/wiki/Simulated_annealing
def part2(tunnels):
    # setup simulated annealing
    temp_start = 1000.0  # starting temp
    temp_stop = 1.0  # stopping temp
    k = 1000  # iteration per temp level
    time_total = 1e6  # total allowed computation time
    time_iter = 1  # time per iteration
    t = time_total/(k*time_iter)  # iteration per temp level
    alpha = pow(temp_stop/temp_start, 1/t)  # decreasing factor
    current_temp = temp_start
    counter = 1
    current_score = 0
    max_seen = 0

    # setup general
    valves_that_we_want_open = set()
    for valve in tunnels.keys():
        if tunnels[valve]['flow'] > 0:
            valves_that_we_want_open.add(valve)

    # key is as follows (start, goal)
    valves_that_we_want_open.add('AA')
    steps_from_a_to_b = get_paths(tunnels, valves_that_we_want_open)

    # assumption that I make, if False the code will break
    assert (tunnels['AA']['flow'] == 0)
    valves_that_we_want_open.remove('AA')
    valves_that_we_want_open = list(valves_that_we_want_open)

    valves_human = valves_that_we_want_open[:len(valves_that_we_want_open)//2]
    valves_elephant = valves_that_we_want_open[len(valves_that_we_want_open)//2:]
    valves_human.insert(0, 'AA')
    valves_elephant.insert(0, 'AA')



    # Simulated annealing algorithm
    while current_temp > temp_stop:
        # select action to perform
        random = np.random.uniform()  # default is between 0.0 and 1.0
        if random < 1/4:
            # move an element from human to elephant
            new_valves_human = deepcopy(valves_human)
            new_valves_elephant = deepcopy(valves_elephant)
            element = new_valves_human.pop(np.random.randint(1, len(new_valves_human)))
            insert_at = np.random.randint(1, len(new_valves_elephant))
            new_valves_elephant.insert(insert_at, element)
        elif random < 1/2:
            # move an element from elephant to human
            new_valves_human = deepcopy(valves_human)
            new_valves_elephant = deepcopy(valves_elephant)
            element = new_valves_elephant.pop(np.random.randint(1, len(new_valves_elephant)))
            insert_at = np.random.randint(1, len(new_valves_human))
            new_valves_human.insert(insert_at, element)
        elif random < 3/4:
            # 2-opt in human
            v1 = np.random.randint(0, len(valves_human) - 1)
            v2 = np.random.randint(v1 + 1, len(valves_human))
            new_valves_human = swap(valves_human, v1, v2)
            new_valves_elephant = deepcopy(valves_elephant)
        else:
            # 2-opt in elephant
            v1 = np.random.randint(0, len(valves_elephant) - 1)
            v2 = np.random.randint(v1 + 1, len(valves_elephant))
            new_valves_human = deepcopy(valves_human)
            new_valves_elephant = swap(valves_elephant, v1, v2)

        # calculate new score
        new_score_human = calculate_score(tunnels, new_valves_human, steps_from_a_to_b, 26)
        new_score_elephant = calculate_score(tunnels, new_valves_elephant, steps_from_a_to_b, 26)
        new_score = new_score_human + new_score_elephant

        # making sure code does not break in the future
        if len(new_valves_elephant) >= 2 and len(new_valves_human) >= 2:
            # if new better than current, current = new
            if new_score > current_score:
                current_score = new_score
                max_seen = max(max_seen, current_score)
                valves_human = deepcopy(new_valves_human)
                valves_elephant = deepcopy(new_valves_elephant)
            # if new equal or worse than current
            else:
                # pick uniform between 0 and 1
                random = np.random.uniform()  # default is between 0.0 and 1.0
                delta = new_score - current_score
                if math.exp(delta / current_temp) >= random:
                    max_seen = max(max_seen, current_score)
                    current_score = new_score
                    valves_human = deepcopy(new_valves_human)
                    valves_elephant = deepcopy(new_valves_elephant)
        counter += 1
        if counter == k:  # reach iterations at this temperature
            current_temp *= alpha
            counter = 1
    return current_score, max_seen


def parse(data):
    parts = dict()
    for line in data:
        split = line.split(' ')
        parts[split[1]] = dict()
        parts[split[1]]['flow'] = int(split[4].split('=')[1].replace(';', ''))
        parts[split[1]]['tunnels'] = []
        for i in range(9, len(split)):
            parts[split[1]]['tunnels'].append(split[i].replace(',', ''))
    return parts


if __name__ == '__main__':
    test_file = line_str(16, True)
    test_tunnels = parse(test_file)
    assert(part1(test_tunnels) == 1651)
    file = line_str(16)
    global_tunnels = parse(file)
    print('part1:', part1(global_tunnels))
    possible_answer, max_seen_answer = part2(global_tunnels)
    print('part2:')
    print('result:', possible_answer)
    print('best result seen:', max_seen_answer)
    print('result is not always the optimal solution, it is an approximation')
