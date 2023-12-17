# 3160 Toy Language Project

The following is the toy language for the 3160 Project.
> The language does basic operations (addition, subtraction, and multiplication) with variables. Variables must be declared and assigned a value before being used.

# Grammar Rules
`Program`: Assignment* \
`Assignment`: Identifier = Exp; \
`Exp`: Exp + Term | Exp - Term | Term \
`Term`:	Term * Fact  | Fact \
`Fact`: ( Exp ) | - Fact | + Fact | Literal | Identifier \
`Identifier`: Letter [Letter | Digit]* \
`Letter`: a|...|z|A|...|Z|_ \
`Literal`: 0 | NonZeroDigit Digit* \
`NonZeroDigit`:	1|...|9 \
`Digit`: 0|1|...|9

## My Notes

I decided to implement this toy  language in multiple (currently only two) languages without using the languages built in functions (unless otherwise specified).

## Languages Implemented In
- Java 
---
**NOTE**

The Java implementation uses the built in ArrayList, and HashMap. I will come back and reimplement those.

---
- Haskell
