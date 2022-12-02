fn compute_gamma_and_epsilon_rates(reports: Vec<Vec<i8>>) -> i64 {
    let size = reports[0].len();

    let common_bits: Vec<i8> = (0..size)
        .into_iter()
        .map(|col_index| {
            let (z, o) = sum_column(&extract_column(&reports, col_index));
            if z > o {
                0
            } else {
                1
            }
        })
        .collect();

    let gamma_rate: i32 = common_bits
        .iter()
        .rev()
        .enumerate()
        .map(|(pow, bit)| if *bit == 1 { 2_i32.pow(pow as u32) } else { 0 })
        .sum();

    let epsilon_rate: i32 = common_bits
        .iter()
        .rev()
        .enumerate()
        .map(|(pow, bit)| if *bit == 1 { 0 } else { 2_i32.pow(pow as u32) })
        .sum();

    println!("{:?} | {} - {}", common_bits, gamma_rate, epsilon_rate);
    (gamma_rate * epsilon_rate) as i64
}

fn compute_oxygen_and_co2_rates(reports: Vec<Vec<i8>>) -> i64 {
    let oxygen_rate = compute_rate(
        &reports,
        |pow, bit| if bit == 1 { 2_i32.pow(pow as u32) } else { 0 },
        |z, o| {
            if z == o {
                1
            } else if z > o {
                0
            } else {
                1
            }
        },
    );

    let c_o2_rate = compute_rate(
        &reports,
        |pow, bit| if bit == 1 { 2_i32.pow(pow as u32) } else { 0 },
        |z, o| {
            if z == o {
                0
            } else if z < o {
                0
            } else {
                1
            }
        },
        
    );

    println!("{} x {}", oxygen_rate, c_o2_rate);
    (oxygen_rate * c_o2_rate) as i64
}

fn compute_rate<F, F2>(reports: &Vec<Vec<i8>>, mut binary_convertion: F, bit_decision: F2) -> i32
where
    F: FnMut(usize, i8) -> i32,
    F2: Fn(i32, i32) -> i8,
{
    let mut filtered_reports = reports.clone();
    let size = reports[0].len();
    let mut rate: i32 = 0;
    let mut i = 0;
    loop {
        if i == size {
            break;
        }

        let (z, o) = sum_column(&extract_column(&filtered_reports, i));
        let actual_bit = bit_decision(z, o);
        filtered_reports = keep_only_number_starting_by(&filtered_reports, actual_bit, i);

        println!("Bit {} Restants {:?}", actual_bit, filtered_reports);
        i += 1;

        if filtered_reports.len() == 1 {
            println!("Filter {:?}", filtered_reports[0]);
            rate = filtered_reports[0]
                .iter()
                .rev()
                .enumerate()
                .map(|(pow, bit)| binary_convertion(pow, *bit))
                .sum();
            break;
        }
    }

    rate
}

fn keep_only_number_starting_by(
    reports: &Vec<Vec<i8>>,
    number: i8,
    position: usize,
) -> Vec<Vec<i8>> {
    reports
        .to_owned()
        .into_iter()
        .filter(|row| row[position] == number)
        .collect()
}

fn sum_column(column: &Vec<i8>) -> (i32, i32) {
    let mut zeros = 0;
    let mut ones = 0;
    for cell in column {
        if *cell == 1 {
            ones += 1;
        } else if *cell == 0 {
            zeros += 1;
        }
    }
    (zeros, ones)
}

fn extract_column(reports: &Vec<Vec<i8>>, index: usize) -> Vec<i8> {
    reports.iter().map(|row| row[index]).collect()
}


/// https://adventofcode.com/2021/day/3
#[cfg(test)]
mod tests {
    use crate::day_three::{compute_gamma_and_epsilon_rates, compute_oxygen_and_co2_rates};
    use aoc_core;
    use aoc_core::parsers::matrix;

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

        assert_eq!(198, compute_gamma_and_epsilon_rates(reports));
    }

    #[test]
    fn should_make_first_input() {
        let file_name = "resources/inputs/day3/input1.txt";
        aoc_core::add_file_to_binary(file_name);

        let reports = matrix::parse_file_to_matrix_i8(file_name);
        assert_eq!(2640986, compute_gamma_and_epsilon_rates(reports));
    }

    // Part two
    #[test]
    fn should_be_230() {
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

        assert_eq!(230, compute_oxygen_and_co2_rates(reports));
    }

    #[test]
    fn should_make_second_input() {
        let file_name = "resources/inputs/day3/input1.txt";
        aoc_core::add_file_to_binary(file_name);

        let reports = matrix::parse_file_to_matrix_i8(file_name);
        assert_eq!(6822109, compute_oxygen_and_co2_rates(reports));
    }
}
