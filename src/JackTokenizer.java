
/**
 * @author  Raul Aguilar
 * @date    09 November 2019
 * JackTokenizer: Removes all comments and white space from the input stream
 * and breaks it into Jack-language tokens, as specified by the Jack grammar.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

// TODO: write clean line method

public class JackTokenizer {
    //private static String VALID_SYMBOLS = "{}()[].,;+-*/&|<>=~";

    private Scanner inputFile;
    PrintWriter outputFile = null;
    private String rawInput;
    private String cleanInput;
    private int lineNumber;
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
    public void tokenizer(String fileName) {
        try {
            inputFile = new Scanner(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found. Exiting program.");
            System.exit(0);
        }
    }

    /**
     * Opens output file and gets ready to write to it
     * @param fileName  Name of the output file
     */
    public void xmlWriter(String fileName) {
        try {
            outputFile = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Could not open output file " + fileName);
            System.err.println("Run program again, make sure you have write permissions, etc.");
            System.err.println("Program exiting.");
            System.exit(0);
        }
    }

    //@Incomplete: Write javadoc
    public void printElement(String tag, String value) {
        outputFile.print("<"+tag+"> ");
        outputFile.print(value);
        outputFile.println(" </"+tag+">");
    }

    /**
     * Closes the output file stream
     */
    public void close() {
        outputFile.close();
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
        //  if current line is empty
        //      read new line from file into rawline, increase line #
        //      end function if rawline is empty (no point in parsing)
        lineNumber++;
        rawInput = inputFile.nextLine();
        if(!rawInput.isEmpty()) {
            //      remove comments from line and white space
            cleanLine();
            //      end function if currentline is empty (no point in parsing)
            if(!cleanInput.isEmpty()) {
                // call parse method (should parse based off current line)
                atoms = cleanInput.split(" ");
                outputFile.println(cleanInput);
                for(Keyword key: Keyword.values()) {
                    if(getCleanLine().startsWith(key.name().toLowerCase())) {
                        outputFile.println(key.name());
                    }
                }
            }
        }
    }

    private void parseToken() {
        
    }

    /**
     * Strips the current line from the file of comments and white space.
     */
    private void cleanLine() {
        int commentIndex = rawInput.indexOf("//");
        if(commentIndex > -1) {
            cleanInput = rawInput.substring(0, commentIndex);
        } else {
            commentIndex = rawInput.indexOf("/*");
            if(commentIndex > -1) {
                cleanInput = rawInput.substring(0, commentIndex);
            } else {
                cleanInput = rawInput;
            }
        }
        cleanInput = cleanInput.trim();
    }

    /* GETTERS */

    public String getCleanLine() {
        return cleanInput;
    }

    /**
     * Returns the line number of the current line
     * @return  Current line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

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