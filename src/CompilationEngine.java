/**
 * CompilationEngine.java
 * Author:  Raul Aguilar
 * Date:    November 20, 2020
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class CompilationEngine {
    private XMLWriter xmlWriter = new XMLWriter();
    private Scanner inputFile;
    private TokenType tokenType;
    private String line;

    public void compilationEngine(String fileName) {
        try {
            inputFile = new Scanner(new FileReader(fileName.substring(0, fileName.lastIndexOf('.'))+"T.xml"));
            xmlWriter.setFileName(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found. Exiting program.");
            System.exit(0);
        }
    }

    public void close() {
        xmlWriter.close();
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
        System.out.println("ADVANCE: " + tokenType);
        if(tokenType == TokenType.KEYWORD) {
            if(line.contains("class")) {
                compileClass();
            }
        }
    }

    private void parseNextLine() {
        line = inputFile.nextLine();
        System.out.println(line);
        tokenType = parseTokenType(line);
    }

    private TokenType parseTokenType(String line) {
        if(line.contains("keyword")) {
            return TokenType.KEYWORD;
        } else if (line.contains("symbol")) {
            return TokenType.SYMBOL;
        } else if (line.contains("integerConstant")) {
            return TokenType.INT_CONST;
        } else if (line.contains("stringConstant")) {
            return TokenType.STRING_CONST;
        } else {
            return TokenType.IDENTIFIER;
        }
    }

    public void compileStatements(String token) {
        //if(token.equals("do")) {
            xmlWriter.writeStringConstTag("Coming from the Compilation Engine!");
        //}
    }

    private void compileClass() {
        xmlWriter.writeTag(true, "class");
    }
}
