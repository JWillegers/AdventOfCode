from readFile import *


# node for linked list
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None
        self.previous = None

    def remove_node(self):
        next_node = self.next
        prev_node = self.previous

        prev_node.next = next_node
        next_node.previous = prev_node

        self.next = None
        self.previous = None

    def add_after(self, target):
        target_2 = target.next

        target.next = self
        self.previous = target

        target_2.previous = self
        self.next = target_2

    def add_before(self, target):
        target_2 = target.previous

        target.previous = self
        self.next = target

        target_2.next = self
        self.previous = target_2


def solution(data, part2):
    # create linked list called nodes
    decryption_key = 811589153
    nodes = []
    last_seen_n = None
    for i in range(len(data)):
        if part2:
            n = Node(data[i] * decryption_key)
        else:
            n = Node(data[i])
        nodes.append(n)
        if last_seen_n is not None:
            n.previous = last_seen_n
            last_seen_n.next = n
        if i + 1 == len(data):
            nodes[0].previous = n
            n.next = nodes[0]
        last_seen_n = n


    # since the order in nodes doesn't change, but only the node next and previous, we can loop over nodes
    for t in range(10 if part2 else 1):
        for i in range(len(nodes)):
            node = nodes[i]
            move_by = node.data
            if move_by > 0:
                target = node.next
                node.remove_node()
                for j in range(1, move_by % (len(nodes) - 1)):
                    target = target.next

                node.add_after(target)
            elif move_by < 0:
                target = node.previous
                node.remove_node()
                for j in range(-1, move_by % (len(nodes) - 1) - (len(nodes) - 1), -1):
                    target = target.previous
                node.add_before(target)

    node_x = None
    for node in nodes:
        if node.data == 0:
            node_x = node
            break
    sum_numbers = 0
    for i in range(1, 3001):
        node_x = node_x.next
        if i % 1000 == 0:
            sum_numbers += node_x.data

    return sum_numbers


# https://realpython.com/linked-lists-python/
if __name__ == '__main__':
    test_file = line_int(20, True)
    assert(solution(test_file, False) == 3)
    assert(solution(test_file, True) == 1623178306)
    file = line_int(20)
    print('part1:', solution(file, False))
    print('part2:', solution(file, True))
