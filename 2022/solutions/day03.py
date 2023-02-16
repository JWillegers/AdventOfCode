from readFile import *


def part1(input_file, priority_dict):
    priority = 0
    for rucksack in input_file:
        assert(len(rucksack) % 2 == 0)
        compartment_one = set()
        compartment_two = set()
        for i in range(len(rucksack)):
            character = rucksack[i]
            if i < len(rucksack) / 2:
                compartment_one.add(character)
            else:
                compartment_two.add(character)
        characters_in_both_compartments = compartment_one.intersection(compartment_two)
        for c in characters_in_both_compartments:
            priority += priority_dict[c]
    return priority


def part2(input_file, priority_dict):
    n_groups_of_three = len(input_file) / 3
    i = 0
    priority = 0
    while i < n_groups_of_three:
        elf_one = set(input_file[3 * i])
        elf_two = set(input_file[3 * i + 1])
        elf_three = set(input_file[3 * i + 2])
        overlap = elf_one.intersection(elf_two, elf_three)
        assert(1 == len(overlap))
        priority += priority_dict[overlap.pop()]
        i += 1
    return priority


if __name__ == '__main__':
    file = line_str(3)
    test_file = line_str(3, True)
    priority_scores = dict()
    score = 1
    for letter in range(ord('a'), ord('z') + 1):
        priority_scores[chr(letter)] = score
        score += 1
    for letter in range(ord('A'), ord('Z') + 1):
        priority_scores[chr(letter)] = score
        score += 1
    assert(157 == part1(test_file, priority_scores))
    assert(70 == part2(test_file, priority_scores))
    print('part1:', part1(file, priority_scores))
    print('part2:', part2(file, priority_scores))
