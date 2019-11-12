
/**
 * @author  Raul Aguilar
 * @date    12 November 2019
 * JackTokenizer: Removes all comments and white space from the input stream
 * and breaks it into Jack-language tokens, as specified by the Jack grammar.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class JackTokenizer {

    private Scanner inputFile;
    PrintWriter outputFile = null;
    private String rawInput;
    private String cleanInput ="";
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
            inputFile.close();
            return false;
        }
    }

    public void advance() {
        // if current line starts with a comment then skip it
        // otherwise add it to the single string of Jack code
        lineNumber++;
        rawInput = inputFile.nextLine();
        if(!rawInput.startsWith("//")) {
            cleanInput += cleanSingleLineComments(rawInput);
        }
    }

    /**
     * Cleans the line of Jack code of single line comments as it is read from the file
     * @param line  The current line of Jack code from file
     * @return      Current line of Jack code stripped of single line comments
     */
    private String cleanSingleLineComments(String line) {
        String clean = line;
        int commentIndex = line.indexOf("//");
        if(commentIndex > 0) {
            clean = line.substring(0, commentIndex);
        }

        clean = clean.trim();
        return clean;
    }

    /**
     * Recursively cleans the Jack code of multiline comments
     * @param line  The line of Jack code
     * @return      Jack code stripped of multiline comments
     */
    public String cleanMultiLineComments(String line) {
        String clean = line;
        int first = line.indexOf("/*");
        int last = line.indexOf("*/");
        if(first < 0 && last < 0) {
            return clean;
        }
        clean = line.substring(0, first) + line.substring(last+2);
        clean = cleanMultiLineComments(clean);
        return clean;
    }

    /**
     * Recursively cleans the Jack code of Javadoc comments
     * @param line  The line of Jack code
     * @return      Jack code stripped of Javadoc comments
     */
    public String cleanJavaDoc(String line) {
        String clean = line;
        int first = line.indexOf("/**");
        int last = line.indexOf("*/");
        if(first < 0 && last < 0) {
            return clean;
        }
        clean = line.substring(0, first) + line.substring(last+2);
        clean = cleanJavaDoc(clean);
        return clean;
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
}