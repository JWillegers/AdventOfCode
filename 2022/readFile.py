def get_folder(test: bool):
    if test:
        return 'testFiles'
    else:
        return 'inputFiles'

'''
line() is used for inputs that look like this:
line 1: good
line 2: jkae
line 3: jpae
etc
returns a list of strings
'''
def line_str(day: int, test=False):
    with open(get_folder(test) + '/day' + str(day) + '.txt', 'r') as file:
        lines = file.read().split('\n')
    return lines

'''
line() is used for inputs that look like this:
line 1: 352
line 2: 724
line 3: 45
etc
returns a list of integers
'''
def line_int(day: int, test=False):
    with open(get_folder(test) + '/day' + str(day) + '.txt', 'r') as file:
        lines = file.read().split('\n')
    new_lines = []
    for n in lines:
        new_lines.append(int(n))
    return new_lines

'''
grid() returns a 2D array
inputs don't have separators in a line based on my 2022 experience
'''
def grid(day: int, test=False, integer=False):
    lines = line_str(day, test)
    new_grid = []
    for l in lines:
        new_line = []
        for c in l:
            if integer:
                new_line.append(int(c))
            else:
                new_line.append(c)
        new_grid.append(new_line)
    return new_grid
