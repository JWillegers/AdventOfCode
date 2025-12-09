from copy import deepcopy
import numpy as np

def parse() -> any:
    ret = []
    with open('y2025/Input/day8.txt', 'r') as file:
        file_body = file.read()
        if len(file_body) == 0:
            raise Exception("File is empty")
        lines = file_body.split('\n')
        for line in lines:
            s = line.split(',')
            ret.append(np.array((int(s[0]), int(s[1]), int(s[2]))))
    return ret

def solvePart1(input: any) -> str:
    inf = 9*(10**10)
    matrix = np.full((len(input), len(input)), inf, dtype=float)

    for i in range(len(input)):
        for j in range(i+1, len(input)):
            matrix[i][j] = np.linalg.norm(input[i]-input[j])
    
    circuits = []
    pairs_found = 0

    while pairs_found < 1000:
        i, j = np.where(matrix==matrix.min())
        i = i[0]
        j = j[0]
    
        circuits = add_to_circuit(circuits, (input[i], input[j]))
        matrix[i][j] = inf
        pairs_found += 1

    circuits = sorted(circuits, key=len, reverse=True)
    answer = len(circuits[0]) * len(circuits[1]) * len(circuits[2])
    return str(answer)

def add_to_circuit(circuits: list[set], pair) -> list[set]:
    first = tuple(pair[0])
    second = tuple(pair[1])
    for circuit in circuits:
        if first in circuit and second in circuit:
            return circuits
        if first in circuit or second in circuit:
            circuit.add(first)
            circuit.add(second)
            circuits = merge_circuits(circuit, circuits)
            return circuits

    circuits.append(set())
    circuits[-1].add(first)
    circuits[-1].add(second)
    return circuits

def merge_circuits(circuit: set, circuits: list[set]) -> list[set]:
    new_circuits = []
    for i in range(len(circuits)):
        if circuits[i] == circuit:
            continue
        intersect = circuit.intersection(circuits[i])
        if len(intersect) > 0:
            union = circuit.union(circuits[i])
            circuit = union
        else:
            new_circuits.append(circuits[i])
    new_circuits.append(circuit)

    return new_circuits

def solvePart2(input: any) -> str:
    return "No solution found"

if __name__ == '__main__':
    input = parse()
    print('part1:', solvePart1(deepcopy(input)))
    print('part2:', solvePart2(input))