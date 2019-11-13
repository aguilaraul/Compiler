
/**
 * @author  Raul Aguilar
 * @date    12 November 2019
 * JackTokenizer: Removes all comments and white space from the input stream
 * and breaks it into Jack-language tokens, as specified by the Jack grammar.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class JackTokenizer {
    private static String VALID_SYMBOLS = "{}()[].,;+-*/&|<>=~";

    private Scanner inputFile;
    PrintWriter outputFile = null;
    private ArrayList<String> tokens = new ArrayList<String>();
    private String[] splitTokens;
    private String rawLine, line = "", cleanLine;
    private int lineNumber;

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
     * Checks if the .jack file has more lines to read. If there are more line to read, then return
     * true; if not, then close Scanner and return false.
     */
    public boolean hasMoreLines() {
        if(inputFile.hasNextLine()) {
            return true;
        } else {
            //inputFile.close();
            return false;
        }
    }
    
    public void readNextLine() {
        lineNumber++;
        rawLine = inputFile.nextLine();

        // Clean line of comments
        line = cleanSingleLineComments(rawLine);
        line = cleanMultiLineComments(line);
        cleanLine = cleanAPIComments(line);

        splitTokens = cleanLine.split(" ");

        // Parse tokens
        for(String s:splitTokens) {
            parseTokens(s);
        }

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
     * Cleans the current line of multiline comments
     * Takes the current line after being stripped of single line comments and strips it of
     * multiline comments
     * @param line  Current line from file
     * @return      Current line without multiline comments
     */
    private String cleanMultiLineComments(String line) {
        String clean = line;
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

        return clean;
    }

    /**
     * Cleans current line of API comments
     * Takes the current line after being stripped of single and multiline comments and strips it
     * of API comments
     * @param line  Current line from file
     * @return      Current line without API comments
     */
    private String cleanAPIComments(String line) {
        String clean = line;
        int start = line.indexOf("/**");
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

        return clean;
    }

    private void parseTokens(String splitToken) {
        if(splitToken.isEmpty()) {
            return;
        }

        if(splitToken.length() == 1) {
            tokens.add(splitToken);
            return;
        }

        //@Incomplete: This is adding the splitToken to the ArrayList more than once for some reason.
        // It probably has something to do with the loop. Double check it

        for(char st:splitToken.toCharArray()) {
            if(VALID_SYMBOLS.indexOf(st) == -1) {
                // did not find symbol
                tokens.add(splitToken);
            } else {
                // found a symbol
                String beforeSymbol = splitToken.substring(0, VALID_SYMBOLS.indexOf(st));
                String theSymbol = "" + splitToken.charAt(VALID_SYMBOLS.indexOf(st));
                String afterSymbol = splitToken.substring(VALID_SYMBOLS.indexOf(st));
                tokens.add(beforeSymbol);
                tokens.add(theSymbol);
                //parseTokens();
            }
        }
    }

    /* GETTERS */

    public String getCleanLine() {
        return cleanLine;
    }

    public void printSplit() {
        for(String s:splitTokens) {
            System.out.println(s);
        }
    }
    
    public void printTokens() {
        for(String t:tokens) {
            System.out.println(t);
        }
    }

    /**
     * Returns the line number of the current line
     * @return  Current line number
     */
    public int getLineNumber() {
        return lineNumber;
    }
}