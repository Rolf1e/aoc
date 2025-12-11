s = """987654321111111
811111111111119
234234234234278
818181911112111"""

rows = s.split("\n")


def find_two_best_joltage(batteries):
    first, second = 0, 0
    for i, b in enumerate(batteries):
        v = int(b)
        if v > first and i < len(batteries) - 1:
            first, second = v, int(batteries[i + 1])
        elif v > second:
            second = v

    return int(f'{first}{second}')


def find_best_joltage(batteries, target_length=12):
    digits = []
    start_position = 0
    for _ in range(target_length):
        skipable = len(batteries) - start_position - (target_length - len(digits))
        search = batteries[start_position: start_position + skipable + 1]
        best_digit = max(search)
        digits.append(best_digit)
        start_position += search.index(best_digit) + 1

    return int(''.join(digits))


sum_joltage = 0
sum_joltage_12 = 0

for row in rows:
    joltage = find_two_best_joltage(row)
    sum_joltage += joltage

    joltage_12 = find_best_joltage(row, 12)
    sum_joltage_12 += joltage_12

    print(row, joltage, joltage_12)

print(f"Sum of joltage: {sum_joltage}")
print(f"Sum of joltage 12: {sum_joltage_12}")
