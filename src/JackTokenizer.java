
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
            inputFile.close();
            return false;
        }
    }

    public void readNextLine() {
        lineNumber++;
        rawLine = inputFile.nextLine();
        line = cleanSingleLineComments(rawLine).trim();
        line = cleanMultiLineComments(line);
        line = cleanJavaDoc(line);

        if(!line.isEmpty() || line != null) {
            cleanLine = line.trim();
        }
        
        
    }

    private String cleanSingleLineComments(String line) {
        int commentIndex = line.indexOf("//");
        if(commentIndex >= 0) {
            return line.substring(0, commentIndex);
        }

        return line;
    }

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

    private String cleanJavaDoc(String line) {
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


    /* GETTERS */

    public String getCleanLine() {
        return cleanLine;
    }

    /**
     * Returns the line number of the current line
     * @return  Current line number
     */
    public int getLineNumber() {
        return lineNumber;
    }
}