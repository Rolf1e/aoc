pub mod day_five;
pub mod day_four;
pub mod day_one;
pub mod day_three;
pub mod day_two;

pub fn split_input(input: &str) -> Vec<String> {
    input.split('\n').map(|s| s.to_string()).collect()
}
