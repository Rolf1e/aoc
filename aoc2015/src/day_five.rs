// https://adventofcode.com/2015/day/5
use itertools::Itertools;

#[derive(Debug, PartialEq)]
pub enum StringStatus {
    Nice,
    // Rule set one
    NaugthyVowels,
    NaugthyExcluded,
    NaugthyDoubleLetter,
    // Rule set two
    NaugthyNoDoublePair,
    NaugthyNoLetterBetweenPair,
}

// Part one:
pub fn find_string_status(input: &str) -> StringStatus {
    let check_for_three_vowels =
        regex::Regex::new(r"[aeiou].*[aeiou].*[aeiou]").unwrap_or_else(|e| panic!("{}", e));
    if !check_for_three_vowels.is_match(input) {
        return StringStatus::NaugthyVowels;
    }

    let check_for_double_letter =
        fancy_regex::Regex::new(r"(.)(\1)").unwrap_or_else(|e| panic!("{}", e));
    let check_for_double_letter = check_for_double_letter.is_match(input);
    assert!(check_for_double_letter.is_ok());
    if !check_for_double_letter.unwrap() {
        return StringStatus::NaugthyDoubleLetter;
    }

    let check_for_excluded_strings =
        regex::Regex::new(r"(ab|cd|pq|xy)").unwrap_or_else(|e| panic!("{}", e));
    if check_for_excluded_strings.is_match(input) {
        return StringStatus::NaugthyExcluded;
    }
    StringStatus::Nice
}

// Part two:
pub fn find_string_status_with_new_rules(input: &str) -> StringStatus {
    let check_for_letter_appear_two_times =
        fancy_regex::Regex::new(r"(..).*(\1)").unwrap_or_else(|e| panic!("{}", e));
    let check_for_letter_appear_two_times = check_for_letter_appear_two_times.is_match(input);
    assert!(check_for_letter_appear_two_times.is_ok());
    if !check_for_letter_appear_two_times.unwrap() {
        return StringStatus::NaugthyNoDoublePair;
    }

    let check_for_repeating_letter =
        fancy_regex::Regex::new(r"(.).(\1)").unwrap_or_else(|e| panic!("{}", e));
    let check_for_repeating_letter = check_for_repeating_letter.is_match(input);
    assert!(check_for_repeating_letter.is_ok());
    if !check_for_repeating_letter.unwrap() {
        return StringStatus::NaugthyNoLetterBetweenPair;
    }

    StringStatus::Nice
}

#[cfg(test)]
pub mod test_2015_day_five {
    use crate::day_five::{find_string_status, find_string_status_with_new_rules, StringStatus};
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

    // === Part one ===
    #[test]
    pub fn should_treat_input_1() {
        let file_name = "resources/inputs/day5/input1.txt";
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

    // === Part two ===
    #[test]
    pub fn should_have_pair_appearing_twice() {
        assert_eq!(
            StringStatus::NaugthyNoDoublePair,
            find_string_status_with_new_rules("ieodomkazucvgmuy")
        );
    }

    #[test]
    pub fn should_be_nice_2() {
        assert_eq!(
            StringStatus::Nice,
            find_string_status_with_new_rules("qjhvhtzxzqqjkmpb")
        );
        assert_eq!(
            StringStatus::Nice,
            find_string_status_with_new_rules("xxyxx")
        );
    }

    #[test]
    pub fn should_have_repeating_letter_without_one_in_the_middle() {
        assert_eq!(
            StringStatus::NaugthyNoLetterBetweenPair,
            find_string_status_with_new_rules("uurcxstgmygtbstg")
        );
    }

    #[test]
    pub fn should_treat_input_2() {
        let file_name = "resources/inputs/day5/input1.txt";
        aoc_core::add_file_to_binary(file_name);
        match aoc_core::read_file(file_name) {
            Ok(content) => {
                let content: Vec<_> = split_input(&content)
                    .iter()
                    .filter(|row| !row.is_empty())
                    .map(|row| find_string_status_with_new_rules(row))
                    .filter(|status| &StringStatus::Nice == status)
                    .collect();

                dbg!(&content.len());
                assert_eq!(55, content.len());
            }
            Err(e) => println!("{}", e),
        }
    }
}
