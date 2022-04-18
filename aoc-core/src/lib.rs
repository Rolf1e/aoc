use std::fmt::Display;
use std::fs;
use std::path::PathBuf;

pub mod parsers;

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

pub fn read_file(file_name: &str) -> Result<String, AocException> {
    match fs::read_to_string(file_name) {
        Ok(content) => Ok(content),
        Err(e) => Err(AocException::ReadFile(file_name.to_string(), e)),
    }
}

pub enum AocException {
    ReadFile(String, std::io::Error),
}

impl Display for AocException {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            AocException::ReadFile(file_name, e) => {
                write!(f, "Failed to read file {}.\n {}", file_name, e)
            }
        }
    }
}
