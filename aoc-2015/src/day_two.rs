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

struct PresentBox(AreaUnit, AreaUnit, AreaUnit);

impl PresentBox {
    fn areas(self) -> Vec<AreaUnit> {
        let PresentBox(l, w, h) = self;
        vec![l * w, w * h, h * l]
    }
}

#[cfg(test)]
pub mod test_2015_day_two {
    use crate::day_two::find_square_feet_of_wrapping_paper;

    fn split_input(input: &str) -> Vec<String> {
        input.split('\n').map(|s| s.to_string()).collect()
    }

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
        let file_name = "inputs/day2/input1.txt";
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
}
