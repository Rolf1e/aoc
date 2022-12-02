use aoc_core;

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

fn count_larger_2(measurements: Vec<i32>) -> i16 {
    let size = measurements.len();
    let mut sumed_by_three = Vec::with_capacity(size);

    let mut first = 0;
    let mut second = 1;
    let mut third = 2;

    loop {
        if third == size {
            return count_larger(sumed_by_three);
        }

        sumed_by_three.push(measurements[first] + measurements[second] + measurements[third]);

        first += 1;
        second += 1;
        third += 1;
    }
}

/// https://adventofcode.com/2021/day/1
#[cfg(test)]
mod tests {
    use crate::day_one::{count_larger, count_larger_2};
    use aoc_core::parsers::vectors;
    use aoc_core;

    // Part one
    #[test]
    fn should_have_seven_larger() {
        let result = count_larger(vec![199, 200, 208, 210, 200, 207, 240, 269, 260, 263]);
        assert_eq!(7, result)
    }

    #[test]
    fn should_make_first_input() {
        let file_name = "resources/inputs/day1/input1.txt";
        aoc_core::add_file_to_binary(file_name);

        let measurements = vectors::parse_file_to_i32(file_name);
        assert_eq!(1167, count_larger(measurements));
    }

    // Part two

    #[test]
    fn should_have_five_larger() {
        let result = count_larger_2(vec![199, 200, 208, 210, 200, 207, 240, 269, 260, 263]);
        assert_eq!(5, result)
    }
    #[test]
    fn should_make_second_input() {
        let file_name = "resources/inputs/day1/input1.txt";
        aoc_core::add_file_to_binary(file_name);

        let measurements = vectors::parse_file_to_i32(file_name);
        assert_eq!(1130, count_larger_2(measurements));
    }
}
