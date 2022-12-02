// https://adventofcode.com/2015/day/4

use md5::{Digest, Md5};
use regex::Regex;

/// Part one:
/// input + numbers => to md5
/// find first result starting by fives zeros
pub fn find_md5_with_five_zeroes(input: &str) -> i32 {
    let regex =
        Regex::new(r"^0{5,}.*").unwrap_or_else(|e| panic!("Failed to compile regex. error {}", e));
    find_md5_with_regex(input, regex)
}

/// Part two:
pub fn find_md5_with_six_zeroes(input: &str) -> i32 {
    let regex =
        Regex::new(r"^0{6,}.*").unwrap_or_else(|e| panic!("Failed to compile regex. error {}", e));
    find_md5_with_regex(input, regex)
}

pub fn find_md5_with_regex(input: &str, regex: Regex) -> i32 {
    let mut payload = 0;
    loop {
        let current_attempt = format!("{}{}", input, payload.to_string());
        let md5_attempt = format!("{:x}", Md5::digest(current_attempt));
        println!("try {}: {}", payload, md5_attempt);
        if regex.is_match(&md5_attempt) {
            return payload;
        } else {
            payload += 1;
        }
    }
}

#[cfg(test)]
pub mod test_2015_day_four {
    use crate::day_four::{find_md5_with_five_zeroes, find_md5_with_six_zeroes};

    // === Part one ===
    // #[test]
    pub fn should_be_609043() {
        assert_eq!(609043, find_md5_with_five_zeroes("abcdef"));
    }

    // #[test]
    pub fn should_be_1048970() {
        assert_eq!(1048970, find_md5_with_five_zeroes("pqrstuv"));
    }

    // #[test]
    pub fn should_treat_input_1() {
        assert_eq!(346386, find_md5_with_five_zeroes("iwrupvqb"));
    }

    // === Part two ===
    // #[test]
    pub fn should_treat_input_2() {
        assert_eq!(9958218, find_md5_with_six_zeroes("iwrupvqb"));
    }
}
