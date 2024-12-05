raw_rules = """47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13"""

raw_page_updates = """75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47"""

raw_rules = open('./input5_a.txt', 'r').read()[:-1]
raw_page_updates = open('./input5_b.txt', 'r').read()[:-1]

def parse_rules(raw_rules: str) -> dict[str]:
    rules = {}
    for line in raw_rules.split('\n'):
        rule = line.split('|')
        key = int(rule[0])
        value = [int(rule[1])]
        if key in rules:
            value = rules[key] + value
        rules[key] = value
    return rules

def parse_page_updates(raw_page_updates: str) -> list[list[int]]:
    page_updates = []
    for line in raw_page_updates.split('\n'):
        page_updates += [[int(e) for e in line.split(',')]]
    return page_updates

rules = parse_rules(raw_rules)
page_updates = parse_page_updates(raw_page_updates)

from functools import cmp_to_key

def compare(a, b):
    if b in rules.get(a, []):
        return -1
    elif a in rules.get(b, []):
        return 1
    else:
        return 0

sum = 0
sum2 = 0
for p in page_updates:
    sorted_pages = sorted(p, key=cmp_to_key(compare))
    at_half = sorted_pages[int(len(sorted_pages) / 2)]
    if p == sorted_pages:
        sum += at_half
    else:
        sum2 += at_half

print('part one', sum)
print('part two', sum2)




