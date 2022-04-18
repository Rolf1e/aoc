pub fn string_to_i32(s: &String) -> i32 {
    match s.parse::<i32>() {
        Ok(i) => i,
        Err(e) => panic!("{}", e),
    }
}

pub fn string_to_i8(s: &str) -> i8 {
    match s.parse::<i8>() {
        Ok(i) => i,
        Err(e) => panic!("{}", e),
    }
}

pub fn char_to_i8(c: &char) -> i8 {
    match c.to_digit(2) {
        Some(i) => i as i8,
        None => panic!("Failed to parse {}", c),
    }
}
