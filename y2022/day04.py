from readFile import *


def part1(all_pairs):
    number_fully_contained = 0
    for pair in all_pairs:
        elves = pair.split(',')
        elf_1 = (int(elves[0].split('-')[0]), int(elves[0].split('-')[1]))
        elf_2 = (int(elves[1].split('-')[0]), int(elves[1].split('-')[1]))
        if (elf_1[0] <= elf_2[0] and elf_1[1] >= elf_2[1]) or \
                (elf_1[0] >= elf_2[0] and elf_1[1] <= elf_2[1]):
            number_fully_contained += 1

    return number_fully_contained


def part2(all_pairs):
    overlapping_assignments = 0
    for pair in all_pairs:
        elves = pair.split(',')
        elf_1 = set([x for x in range(int(elves[0].split('-')[0]), int(elves[0].split('-')[1]) + 1)])
        elf_2 = set([x for x in range(int(elves[1].split('-')[0]), int(elves[1].split('-')[1]) + 1)])
        if len(elf_1.intersection(elf_2)) > 0:
            overlapping_assignments += 1
    return overlapping_assignments


# cleaner solution with some influence of other solutions
def clean_solution(all_pairs):
    number_fully_contained = 0
    overlapping_assignments = 0
    for pair in all_pairs:
        elves = pair.split(',')
        elf_1_min, elf_1_max = elves[0].split('-')
        elf_2_min, elf_2_max = elves[1].split('-')
        elf_1 = set([x for x in range(int(elf_1_min), int(elf_1_max) + 1)])
        elf_2 = set([x for x in range(int(elf_2_min), int(elf_2_max) + 1)])
        if elf_1.issubset(elf_2) or elf_2.issubset(elf_1):
            number_fully_contained += 1
        if len(elf_1.intersection(elf_2)) > 0:
            overlapping_assignments += 1
    return number_fully_contained, overlapping_assignments


if __name__ == '__main__':
    file = line_str(4)
    print('=== original solution ===')
    print('part1:', part1(file))
    print('part2:', part2(file))
    print('=== clean solution ===')
    p1, p2 = clean_solution(file)
    print('part1:', p1, '\npart2:', p2)
