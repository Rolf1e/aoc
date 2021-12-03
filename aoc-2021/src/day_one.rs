use aoc_core::read_aoc_input;

fn count_larger(measurements: Vec<i32>) -> i16 {
    let mut greater_than_previous = 0;

    let size = measurements.len();

    if size < 2 {
        panic!("Must have 2 numbers at least")
    }

    let mut first = 0;
    let mut second = 1;

    loop {
        if second == size {
            return greater_than_previous;
        }

        if measurements[second] > measurements[first] {
            greater_than_previous += 1;
        }
        first += 1;
        second += 1;
    }
}

fn parse_file(file_name: &str) -> Vec<i32> {
    match read_aoc_input(file_name) {
        Ok(content) => content
            .iter()
            .filter(|s| !s.is_empty())
            .map(|s| string_to_i32(s))
            .collect(),
        Err(e) => {
            panic!("{}", e.message())
        }
    }
}

fn string_to_i32(s: &String) -> i32 {
    match s.parse::<i32>() {
        Ok(i) => i,
        Err(e) => panic!("{}", e),
    }
}

/// https://adventofcode.com/2021/day/1
#[cfg(test)]
mod tests {
    use crate::day_one::{count_larger, parse_file};
    use std::path::PathBuf;

    // Part one
    #[test]
    fn should_have_seven_larger() {
        let result = count_larger(vec![199, 200, 208, 210, 200, 207, 240, 269, 260, 263]);
        assert_eq!(7, result)
    }

    #[test]
    fn should_make_first_input() {
        let file_name = "inputs/2021/day1/input1.txt";
        let mut dir = PathBuf::from(env!("CARGO_MANIFEST_DIR"));
        dir.push(file_name);

        let measurements = parse_file(file_name);
        assert_eq!(0, count_larger(measurements));
    }
}
