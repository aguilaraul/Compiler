/**
 * JackTokenizer.java
 * Author:  Raul Aguilar
 * Date:    November 20, 2020
 * JackTokenizer: Removes all comments and white space from the input stream
 * and breaks it into Jack-language tokens, as specified by the Jack grammar.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class JackTokenizer {
    private static String VALID_SYMBOLS = "{}()[].,;+-*/&|<>=~";
    private static String VALID_IDENTIFIER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.$:";

    private XMLWriter xmlWriter = new XMLWriter();
    private Scanner inputFile;
    private KeywordTable keywords = new KeywordTable();
    private ArrayList<String> tokens = new ArrayList<>();
    private String cleanLine;
    private int lineNumber;
    private String token;
    private TokenType tokenType;
    private int keyword;
    private String symbol;
    private int intConst;
    private String stringVal;

    /**
     * Opens the input .jack file and gets ready to tokenize it
     * @param fileName Name of the vm file
     */
    public void tokenizer(String fileName) {
        try {
            inputFile = new Scanner(new FileReader(fileName));
            xmlWriter.setFileName(fileName.substring(0, fileName.lastIndexOf('.'))+"T.jack");
            xmlWriter.setTokenizer(this);
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found. Exiting program.");
            System.exit(0);
        }
    }

    /**
     * Checks if the .jack file has more lines to read.
     * If there are more line to read, then return true;
     * If not, then close Scanner and return false.
     */
    public boolean hasMoreLines() {
        if(inputFile.hasNextLine()) {
            return true;
        } else {
            inputFile.close();
            return false;
        }
    }

    /**
     * Reads the next line from the Jack file, cleans it of comments, and parses
     * it into tokens
     */
    public void parseNextLine() {
        lineNumber++;
        String rawLine = inputFile.nextLine();
        String line = cleanSingleLineComments(rawLine);
        cleanLine = cleanMultiLineComments(line);
        parseTokens(cleanLine);
    }

    /**
     * Takes in the current line and strips it of single line comments and trims it
     * @param line  Current line from file
     * @return      Current line without single line comments
     */
    private String cleanSingleLineComments(String line) {
        int commentIndex = line.indexOf("//");
        if(commentIndex >= 0) {
            return line.substring(0, commentIndex).trim();
        }

        return line.trim();
    }

    /**
     * Cleans the current line of multiline comments and API comments
     * Takes the current line after being stripped of single line comments and
     * strips it of multiline comments including API comments
     * @param line  Current line from file
     * @return      Current line without multiline comments
     */
    private String cleanMultiLineComments(String line) {
        int start = line.indexOf("/*");
        int end = line.indexOf("*/");

        if(start >= 0) {
            return line.substring(0, start);
        }
        if(line.startsWith("*")) {
            return "";
        }
        if(end >= 0) {
            return line.substring(end+2);
        }

        return line;
    }

    /**
     * Recursively parses the current line into tokens split up by symbols.
     * Adding each token from the line into an ArrayList containing all of
     * tokens parsed from the file.
     * PRECONDITION: Current line from Jack file is clean of comments
     * @param line  The current line from the Jack file
     */
    private void parseTokens(String line) {
        boolean foundSymbol = false;
        int symbolIndex = 0;

        if(line.length() == 1) {
            tokens.add(line.trim());
        }
        if(line.length() > 1) {
            for(int i = 0; i < line.length(); i++) {
                if(VALID_SYMBOLS.contains(""+line.charAt(i))) {
                    // current character is a symbol
                    symbolIndex = i;
                    foundSymbol = true;
                    break;
                }
            }

            if(foundSymbol) {
                if(symbolIndex != 0) {
                    // add before the symbol if it doesn't have quotation marks
                    // split it up first
                    if(line.charAt(0) != '"') {
                        tokens.addAll(Arrays.asList(line.substring(0, symbolIndex).trim().split(" ")));
                    } else {
                        tokens.add(line.substring(0, symbolIndex).trim()); // add before the symbol
                    }
                }
                tokens.add(line.charAt(symbolIndex)+"".trim()); // add the symbol
                parseTokens(line.substring(symbolIndex+1).trim()); // continue to parse after the symbol
            } else {
                tokens.add(line);
            }
        }
    }

    /**
     * Gets the next token from the input, and makes it the current token.
     * This method should be called only if hasMoreTokens is true
     * Initially there is no current token
     */
    public void advance() {
        parseNextLine();
    }

    /**
     * Writes the tokens found in the file into and XML file with the
     * proper tags.
     */
    public void writeToXML() {
        xmlWriter.writeTag(true,"tokens");
        for(String t:tokens) {
            token = t;
            tokenType = tokenType(token);
            xmlWriter.writeTokenTag(tokenType, token);
        }
        xmlWriter.writeTag(false,"tokens");
        xmlWriter.close();
    }

    /**
     * Returns the type of the current token, as a constant
     * @return Enum TokenType
     */
    private TokenType tokenType(String token) {
        try {
            intConst = Integer.parseInt(token);
            return TokenType.INT_CONST;
        } catch (NumberFormatException notAnInteger) {
            keywords.KeywordTable();
            if(token.charAt(0) == '"') {
                stringVal = token.substring(1, token.length()-1);
                return TokenType.STRING_CONST;
            } else if(VALID_SYMBOLS.contains(token)) {
                switch (token) {
                    case "<":
                        symbol = "&lt;";
                        return TokenType.SYMBOL;
                    case ">":
                        symbol = "&gt;";
                        return TokenType.SYMBOL;
                    case "\"":
                        symbol = "&quot;";
                        return TokenType.SYMBOL;
                    case "'":
                        symbol = "&apos;";
                        return TokenType.SYMBOL;
                    case "&":
                        symbol = "&amp;";
                        return TokenType.SYMBOL;
                    default:
                        symbol = token;
                        return TokenType.SYMBOL;
                }
            } else if(keywords.contains(token)) {
                keyword = keywords.getKeyword(token);
                return TokenType.KEYWORD;
            } else {
                return TokenType.IDENTIFIER;
            }
        }
    }

    private boolean isValidIdentifier(String symbol, int lineNumber) {
        boolean isValidName = false;
        for(char c:symbol.toCharArray()) {
            if(VALID_IDENTIFIER.indexOf(c) == -1) {
                System.out.printf("Symbol name is not valid on line %d. Program exiting.", lineNumber);
                isValidName = false;
                System.exit(0);
            } else {
                isValidName = true;
            }
        }

        return isValidName;
    }

    /* GETTERS */

    /**
     * Returns the keyword which is the current token, as a constant.
     * Should be called only if tokenType is KEYWORD.
     * @return  Integer constant of the keyword
     */
    public int keyWord() {
        return keyword;
    }

    /**
     * Returns the character which is the current token as a String.
     * Should be called only if tokenType is SYMBOL.
     * @return  String of the symbol
     */
    public String symbol() {
        return symbol;
    }

    /**
     * Returns the integer value of the current token.
     * Should be called only if tokenType is INT_CONST.
     * @return  Integer constant
     */
    public int intConst() {
        return intConst;
    }

    /**
     * Returns the string value of the current token, without the two
     * enclosing double quotes.
     * Should be called only if tokenType is STRING_CONST.
     * @return  The string inside double quotes
     */
    public String stringVal() {
        return stringVal;
    }

    /**
     * Returns the line number of the current line
     * @return  Current line number
     */
    public int getLineNumber() {
        return lineNumber;
    }
}