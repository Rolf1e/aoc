use crate::parsers::unique;

pub fn parse_file_to_matrix_i8(file_name: &str) -> Vec<Vec<i8>> {
    match crate::read_aoc_input(file_name) {
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
    row.chars().map(|c| unique::char_to_i8(&c)).collect()
}
