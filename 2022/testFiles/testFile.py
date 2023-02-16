import sys
sys.path.append('..')
import readFile

def run():
    print(readFile.line_str(0, True))
    print('\n')
    print(readFile.grid(0, True))
    print('\n')
    print(readFile.line_int(0, True))
    print('\n')
    print(readFile.grid(0, True, True))

if __name__ == '__main__':
    run()