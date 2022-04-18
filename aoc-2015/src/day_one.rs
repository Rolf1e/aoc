// https://adventofcode.com/2015/day/1

/// Part one:
/// ( -> +1 floor
/// ) -> -1 floor
pub fn resolve_floor_level(input: &str) -> i32 {
    let mut floor_level = 0;
    for c in input.chars() {
        floor_level += floor_rule(c) as i32;
    }

    floor_level
}

fn floor_rule(c: char) -> i8 {
    if c == '(' {
        1
    } else if c == ')' {
        -1
    } else {
        0
    }
}

#[cfg(test)]
pub mod test_2015_day_one {
    use crate::day_one::resolve_floor_level;

    // === Part one ===
    #[test]
    pub fn should_be_ground_floor() {
        assert_eq!(0, resolve_floor_level("(())"));
        assert_eq!(0, resolve_floor_level("()()"));
    }

    #[test]
    pub fn should_be_third_floor() {
        assert_eq!(3, resolve_floor_level("((("));
        assert_eq!(3, resolve_floor_level("(()(()("));
        assert_eq!(3, resolve_floor_level("))((((("));
    }

    #[test]
    pub fn should_be_first_basement() {
        assert_eq!(-1, resolve_floor_level("())"));
        assert_eq!(-1, resolve_floor_level("))("));
    }

    #[test]
    pub fn should_be_third_basement() {
        assert_eq!(-3, resolve_floor_level(")))"));
        assert_eq!(-3, resolve_floor_level(")())())"));
    }
}
