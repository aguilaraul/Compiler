/*
 *  @author Raul Aguilar
 *  @date   10 March 2020
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class CompilationEngine {
    private XMLWriter xmlWriter = new XMLWriter();
    private Scanner inputFile;
    private TokenType tokenType;

    public void compilationEngine(String fileName) {
        try {
            inputFile = new Scanner(new FileReader(fileName.substring(0, fileName.lastIndexOf('.'))+"T.xml"));
            xmlWriter.setFileName(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found. Exiting program.");
            System.exit(0);
        }
    }

    /**
     * Checks if the tokenized xml file has more lines to read.
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

    public void advance() {
        parseNextLine();
    }

    private void parseNextLine() {
        String line = inputFile.nextLine();
        if(line.contains("keyword")) {
            tokenType = TokenType.KEYWORD;
            //@TODO Replace this with a method that returns the tokenType
            // of the current line. From there I can decide what to do
            // with it.
        }
    }

    public void compileStatements(String token) {
        //if(token.equals("do")) {
            xmlWriter.writeStringConstTag("Coming from the Compilation Engine!");
        //}

        xmlWriter.close();
    }

    private void compileDo() {

    }
}
