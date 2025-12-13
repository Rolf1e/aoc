
s = """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  """

import math
rows = s.split("\n")

operations = list(filter(lambda op: op != ' ', rows[-1]))
print('operations', operations)

# part 1
problems = {}
problems_accumulator = {}

for row in rows[:-1]:
    i = 0
    for n in row.strip().split(" "):
        if n != '':
            problems[i] = problems.get(i, []) + [int(n)]
            if operations[i] == '+':
                problems_accumulator[i] = problems_accumulator.get(i, 0) + int(n)
            elif operations[i] == '*':
                problems_accumulator[i] = problems_accumulator.get(i, 1) * int(n)
            i += 1

print('problems_accumulator', problems_accumulator)
print('problems', problems)

total = sum([v for k, v in problems_accumulator.items()])
print(f"Total sum of problems: {total}")

# part 2

raw_operations = rows[-1]
print('raw_operations', list(raw_operations))

problems_right_to_left = {}
problem_index = 0
x = len(rows[0]) - 1
while x >= 0:
    current = [rows[y][x] for y in range(len(rows) - 1)]
    problems_right_to_left[problem_index] = problems_right_to_left.get(problem_index, []) + [int(''.join(current))]
    if raw_operations[x] != ' ':
        problem_index += 1
        x -= 1 # skip empty column
    x -= 1
print('problems_right_to_left', problems_right_to_left)
sum_right_to_left = 0

reverse_operations = list(reversed(operations))

for k, v in problems_right_to_left.items():
    if reverse_operations[k] == '+':
       sum_right_to_left += sum(v)
    elif reverse_operations[k] == '*':
       sum_right_to_left += math.prod(v)
   
print('sum_right_to_left', sum_right_to_left)
    
