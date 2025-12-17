s = """.......S.......
...............
.......^.......
...............
......^.^......
...............
.....^.^.^.....
...............
....^.^...^....
...............
...^.^...^.^...
...............
..^...^.....^..
...............
.^.^.^.^.^...^.
..............."""

def print_bean_shapes(bean_shapes):
    print('bean_shapes', "".join(['|' if x == 1 else '.' for x in bean_shapes]))

def print_timeline_shapes(timelines_shapes):
    print('time_shapes', ''.join(str(n) for n in timelines_shapes), sum(timelines_shapes))

rows = s.split("\n")

bean_shapes = [1 if n == 'S' else 0 for n in rows[0]]
timelines_shapes = bean_shapes.copy()

print_bean_shapes(bean_shapes)
print_timeline_shapes(timelines_shapes)

count_split = 0

for row in rows:
    new_bean_shapes = bean_shapes.copy()
    for i, c in enumerate(row):
        if c == '^' and bean_shapes[i] == 1:
            left, right = i - 1, i + 1
            
            count_split += 1
            
            new_bean_shapes[i] = 0
            new_bean_shapes[left] = 1
            new_bean_shapes[right] = 1

            timelines_shapes[left] += timelines_shapes[i]
            timelines_shapes[right] += timelines_shapes[i]
            timelines_shapes[i] = 0

    bean_shapes = new_bean_shapes
    print_bean_shapes(bean_shapes)
    print_timeline_shapes(timelines_shapes)

print(f"Total split beans: {count_split}")
print(f"Total timelines: {sum(timelines_shapes)}")
