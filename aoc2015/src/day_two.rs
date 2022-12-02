// https://adventofcode.com/2015/day/2
use aoc_core::parsers::unique;
type AreaUnit = i32;

/// Part one:
/// area = 2*l*w + 2*w*h + 2*h*l
/// return area + area smallest side
pub fn find_square_feet_of_wrapping_paper(input: &str) -> AreaUnit {
    let members: Vec<i32> = input.split('x').map(|c| unique::string_to_i32(c)).collect();

    if 3 != members.len() {
        panic!("Something went wrong when parsing input {}", input);
    }

    let present_box = PresentBox(members[0], members[1], members[2]);
    let mut areas = present_box.areas();
    areas.sort();
    dbg!(&areas);

    let smallest = areas[0];
    dbg!(&smallest);

    2 * areas.iter().sum::<i32>() + smallest
}

pub fn find_feet_of_rubon(input: &str) -> AreaUnit {
    let members: Vec<i32> = input.split('x').map(|c| unique::string_to_i32(c)).collect();
    if 3 != members.len() {
        panic!("Something went wrong when parsing input {}", input);
    }

    let present_box = PresentBox(members[0], members[1], members[2]);
    let two_smallest = present_box.find_two_smallest();
    two_smallest[0] * 2 + two_smallest[1] * 2 + present_box.volume()
}

#[derive(Debug)]
struct PresentBox(AreaUnit, AreaUnit, AreaUnit);

impl PresentBox {
    fn areas(&self) -> Vec<AreaUnit> {
        let PresentBox(l, w, h) = self;
        vec![l * w, w * h, h * l]
    }

    fn find_two_smallest(&self) -> Vec<AreaUnit> {
        let PresentBox(l, w, h) = self;
        let mut sides = vec![l, w, h];
        sides.sort();
        vec![*sides[0], *sides[1]]
    }

    fn volume(&self) -> AreaUnit {
        let PresentBox(l, w, h) = self;
        l * w * h
    }
}

#[cfg(test)]
pub mod test_2015_day_two {
    use crate::day_two::{find_feet_of_rubon, find_square_feet_of_wrapping_paper};
    use crate::split_input;


    // === Part one ===

    #[test]
    pub fn should_be_58() {
        assert_eq!(58, find_square_feet_of_wrapping_paper("2x3x4"));
    }

    #[test]
    pub fn should_be_43() {
        assert_eq!(43, find_square_feet_of_wrapping_paper("1x1x10"));
    }

    #[test]
    pub fn should_do_input_1() {
        let file_name = "resources/inputs/day2/input1.txt";
        aoc_core::add_file_to_binary(file_name);
        match aoc_core::read_file(file_name) {
            Ok(content) => {
                let res = split_input(&content)
                    .iter()
                    .filter(|row| !row.is_empty())
                    .map(|row| find_square_feet_of_wrapping_paper(row))
                    .sum::<i32>();

                assert_eq!(1598415, res);
            }
            Err(e) => println!("{}", e),
        }
    }

    // === Part two ===
    #[test]
    pub fn should_be_34() {
        assert_eq!(34, find_feet_of_rubon("2x3x4"));
    }

    #[test]
    pub fn should_be_14() {
        assert_eq!(14, find_feet_of_rubon("1x1x10"));
    }

    #[test]
    pub fn should_do_input_2() {
        let file_name = "resources/inputs/day2/input1.txt";
        aoc_core::add_file_to_binary(file_name);
        match aoc_core::read_file(file_name) {
            Ok(content) => {
                let res = split_input(&content)
                    .iter()
                    .filter(|row| !row.is_empty())
                    .map(|row| find_feet_of_rubon(row))
                    .sum::<i32>();

                assert_eq!(3812909, res);
            }
            Err(e) => println!("{}", e),
        }
    }
}
