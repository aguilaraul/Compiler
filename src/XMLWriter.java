/**
 * XMLWriter.java
 * Author:  Raul Aguilar
 * Date:    November 20, 2020
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class XMLWriter {
    private JackTokenizer jackTokenizer;
	private PrintWriter outputFile = null;

    /**
     * Opens the output file and gets ready to write into it
     * @param fileName  Name of the output file
     */
    private void xmlWriter(String fileName) {
        try {
            outputFile = new PrintWriter(new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open output file " + fileName);
            System.err.println("Run program again, make sure you have write permissions, etc.");
            System.err.println("Program exiting.");
            System.exit(0);
        }
    }

    /**
     * Sets the name of the output XML file and gets it ready to write
     * @param fn Name of the input file name that will become the name
     *                 of the output file
     */
    public void setFileName(String fn) {
        String fileName = fn.substring(0, fn.lastIndexOf('.')) + ".xml";
        xmlWriter(fileName);
    }

    /**
     * Passes the JackTokenizer in order to access the current token
     * @param jt
     */
    public void setTokenizer(JackTokenizer jt) {
        jackTokenizer = jt;
    }

    /**
     * Closes the output XML file stream
     */
    public void close() {
        outputFile.close();
    }

    public void writeTag(Boolean start, String tag) {
        if(start) {
            outputFile.println("<"+ tag + ">");
        } else {
            outputFile.println("</"+ tag + ">");
        }
    }

    /**
     * Writes XML tag with appropriate element and value pertaining
     * to the current token
     */
    public void writeTokenTag(TokenType tokenType, String token) {
        if(tokenType == TokenType.STRING_CONST) {
            outputFile.print("\t<stringConstant> ");
            outputFile.print(jackTokenizer.stringVal());
            outputFile.println(" </stringConstant>");
        } else if(tokenType == TokenType.INT_CONST) {
            outputFile.print("\t<integerConstant> ");
            outputFile.print(jackTokenizer.intConst());
            outputFile.println(" </integerConstant>");
        } else if(tokenType == TokenType.SYMBOL) {
            outputFile.print("\t<symbol> ");
            outputFile.print(jackTokenizer.symbol());
            outputFile.println(" </symbol>");
        } else {
            outputFile.print("\t<"+tokenType.name().toLowerCase()+"> ");
            outputFile.print(token);
            outputFile.println(" </"+tokenType.name().toLowerCase()+">");
        }
    }

    public void writeStringConstTag(String stringVal) {
        outputFile.print("\t<stringConstant> ");
        outputFile.print(stringVal);
        outputFile.println(" </stringConstant>");
    }
}