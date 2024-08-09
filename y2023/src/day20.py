import math
import networkx as nx
import matplotlib.pyplot as plt

flip_flop_symbol = '%'
conjunction_symbol = '&'
# high pulse = True; low pulse = False


def solution(input_20):
    flip_flops, conjunctions, mapping, state = parse(input_20)
    cycle = 1
    low_pulses_send = 0
    high_pulses_send = 0

    # press the button 1000 times
    while cycle <= 1000:
        queue = ['broadcaster']
        low_pulses_send += 1
        cycle += 1

        # while a signal goes through the system
        while len(queue) > 0:
            current = queue.pop(0)

            for dest in mapping[current]:
                if state[current]:
                    high_pulses_send += 1
                else:
                    low_pulses_send += 1

                if dest in flip_flops:
                    if not state[current]:  # LOW
                        state[dest] = not state[dest]
                        queue.append(dest)
                elif dest in conjunctions.keys():  # conjunction
                    # update incoming signal
                    conjunctions[dest][current] = state[current]

                    # if all high
                    if not (False in conjunctions[dest].values()):
                        state[dest] = False  # LOW
                    else:
                        state[dest] = True   # HIGH
                    queue.append(dest)
                elif dest == 'broadcaster':
                    print("TODO")
                    exit()
    print("part 1:", high_pulses_send * low_pulses_send)

    # reset states & conjunctions
    for k in state.keys():
        state[k] = False
    for v in conjunctions.values():
        for k in v.keys():
            v[k] = False

    '''
    For Part 2 some assumptions are made based on plotting the graph and 
    that there are some unknown restrictions for the input
    
    1) the broadcaster only has outgoing signals
    2) these are send to a multiple clusters
    3) these clusters exist of flipflops and 1 conjunction module
    4) the conjunction module has an outgoing signal to another conjunction module
    5) the receiving modules of assumption 4 all input into a single conjunction module
    6) the single conjunction module of assumption 5 is the input for rx
    '''

    # identify clusters
    clusters = []
    cluster_entry_point = []
    for end_point in mapping['broadcaster']:
        cluster = set()
        queue = [end_point]
        while len(queue) > 0:
            current = queue.pop(0)
            if current not in cluster:
                cluster.add(current)
                if current in flip_flops:
                    for outgoing in mapping[current]:
                        queue.append(outgoing)
        clusters.append(cluster)
        cluster_entry_point.append(end_point)

    # find receiving modules for assumption 5
    assumption5 = set()
    for k, v in conjunctions.items():
        if len(v.values()) == 1:
            assumption5.add(k)

    '''
    Logic that follows from the assumptions:
    a) the conjunction modules in the clusters need to be low at the same time. 
        But only once per button press (doesn't count if they are all low at the start)
    '''
    times = []
    times_diff = []
    for i in range(len(clusters)):
        cycle = 0
        times.append([])
        times_diff.append([])

        while len(times[i]) < 20:
            cycle += 1
            queue = [cluster_entry_point[i]]
            state[cluster_entry_point[i]] = not state[cluster_entry_point[i]]
            count_conjunction_output_low = 0

            while len(queue) > 0:
                current = queue.pop(0)

                for dest in mapping[current]:

                    if dest in flip_flops:
                        if not state[current]:  # LOW
                            state[dest] = not state[dest]
                            queue.append(dest)
                    elif dest in conjunctions.keys():  # conjunction
                        # update incoming signal
                        conjunctions[dest][current] = state[current]

                        # if all high
                        if not (False in conjunctions[dest].values()):
                            state[dest] = False  # LOW
                            count_conjunction_output_low += 1
                        else:
                            state[dest] = True  # HIGH
                        if dest in flip_flops:
                            queue.append(dest)

            if count_conjunction_output_low == 1:
                times[i].append(cycle)
                if len(times[i]) >= 2:
                    times_diff[i].append(times[i][-1] - times[i][-2])

    # LCM
    max_diff = [x[0] for x in times]
    lcm = 1
    for a in max_diff:
        lcm = lcm*a//math.gcd(lcm, a)
    print("part 2:", lcm)

    # debug
    # plot_graph(flip_flops, conjunctions, mapping)


def plot_graph(flip_flops, conjunctions, mapping):
    G = nx.DiGraph()
    color_map = []
    for key in mapping:
        if key not in G.nodes:
            G.add_node(key)
            color_map = det_color(flip_flops, conjunctions, key, color_map)
        for v in mapping[key]:
            if v not in G.nodes:
                G.add_node(v)
                color_map = det_color(flip_flops, conjunctions, v, color_map)
            G.add_edge(key, v)
    nx.draw(G, node_color=color_map, with_labels=True)
    plt.show()


# red: flip_flop, green: conjunction, blue: other (broadcaster & rx)
def det_color(flip_flops, conjunctions, key, color_map):
    if key in flip_flops:
        color_map.append('red')
    elif key in conjunctions.keys():
        color_map.append('green')
    else:
        color_map.append('blue')
    return color_map


def parse(input_20):
    flip_flops = []
    conjunctions = {}
    mapping = {}
    state = {}
    for line in input_20:
        s = line.split(' ')

        src = s[0][1:]
        if s[0][0] == flip_flop_symbol:
            flip_flops.append(src)
            mapping[src] = []
        elif s[0][0] == conjunction_symbol:
            conjunctions[src] = {}
            mapping[src] = []
        else:
            src = s[0]
            mapping[src] = []

        state[src] = False

        for i in range(2, len(s)):
            if s[i][-1] == ',':
                mapping[src].append(s[i][:-1])
            else:
                mapping[src].append(s[i])

    for line in input_20:
        s = line.split(' ')

        src = s[0][1:]
        for i in range(2, len(s)):
            dest = s[i]
            if s[i][-1] == ',':
                dest = s[i][:-1]

            if dest in conjunctions.keys():
                conjunctions[dest][src] = False

    return flip_flops, conjunctions, mapping, state


if __name__ == '__main__':
    with open('inputs/day20.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)