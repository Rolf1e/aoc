map = """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...""".split('\n')


import time
from enum import Enum
class Direction(Enum):
    UP = 0
    RIGHT = 1
    DOWN = 2
    LEFT = 3

def get_symbol(current: Direction) -> str:
    match current:
        case Direction.UP:
            return '^'
        case Direction.RIGHT:
            return '>'
        case Direction.DOWN:
            return 'v'
        case Direction.LEFT:
            return '<'
        case _ : raise Exception('illegal')

def get_next_facing(current: Direction) -> int:
    match current:
        case Direction.UP:
            return Direction.RIGHT
        case Direction.RIGHT:
            return Direction.DOWN
        case Direction.DOWN:
            return Direction.LEFT
        case Direction.LEFT:
            return Direction.UP
        case _ : raise Exception('illegal')

def compute_next_coordinates(facing: Direction, x, y: int) -> tuple[int, int]:
    match facing:
        case Direction.UP:
            return x, y - 1
        case Direction.RIGHT:
            return x + 1, y
        case Direction.DOWN:
            return x, y + 1
        case Direction.LEFT:
            return x - 1, y
        case _ : raise Exception('illegal')
    

def find_start(map: list[list[str]]) -> tuple[int, int]:
    x = y = 0
    for l in map:
        if '^' in l:
            return l.index('^'), y
        else:
            y += 1


def predict_path(map: list[list[str]], x: int, y: int):
    finished = False
    facing = Direction.UP
    cx, cy = x, y
    bx, by = len(map) - 1, len(map[0]) - 1
    while not finished:
        time.sleep(0.03)
        nx, ny = compute_next_coordinates(facing, cx, cy)
        # print(nx, ny)
        if bx < nx or by < ny or nx < 0 or ny < 0:
            finished = True
        else:
            at_next = map[ny][nx]
            if '#' == at_next:
                facing = get_next_facing(facing)
                nx, ny = compute_next_coordinates(facing, cx, cy)

            map[cy][cx] = 'X'
            cx = nx
            cy = ny
            map[ny][nx] = get_symbol(facing)
        display(map)

def display(map):
    print()
    for l in map:
        print(''.join(l))

# map = open('./input6.txt', 'r').read().split('\n')

x, y = find_start(map)
map = [list(l) for l in map]
predict_path(map, x, y)

guard_steps = 0
for l in map:
    guard_steps += ''.join(l).count('X')
print(guard_steps + 1)
