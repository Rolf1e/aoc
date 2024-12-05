s  = """MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX"""


def countXMAS(s: str) -> int:
    return s.count('XMAS') + s.count('SAMX')
        

lines = s.split('\n') 

data = open('./input4.txt', 'r').read()
lines = data.split('\n')[:-1]

max_col = len(lines[0])
max_row = len(lines)
cols = [[] for _ in range(max_col)]
rows = [[] for _ in range(max_row)]
fdiag = [[] for _ in range(max_row + max_col - 1)]
bdiag = [[] for _ in range(len(fdiag))]
min_bdiag = -max_row + 1

for x in range(max_col):
    for y in range(max_row):
        cell = lines[y][x]
        cols[x] += [cell]
        rows[y] += [cell]
        fdiag[x+y] += [cell]
        bdiag[x-y-min_bdiag] += [cell]

count = 0
to_be_checked = cols + rows + fdiag + bdiag

for e in to_be_checked:
    line = ''.join(e)
    count += countXMAS(line)


print('part one', count)

def countMAS(left: str, right: str) -> int:
    occ = ['MAS', 'SAM']
    if left in occ and right in occ:
        return 1
    else:
        return 0

count2 = 0
for x in range(1, len(lines) - 1):
    for y in range(1, len(lines[0]) - 1):
        if 'A' == lines[y][x]:
            right = lines[y-1][x-1] + 'A' +  lines[y+1][x+1]
            left = lines[y+1][x-1] + 'A' +  lines[y-1][x+1]
            count2 += countMAS(right, left)

print('part two', count2)

