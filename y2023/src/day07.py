def solution(lines):
    bookkeeping = []
    card_strength = ['A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2']
    for i in range(len(lines)):
        """
        Codes for each hand
        50 = five of a kind
        40 = four of a kind
        32 = full house
        30 = three of a kind
        22 = two pair
        21 = one pair
        10 = high card
        
        stored in bookkeeping as (code part1, code part2, cards, bid)
        """
        split = lines[i].split(" ")
        cards = split[0]
        bid = int(split[1])
        codeP1 = 10
        codeP2 = 10
        cards_checked = []
        occurrences_J = 0
        # find code for cards
        for a in range(5):
            c = cards[a]
            if c not in cards_checked:
                cards_checked.append(c)
                occurrences = 1
                for j in range(a + 1, 5):
                    if cards[j] == c:
                        occurrences += 1
                match occurrences:
                    case 5:
                        codeP1 = 50
                    case 4:
                        codeP1 = 40
                    case 3:
                        if codeP1 == 21:
                            codeP1 = 32
                        else:
                            codeP1 = 30
                    case 2:
                        if codeP1 == 21:
                            codeP1 = 22
                        elif codeP1 == 30:
                            codeP1 = 32
                        else:
                            codeP1 = 21
                if c == 'J':
                    occurrences_J = occurrences
        #find code part2
        match occurrences_J:
            case 5:
                codeP2 = 50
            case 4:
                codeP2 = 50
            case 3:
                codeP2 = 50 if codeP1 == 32 else 40
            case 2:
                if codeP1 == 32:
                    codeP2 = 50
                elif codeP1 == 22:
                    codeP2 = 40
                else:
                    codeP2 = 30
            case 1:
                codeP2 = codeP1 + 10
                if codeP2 == 20:
                    codeP2 = 21
                if codeP2 == 31:
                    codeP2 = 30
            case 0:
                codeP2 = codeP1


        #do bookkeeping
        if len(bookkeeping) == 0:
            bookkeeping.append([codeP1, codeP2, cards, bid])
            continue

        index = 0
        while (True):
            # check if code is weaker
            if codeP1 < bookkeeping[index][0]:
                bookkeeping.insert(index, [codeP1, codeP2, cards, bid])
                break

            # compare two cards
            if codeP1 == bookkeeping[index][0]:
                tripleBreak = False
                doubleBreak = False
                for j in range(5):
                    if doubleBreak or tripleBreak:
                        break

                    if cards[j] == bookkeeping[index][2][j]:
                        continue

                    for char in card_strength:
                        if cards[j] == char:
                            # cards is stronger
                            doubleBreak = True
                            break

                        if bookkeeping[index][2][j] == char:
                            # cards is weaker
                            bookkeeping.insert(index, [codeP1, codeP2, cards, bid])
                            tripleBreak = True
                            break

                if tripleBreak:
                    break
            index += 1
            if index == len(bookkeeping):
                bookkeeping.append([codeP1, codeP2, cards, bid])
                break

    p1 = 0
    for i in range(len(bookkeeping)):
        p1 += (i + 1) * bookkeeping[i][3]
    print("Part1:", p1)

    #part2
    card_strength = ['A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J']
    part2 = []
    for item in bookkeeping:
        if len(part2) == 0:
            part2.append(item)
            continue

        index = 0
        while (True):
            # check if code is weaker
            if item[1] < part2[index][1]:
                part2.insert(index, item)
                break

            # compare two cards
            if item[1] == part2[index][1]:
                tripleBreak = False
                doubleBreak = False
                for j in range(5):
                    if doubleBreak or tripleBreak:
                        break

                    if item[2][j] == part2[index][2][j]:
                        continue

                    for char in card_strength:
                        if item[2][j] == char:
                            # cards is stronger
                            doubleBreak = True
                            break

                        if part2[index][2][j] == char:
                            # cards is weaker
                            part2.insert(index, item)
                            tripleBreak = True
                            break

                if tripleBreak:
                    break
            index += 1
            if index == len(part2):
                part2.append(item)
                break

    p2 = 0
    for i in range(len(part2)):
        p2 += (i + 1) * part2[i][3]
    print("Part1:", p2)


if __name__ == '__main__':
    with open('inputs/day07.txt', 'r') as file:
        l = file.read().split('\n')
    solution(l)
