use std::fs;

pub fn read_aoc_input(file_name: &str) -> Result<Vec<String>, AocException> {
    let content = read_file(file_name)?
        .split('\n')
        .map(|s| s.to_string())
        .collect();
    Ok(content)
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
