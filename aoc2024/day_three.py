from re import findall 

regex = r"mul\((\d+),(\d+)\)|(do\(\))|(don't\(\))"

s = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
s2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
data = open('./input3.txt', 'r').read()

def solve(s, with_do = False): 
    enabled = True
    total = 0

    for a, b, do, dont in findall(regex, s):
        if do or dont:
            enabled = bool(do)
        else:
            x = int(a) * int(b)
            if not with_do:
                total += x
            else:
                total += x * enabled

    return total

assert 161 == solve(s)
assert 48 == solve(s2, True)

print('part one', solve(data))
print('part two', solve(data, True))

