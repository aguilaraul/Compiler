/**
 * @author  Raul Aguilar
 * @date    09 November 2019
 * JackTokenizer: Removes all comments and white space from the input stream
 * and breaks it into Jack-language tokens, as specified by the Jack grammar.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

// TODO: write clean line method

public class JackTokenizer {
    //private static String VALID_SYMBOLS = "{}()[].,;+-*/&|<>=~";

    private Scanner inputFile;
    private String rawInput;
    private String cleanInput;
    private String[] atoms;
    private String atom;
    private TokenType tokenType;
    private Keyword keyword;
    private char symbol;
    private int intValue;
    private String stringValue;
    private String identifier;

    /**
     * Opens the input .jack file and gets ready to tokenize it
     * @param fileName Name of the vm file
     */
    public void JackTokenizer(String fileName) {
        try {
            inputFile = new Scanner(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found. Exiting program.");
            System.exit(0);
        }
    }

    /**
     * Checks if the .jack file has more lines to read. If there are more line to read, then return
     * true; if not, then close Scanner and return false.
     */
    public boolean hasMoreTokens() {
        if(inputFile.hasNextLine()) {
            return true;
        } else {
            inputFile.close();
            return false;
        }
    }

    /**
     * Gets the next token from the input and makes it the current token. This method should be
     * called only if hasMoreTokens() is true. Initially there is no current token.
     */
    public void advance() {
        rawInput = inputFile.nextLine();
        cleanLine();
        atoms = cleanInput.split(" ");
        parseKeyword();
    }

    /**
     * Strips the current line from the file of comments and white space.
     */
    private void cleanLine() {
        int commentIndex = rawInput.indexOf('/');
        if(commentIndex > -1) {
            cleanInput = rawInput.substring(0, commentIndex);
        } else {
            cleanInput = rawInput;
        }
        cleanInput = cleanInput.trim();
    }

    private void parseTokenType() {

    }

    private void parseKeyword() {
        switch (atoms[0]) {
            case "class":
                keyword = Keyword.CLASS;
                break;
            case "constructor":
                keyword = Keyword.CONSTRUCTOR;
                break;

        }
    }

    /* GETTERS */

    /**
     * Returns the current tokenized atom
     * @return  Current tokenized atom
     */
    public String getAtom() {
        return atom;
    }

    /**
     * Returns the type of the current token.
     * @return  The type of the current token
     */
    public TokenType getTokenType() {
        return tokenType;
    }

    /**
     * Returns the keyword which is the current token. Should be called only
     * when tokenType() is KEYWORD.
     * @return  The keyword of the current token
     */
    public Keyword getKeyword() {
        return keyword;
    }

    /**
     * Returns the character which is the current token. Should be called only
     * when tokenType() is SYMBOL.
     * @return  The character tokenized into symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Returns the identifier which is the current token. Should be called only
     * when tokenType() is IDENTIFIER.
     * @return  Identifier of current token
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the integer value of the current token. Should be called only
     * when tokenType() is INT_CONST.
     * @return  Integer of the current token
     */
    public int getIntValue() {
        return intValue;
    }

    /**
     * Returns the string value of the current token, without the double
     * quotes. Should be called only when tokenType() is STRING_CONST. 
     * @return
     */
    public String getStringValue() {
        return stringValue;
    }
}