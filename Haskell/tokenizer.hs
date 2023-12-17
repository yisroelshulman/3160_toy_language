

-- token types as a sudo enum
data TokenType = LEFT_PARENT | RIGHT_PARENT | EQUAL | PLUS | MINUS | STAR | SEMICOLON
                | IDENTIFIER | LITERAL | ERROR | EOF deriving Show

-- representation of a token
data Token = Token { ttype :: TokenType, lexeme :: String, literal :: Maybe Int } deriving Show

-- tokenizer
tokenize :: String -> [Token]
tokenize [] = [Token { ttype = EOF, lexeme = "", literal = Nothing}]
tokenize s@(h:t) -- s as a head tail split
    | h == '(' = Token {ttype = LEFT_PARENT, lexeme = "(", literal = Nothing} : tokenize t
    | h == ')' = Token {ttype = RIGHT_PARENT, lexeme = ")", literal = Nothing} : tokenize t
    | h == '=' = Token {ttype = EQUAL, lexeme = "=", literal = Nothing}: tokenize t
    | h == '+' = Token {ttype = PLUS, lexeme = "+", literal = Nothing} : tokenize t
    | h == '-' = Token {ttype = MINUS, lexeme = "-", literal = Nothing} : tokenize t
    | h == '*' = Token {ttype = STAR, lexeme = "*", literal = Nothing} : tokenize t
    | h == ';' = Token {ttype = SEMICOLON, lexeme = ";", literal = Nothing} : tokenize t
    | h == ' ' = tokenize t
    | is_digit h = do let (num, rest) = split_digit is_digit s
                      number num : tokenize rest
    | is_letter h = do let (var, rest) = split_string is_alpha_numeric s
                       Token { ttype = IDENTIFIER, lexeme = var, literal = Nothing } : tokenize rest

-- checks if c is a digit
is_digit :: Char -> Bool
is_digit c = c >= '0' && c <= '9'

-- checks if c is a letter
is_letter :: Char -> Bool
is_letter c = (c >= 'a' && c <= 'z') || (c >= 'A' && c >= 'Z') || c == '_'

-- check if c is alphanumeric
is_alpha_numeric :: Char -> Bool
is_alpha_numeric c = is_digit c || is_letter c

-- splits a String into 2 the first part contains the leading digits and the second part is the rest
split_digit :: (Char -> Bool) -> String -> (String, String)
split_digit _ [] = ("", "")
split_digit fn s@(h:t)
    | fn h = let (first, rest) = split_digit fn t in (h:first, rest)
    | otherwise = ("", s)

-- splits a string in 2 the first part is an identifier and the second is the rest
split_string :: (Char -> Bool) -> String -> (String, String)
split_string _ [] = ("", "")
split_string fn s@(h:t)
    | fn h = let (first, rest) = split_string fn t in (h:first, rest)
    | otherwise = ("", s)

-- number tokens (assert that the String only contains digits)
number :: String -> Token
number "0" = Token { ttype = LITERAL, lexeme = "0", literal = Just 0 }
number s@(h:t) -- need a handle to the entire String
    | h == '0' = Token { ttype = ERROR, lexeme = "", literal = Nothing }
    | otherwise = Token { ttype = LITERAL, lexeme = s, literal = Just (str_to_int (my_reverse s)) }

-- reverse a list
my_reverse :: String -> String
my_reverse [] = []
my_reverse (h:t) = my_reverse t ++ [h]

-- convert a string of ints to an int (no error checking)
str_to_int :: String -> Int
str_to_int [x] = char_to_int x
str_to_int (h:t) = char_to_int h + (10 * str_to_int t)

-- convert a char to int (no error checking)
char_to_int :: Char -> Int
char_to_int '0' = 0
char_to_int '1' = 1
char_to_int '2' = 2
char_to_int '3' = 3
char_to_int '4' = 4
char_to_int '5' = 5
char_to_int '6' = 6
char_to_int '7' = 7
char_to_int '8' = 8
char_to_int '9' = 9
