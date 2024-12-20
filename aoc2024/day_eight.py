s = """............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............"""

s2 = """..........
..........
..........
....a.....
..........
.....a....
..........
..........
..........
.........."""

def parse(lines: str):
    pos = {}
    x = y = 0
    for line in lines.split('\n'):
        for p in line:
            if p != '.':
                value = [{'x': x, 'y': y}]
                if p in pos:
                    value = pos[p] + value
                pos[p] = value
            x += 1
        y += 1
        x = 0

    return pos


pos = parse(s2)
        
                
            

