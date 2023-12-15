def solution(input):
    steps = input.split(",")
    sumP1 = 0
    for step in steps:
        result = 0
        for char in step:
            result = ((result + ord(char)) * 17) % 256
        sumP1 += result
    print("Part 1:", sumP1)

    boxes = [[] for a in range(256)]
    for step in steps:
        box = 0
        for char in step:
            if char == '=' or char == '-':
                break
            box = ((box + ord(char)) * 17) % 256
        if '-' in step:
            step.replace('-', '')
            for item in boxes[box]:
                if item[:len(step) - 1] == step[:len(step) - 1]:
                    boxes[box].remove(item)
                    break
        if '=' in step:
            done = False
            for i in range(len(boxes[box])):
                item = boxes[box][i]
                if item[:len(item) - 2] == step[:len(step) - 2]:
                    boxes[box][i] = step 
                    done = True
                    break
            if not done:
                boxes[box].append(step)

    sumP2 = 0
    for i in range(len(boxes)):
        for j in range(len(boxes[i])):
            focal_length = int(boxes[i][j].split("=")[1])
            sumP2 += (i + 1) * (j + 1) * focal_length
                  
    print("Part 2:", sumP2)


if __name__ == '__main__':
    with open('inputs/day15.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l[0])