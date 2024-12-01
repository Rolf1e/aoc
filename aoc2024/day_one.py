s = """3   4
4   3
2   5
1   3
3   9
3   3"""
 

splited = s.split('\n')

row_sep = "   "

left = []
right = []

for row in splited: 
    r = [int(c) for c in row.split(row_sep) ]
    left.append(r[0])
    right.append(r[1])

left.sort()
right.sort()

sum = 0
sum2 = 0

for i, lv in enumerate(left):
    distance = abs(lv - right[i])
    sum += distance

    c = right.count(lv)
    sum2 += lv * c

print(sum)

print(sum2)
    


