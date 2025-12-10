s = """R1000
L68
L30
R48
L5
R60
L55
L1
L99
R14
L82"""

rows = s.split('\n')

dial_position = 50

count_at_0 = 0
count_during_rotation = 0

for row in rows:
    direction, distance = row[0], int(row[1:])
    full, partial = divmod(distance, 100)
    count_during_rotation += full
    delta = -partial if direction == 'L' else partial
    new_position = dial_position + delta

    message = ""
    if dial_position != 0:
        if direction == 'L' and new_position <= 0:
            count_during_rotation += 1
            message = f"during this rotation, the dial crossed 0 {count_during_rotation} time(s)."
        elif direction == 'R' and new_position >= 100:
            count_during_rotation += 1
            message = f"during this rotation, the dial crossed 0 {count_during_rotation} time(s)."

    dial_position = new_position % 100
    count_at_0 += dial_position == 0

    print(f"The dial is rotated `{direction}{distance}` to point at `{dial_position}`; {message}")

print(f"The dial pointed at 0 a total of {count_at_0} times.")
print(f"The dial crossed 0 a total of {count_during_rotation} times.")
