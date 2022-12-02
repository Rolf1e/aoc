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

/// Part two:
/// Find position of the first character that makes -1
pub fn position_first_character_enter_basement(input: &str) -> Option<i32> {
    let mut position = 1;
    let mut current_floor_level = 0;
    for c in input.chars() {
        current_floor_level += floor_rule(c) as i32;
        if current_floor_level == -1 {
            return Some(position);
        } else {
            position += 1;
        }
    }

    None
}

#[cfg(test)]
pub mod test_2015_day_one {
    use crate::day_one::{position_first_character_enter_basement, resolve_floor_level};

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

    #[test]
    pub fn should_read_input_1() {
        let file_name = "resources/inputs/day1/input1.txt";
        aoc_core::add_file_to_binary(file_name);
        match aoc_core::read_file(file_name) {
            Ok(content) => {
                assert_eq!(232, resolve_floor_level(&content));
            }
            Err(e) => println!("{}", e),
        }
    }

    // === Part two ===
    #[test]
    pub fn should_enter_basement_at_1() {
        assert_eq!(Some(1), position_first_character_enter_basement(")"));
    }

    #[test]
    pub fn should_enter_basement_at_5() {
        assert_eq!(Some(5), position_first_character_enter_basement("()())"));
    }

    #[test]
    pub fn should_read_input_2() {
        let file_name = "resources/inputs/day1/input1.txt";
        aoc_core::add_file_to_binary(file_name);
        match aoc_core::read_file(file_name) {
            Ok(content) => {
                assert_eq!(Some(1783), position_first_character_enter_basement(&content));
            }
            Err(e) => println!("{}", e),
        }
    }

}
