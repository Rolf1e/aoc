use crate::parsers::unique;

pub fn parse_file_to_i8(file_name: &str) -> Vec<i8> {
    parse_file(file_name, |s| unique::char_to_i8(&s.chars().next().unwrap()))
}

pub fn parse_file_to_i32(file_name: &str) -> Vec<i32> {
    parse_file(file_name, |s| unique::string_to_i32(&s))
}

fn parse_file<F, T>(file_name: &str, f: F) -> Vec<T>
where
    F: Fn(&String) -> T,
{
    match crate::read_aoc_input(file_name) {
        Ok(content) => content
            .iter()
            .filter(|s| !s.is_empty())
            .map(|s| f(s))
            .collect(),
        Err(e) => {
            panic!("{}", e)
        }
    }
}
