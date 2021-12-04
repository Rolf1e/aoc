use std::fs;
use std::path::PathBuf;

pub fn add_file_to_binary(file_name: &str) {
    let mut dir = PathBuf::from(env!("CARGO_MANIFEST_DIR"));
    dir.push(file_name);
}

pub fn read_aoc_input(file_name: &str) -> Result<Vec<String>, AocException> {
    let content = read_file(file_name)?
        .split('\n')
        .map(|s| s.to_string())
        .collect();
    Ok(content)
}

pub fn string_to_i32(s: &String) -> i32 {
    match s.parse::<i32>() {
        Ok(i) => i,
        Err(e) => panic!("{}", e),
    }
}


fn read_file(file_name: &str) -> Result<String, AocException> {
    match fs::read_to_string(file_name) {
        Ok(content) => Ok(content),
        Err(e) => Err(AocException::ReadFile(file_name.to_string(), e)),
    }
}

pub enum AocException {
    ReadFile(String, std::io::Error),
}

impl AocException {
    pub fn message(&self) -> String {
        match self {
            AocException::ReadFile(file_name, e) => {
                format!("Failed to read file {}.\n {}", file_name, e)
            }
        }
    }
}
