/*
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
    private ArrayList<String> tokens = new ArrayList<>();
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
    
    public void readNextLine() {
        lineNumber++;
        rawLine = inputFile.nextLine();

        // Clean line of comments
        line = cleanSingleLineComments(rawLine);
        cleanLine = cleanMultiLineComments(line);
        // TODO: Check if current line has a string by checking for quotation marks
        // because we need to keep those together as one full line before splitting
        // up the whole line into tokens

        //cleanLine = "do Output.printString(\"Hello world!\");";

        splitTokens = cleanLine.split(" ");

        String _1 = "do";

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

    private void parseTokens(String splitToken) {
        boolean foundSymbol = false;
        int symbolIndex = 0;

        /*if (splitToken.isEmpty()) {
            tokens.add("");
        }*/

        if(splitToken.length() == 1) {
            tokens.add(splitToken);
        }
        if(splitToken.length() > 1) {
            for(int i = 0; i < splitToken.length(); i++) {
                if(VALID_SYMBOLS.contains(""+splitToken.charAt(i))) {
                    // current character is a symbol
                    symbolIndex = i;
                    foundSymbol = true;
                    break;
                }
            }

            if(foundSymbol) {
                tokens.add(splitToken.substring(0, symbolIndex)); // add before the symbol
                tokens.add(splitToken.charAt(symbolIndex)+""); // add the symbol
                parseTokens(splitToken.substring(symbolIndex+1));
            } else {
                tokens.add(splitToken);
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