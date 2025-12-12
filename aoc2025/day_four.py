s = """..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@."""

def display_grid(grid):
    for row in grid:
        print("".join(row))

def compute_neighbours_coordinates(x, y, maxX, maxY):
    neighbours = [
        (x - 1, y - 1), (x - 1, y), (x - 1, y + 1),
        (x, y - 1), (x, y + 1),
        (x + 1, y - 1), (x + 1, y), (x + 1, y + 1)
    ]
    return list(filter(lambda coord: 0 <= coord[0] < maxX and 0 <= coord[1] < maxY, neighbours))


def count_adjacent(grid, x, y):
    neighbours_coord = compute_neighbours_coordinates(x, y, len(grid), len(grid[0]))
    count = 0
    for x, y in neighbours_coord:
        if "@" == grid[y][x]:
            count += 1
    return count

def copy_grid(grid) :
    return [row.copy() for row in grid]

def remove_paper_roll(grid):
    copied_grid = copy_grid(grid)
    count_fewer_than_4 = 0
    for y in range(len(grid)):
        for x in range(len(grid[0])):
            if "@" == grid[y][x]:
                adjacent_count = count_adjacent(grid, x, y)
                if adjacent_count < 4:
                    count_fewer_than_4 += 1
                    copied_grid[y][x] = 'x'
    return copied_grid, count_fewer_than_4

grid = [list(row) for row in s.split("\n")]
print("Initial grid: ")
display_grid(grid)

count_removed = 0
copied_grid, count_fewer_than_4 = remove_paper_roll(grid)

print(f"Total cells with fewer than 4 adjacent '@': {count_fewer_than_4}")

while count_fewer_than_4 != 0:
    count_removed += count_fewer_than_4
    grid = copied_grid
    copied_grid, count_fewer_than_4 = remove_paper_roll(grid)
    
print("Updated grid: ")
display_grid(copied_grid)
    
print(f"Total removed cells: {count_removed}")
