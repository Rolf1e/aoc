/// Part one:
pub fn present_per_house(input: &str) -> i32 {
    let mut house_count = 1;
    let mut santa_position = Position::new();
    let mut delivered_houses = vec![santa_position.clone()];
    dbg!("First Santa position: {}", &santa_position);

    for symbol in input.chars() {
        if symbol == '\n' {
            continue;
        }

        santa_position.compute_from_direction(CardinalPoint::from(symbol));
        dbg!("Santa position: {}", &santa_position);
        if !delivered_houses.contains(&santa_position) {
            delivered_houses.push(santa_position.clone());
            house_count += 1;
        }
    }

    house_count
}

enum CardinalPoint {
    NORTH,
    EAST,
    SOUTH,
    WEST,
}

/// y
/// ^
/// |
/// |
/// |
/// |_______> x
#[derive(PartialEq, Clone, Debug)]
struct Position {
    x: i16,
    y: i16,
}

impl Position {
    fn new() -> Self {
        Position { x: 0, y: 0 }
    }

    fn compute_from_direction(&mut self, direction: CardinalPoint) {
        match direction {
            CardinalPoint::NORTH => self.y += 1,
            CardinalPoint::EAST => self.x += 1,
            CardinalPoint::SOUTH => self.y -= 1,
            CardinalPoint::WEST => self.x -= 1,
        }
    }
}

#[cfg(test)]
pub mod test_2015_day_three {
    // === Part one ===

    use crate::day_three::present_per_house;

    #[test]
    pub fn should_be_2() {
        assert_eq!(2, present_per_house(">"));
        assert_eq!(2, present_per_house("^v^v^v^v^v"));
    }

    #[test]
    pub fn should_be_4() {
        assert_eq!(4, present_per_house("^>v<"));
    }

    #[test]
    pub fn should_read_input_1() {
        let file_name = "inputs/day3/input1.txt";
        aoc_core::add_file_to_binary(file_name);
        match aoc_core::read_file(file_name) {
            Ok(content) => {
                assert_eq!(2081, present_per_house(&content));
            }
            Err(e) => println!("{}", e),
        }
    }
}

impl From<char> for CardinalPoint {
    fn from(c: char) -> Self {
        match c {
            '^' => Self::NORTH,
            '>' => Self::EAST,
            'v' => Self::SOUTH,
            '<' => Self::WEST,
            w => panic!("Illegal cardinal point !: ({})", w),
        }
    }
}
