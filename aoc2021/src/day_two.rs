use aoc_core::read_aoc_input;

struct Command(String, i8);

fn compute_horizontal_position_and_depth(commands: Vec<Command>) -> (i16, i16) {
    let mut horizontal_position: i16 = 0;
    let mut depth: i16 = 0;

    for Command(direction, length) in commands {
        if direction == "forward" {
            horizontal_position += length as i16;
        }
        // It's a submarine, up and down are inverted
        else if direction == "up" {
            depth -= length as i16;
        } else if direction == "down" {
            depth += length as i16;
        }
    }

    (horizontal_position, depth)
}

fn compute_horizontal_position_and_depth_with_aim(commands: Vec<Command>) -> (i32, i32) {
    let mut horizontal_position: i32 = 0;
    let mut depth: i32 = 0;
    let mut aim: i32 = 0;

    for Command(direction, length) in commands {
        if direction == "forward" {
            horizontal_position += length as i32;
            depth += aim * length as i32;
        }
        // It's a submarine, up and down are inverted
        else if direction == "up" {
            aim -= length as i32;
        } else if direction == "down" {
            aim += length as i32;
        }
    }

    (horizontal_position, depth)
}

fn parse_file(file_name: &str) -> Vec<Command> {
    match read_aoc_input(file_name) {
        Ok(content) => content
            .into_iter()
            .filter(|s| !s.is_empty())
            .map(|s| parse_row(&s))
            .collect(),
        Err(e) => {
            panic!("{}", e)
        }
    }
}

fn parse_row(row: &String) -> Command {
    let mut iter = row.split_whitespace();
    if let Some(direction) = iter.next() {
        if let Some(length) = iter.next() {
            Command(
                String::from(direction),
                aoc_core::parsers::unique::string_to_i32(&String::from(length)) as i8,
            )
        } else {
            panic!("Failed to parse length from row {}", row);
        }
    } else {
        panic!("Failed to parse direction from row {}", row);
    }
}

/// https://adventofcode.com/2021/day/2
#[cfg(test)]
mod tests {
    use aoc_core;

    use crate::day_two::{
        compute_horizontal_position_and_depth, compute_horizontal_position_and_depth_with_aim,
        parse_file, Command,
    };

    // Part one
    #[test]
    fn should_be_150() {
        let commands = vec![
            Command(String::from("forward"), 5),
            Command(String::from("down"), 5),
            Command(String::from("forward"), 8),
            Command(String::from("up"), 3),
            Command(String::from("down"), 8),
            Command(String::from("forward"), 2),
        ];

        let (horizontal_position, depth) = compute_horizontal_position_and_depth(commands);
        assert_eq!(150, horizontal_position * depth);
    }

    #[test]
    fn should_make_first_input() {
        let file_name = "resources/inputs/day2/input1.txt";
        aoc_core::add_file_to_binary(file_name);

        let commands = parse_file(file_name);
        let (horizontal_position, depth) = compute_horizontal_position_and_depth(commands);
        assert_eq!(2070300, horizontal_position as i32 * depth as i32);
    }

    // Part two
    #[test]
    fn should_be_900() {
        let commands = vec![
            Command(String::from("forward"), 5),
            Command(String::from("down"), 5),
            Command(String::from("forward"), 8),
            Command(String::from("up"), 3),
            Command(String::from("down"), 8),
            Command(String::from("forward"), 2),
        ];

        let (horizontal_position, depth) = compute_horizontal_position_and_depth_with_aim(commands);
        assert_eq!(900, horizontal_position as i32 * depth as i32);
    }

    #[test]
    fn should_make_second_input() {
        let file_name = "resources/inputs/day2/input1.txt";
        aoc_core::add_file_to_binary(file_name);

        let commands = parse_file(file_name);
        let (horizontal_position, depth) = compute_horizontal_position_and_depth_with_aim(commands);
        assert_eq!(2078985210, horizontal_position as i64 * depth as i64);
    }
}
