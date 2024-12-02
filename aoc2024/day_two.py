s = """7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9"""

reports = s.split("\n")

INC = 1
DEC = -1

def compare(levels):
    safe = True
    direction = 0 # 1 inc -1 dec

    if levels[0] > levels[1]:
        direction = DEC
    elif levels[0] < levels[1]:
        direction = INC

    for i in range(1, len(levels)):
        left, right = levels[i-1], levels[i]

        if left == right:
            return False

        if INC == direction:
            if not(1 <= right - left <= 3):
                return False
        elif DEC == direction:
            if not(1 <= left - right <= 3):
                return False

    return True


def compare_with_discrepency(levels):
    for i in range(len(levels)):
        sub_list = levels[:i] + levels[i+1:]
        if compare(sub_list):
            return True

    return False

    
count_safe = 0
count_discrepency = 0
  
for report in reports:
    levels = [int(n) for n in report.split(" ")]
    
    comp = compare(levels)
    if comp:
        count_safe += 1
    
    if comp or compare_with_discrepency(levels):
        count_discrepency += 1
    
print(count_safe)
print(count_discrepency)
