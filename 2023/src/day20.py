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
    print("part 1: ", high_pulses_send * low_pulses_send)


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