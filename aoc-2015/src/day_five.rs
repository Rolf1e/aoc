// https://adventofcode.com/2015/day/5
use itertools::Itertools;

#[derive(Debug, PartialEq)]
pub enum StringStatus {
    Nice,
    NaugthyVowels,
    NaugthyExcluded,
    NaugthyDoubleLetter,
}

pub fn find_string_status(input: &str) -> StringStatus {
    if check_for_excluded_strings(input) {
        StringStatus::NaugthyExcluded
    } else if check_for_three_vowels(input) {
        StringStatus::NaugthyVowels
    } else if check_for_double_letter(input) {
        StringStatus::NaugthyDoubleLetter
    } else {
        StringStatus::Nice
    }
}

fn check_for_double_letter(input: &str) -> bool {
    let chars = input.chars().collect_vec();
    let mut last_char: char = chars[0];
    let mut occurence = 1;
    for i in 1..chars.len() {
        let c = chars[i];
        if last_char != c {
            last_char = c;
            occurence = 1;
        } else {
            occurence += 1;
        }

        if occurence == 2 {
            return false;
        }
    }
    return true;
}

#[test]
pub fn should_be_ok_for_double_letters() {
    assert!(!check_for_double_letter("aabbccdd"));
    assert!(!check_for_double_letter("abcdde"));
}

fn check_for_three_vowels(input: &str) -> bool {
    input.chars().filter(is_vowels).count() < 3
}

fn is_vowels(c: &char) -> bool {
    match c {
        'a' | 'e' | 'i' | 'o' | 'u' => true,
        _ => false,
    }
}

fn check_for_excluded_strings(input: &str) -> bool {
    input.contains("ab")
        || input.contains("cd")
        || input.contains("pq")
        || input.contains("xy")
}

#[cfg(test)]
pub mod test_2015_day_five {
    use crate::day_five::{find_string_status, StringStatus};
    use crate::split_input;

    // === Part one ===
    #[test]
    pub fn should_be_nice() {
        assert_eq!(StringStatus::Nice, find_string_status("ugknbfddgicrmopn"));
        assert_eq!(StringStatus::Nice, find_string_status("aaa"));
    }

    #[test]
    pub fn should_be_exclude_some_strings() {
        assert_eq!(
            StringStatus::NaugthyExcluded,
            find_string_status("haegwjzuvuyypxyu")
        );
    }

    #[test]
    pub fn should_miss_three_vowels() {
        assert_eq!(
            StringStatus::NaugthyVowels,
            find_string_status("dvszwmarrgswjxmb")
        );
    }

    #[test]
    pub fn should_miss_double_letter() {
        assert_eq!(
            StringStatus::NaugthyDoubleLetter,
            find_string_status("jchzalrnumimnmhp")
        );

    }

    #[test]
    pub fn should_treat_input_1() {
        let file_name = "inputs/day5/input1.txt";
        aoc_core::add_file_to_binary(file_name);
        match aoc_core::read_file(file_name) {
            Ok(content) => {
                let content: Vec<_> = split_input(&content)
                    .iter()
                    .filter(|row| !row.is_empty())
                    .map(|row| find_string_status(row))
                    .filter(|status| &StringStatus::Nice == status)
                    .collect();

                dbg!(&content.len());
                assert_eq!(255, content.len());
            }
            Err(e) => println!("{}", e),
        }
    }
}
