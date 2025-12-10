s = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"

def is_invalid_twice(id_str: str) -> bool:
    return is_invalid(id_str, 2)

def is_invalid(id_str: str, times: int) -> bool:
    if len(id_str) % times != 0:
        return False
    index_half = int(len(id_str) / times)
    half = id_str[0:index_half]
    return id_str == half * times

def is_invalid_at_least_twice(id_str) -> bool:
    for times in range(2, len(id_str) + 1):
        if is_invalid(id_str, times):
            return True
    return False

rows = s.split(",")
acc = 0
acc_at_least_twice = 0

for row in rows:
    start, end = map(int, row.split("-"))
    ids = [i for i in range(start, end + 1)]
    invalid_ids = []
    invalid_at_least_twice = []
    # print(row, ids)
    for id in ids:
        id_str = str(id)
        if is_invalid_twice(id_str):
            invalid_ids.append(id)
        if is_invalid_at_least_twice(id_str):
            invalid_at_least_twice.append(id)

    acc += sum(invalid_ids)
    acc_at_least_twice += sum(invalid_at_least_twice)

print(f"Actual total: {acc}")
print(f"Actual total (at least twice): {acc_at_least_twice}")
