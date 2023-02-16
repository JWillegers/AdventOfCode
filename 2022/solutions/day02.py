from readFile import *


shape_scores = {
'A': 1, 'X': 1,
'B': 2, 'Y': 2,
'C': 3, 'Z': 3
}
score_draw = 3
score_win = 6


def part1(matches):
    score = 0
    for opponent, player in matches:
        score += shape_scores[player]

        if opponent == 'A':  # rock
            if player == 'X':  # rock
                score += score_draw
            elif player == 'Y':  # paper
                score += score_win
        elif opponent == 'B':  # paper
            if player == 'Y':  # paper
                score += score_draw
            elif player == 'Z':  # scissor
                score += score_win
        else:  # opponent == 'C' # scissors
            if player == 'Z':  # scissors
                score += score_draw
            elif player == 'X':  # rock
                score += score_win
    return score


def part2(matches):
    lose_match = {
        'A': 'C',
        'B': 'A',
        'C': 'B'
    }
    win_match = {
        'A': 'B',
        'B': 'C',
        'C': 'A'
    }
    score = 0
    for opponent, desired_outcome in matches:
        if desired_outcome == 'X':  # lose
            score += shape_scores[lose_match[opponent]]
        elif desired_outcome == 'Y':  # draw
            score += shape_scores[opponent]
            score += score_draw
        else:  # win
            score += shape_scores[win_match[opponent]]
            score += score_win

    return score


if __name__ == '__main__':
    file = line_str(2)
    games = []
    for line in file:
        split = line.split(' ')
        games.append((split[0], split[1]))
    print('part1:', part1(games))
    print('part2:', part2(games))
