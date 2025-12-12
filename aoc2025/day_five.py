raw_id_ranges = """3-5
16-20
10-14
12-18"""

raw_ids = """1
5
8
11
17
32"""

id_ranges = []
for row in raw_id_ranges.split("\n"):
    start, end = map(int, row.split("-"))
    id_ranges.append((start, end))

id_ranges=sorted(id_ranges, key=lambda x: x[0])

merged_ranges = []

for start, end in id_ranges:
    changed = False    
    for i, (s, e) in enumerate(merged_ranges):
        if s <= start <= e:
            changed = True
            merged_ranges[i] = (min(start, s), max(end, e))
                
    if not changed:
        merged_ranges.append((start, end))
                
# part 1                
count_fresh = 0
for raw_id in raw_ids.split("\n"):
    if any(start <= int(raw_id) <= end for start, end in merged_ranges):
        count_fresh += 1

print(f"Total fresh IDs: {count_fresh}")

# part 2
count_total_fresh = 0

for id_range in merged_ranges:
    start, end = id_range
    count_total_fresh += (end + 1 - start)

print(f"Total possible fresh IDs: {count_total_fresh}")
