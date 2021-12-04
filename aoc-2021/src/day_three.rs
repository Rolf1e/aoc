fn compute_reports(reports: Vec<Vec<i8>>) -> i64 {
    let reports: Vec<Vec<bool>> = reports.into_iter().map(|row| transform_row(row)).collect();

    let size = reports[0].len();

    let common_bits: Vec<bool> = (0..size)
        .into_iter()
        .map(|col_index| sum_column(&extract_column(&reports, col_index)))
        .collect();

    let gamma_rate: i32 = common_bits
        .iter()
        .rev()
        .enumerate()
        .map(|(pow, bit)| if *bit { 2_i32.pow(pow as u32) } else { 0 })
        .sum();

    let epsilon_rate: i32 = common_bits
        .iter()
        .rev()
        .enumerate()
        .map(|(pow, bit)| if *bit { 0 } else { 2_i32.pow(pow as u32) })
        .sum();

    println!("{:?} | {} - {}", common_bits, gamma_rate, epsilon_rate);
    (gamma_rate * epsilon_rate) as i64
}

fn sum_column(column: &Vec<bool>) -> bool {
    let mut zeros = 0;
    let mut ones = 0;
    for cell in column {
        if *cell {
            ones += 1;
        } else {
            zeros += 1;
        }
    }
    zeros < ones
}

fn extract_column(reports: &Vec<Vec<bool>>, index: usize) -> Vec<bool> {
    reports.iter().map(|row| row[index]).collect()
}

fn transform_row(row: Vec<i8>) -> Vec<bool> {
    row.into_iter()
        .map(|n| {
            if n == 1 {
                true
            } else if n == 0 {
                false
            } else {
                panic!("Should only be 0 or 1")
            }
        })
        .collect()
}

fn parse_file(file_name: &str) -> Vec<Vec<i8>> {
    match aoc_core::read_aoc_input(file_name) {
        Ok(content) => content
            .iter()
            .filter(|s| !s.is_empty())
            .map(|row| parse_row(row))
            .collect(),
        Err(e) => {
            panic!("{}", e.message())
        }
    }
}

fn parse_row(row: &String) -> Vec<i8> {
    row.chars().map(|c| aoc_core::string_to_i8(&c)).collect()
}

/// https://adventofcode.com/2021/day/3
#[cfg(test)]
mod tests {
    use crate::day_three::compute_reports;
    use aoc_core;

    use super::parse_file;

    // Part one
    #[test]
    fn should_be_198() {
        let reports = vec![
            vec![0, 0, 1, 0, 0],
            vec![1, 1, 1, 1, 0],
            vec![1, 0, 1, 1, 0],
            vec![1, 0, 1, 1, 1],
            vec![1, 0, 1, 0, 1],
            vec![0, 1, 1, 1, 1],
            vec![0, 0, 1, 1, 1],
            vec![1, 1, 1, 0, 0],
            vec![1, 0, 0, 0, 0],
            vec![1, 1, 0, 0, 1],
            vec![0, 0, 0, 1, 0],
            vec![0, 1, 0, 1, 0],
        ];

        assert_eq!(198, compute_reports(reports));
    }

    #[test]
    fn should_make_first_input() {
        let file_name = "inputs/day3/input1.txt";
        aoc_core::add_file_to_binary(file_name);

        let reports = parse_file(file_name);
        assert_eq!(198, compute_reports(reports));
    }
}
