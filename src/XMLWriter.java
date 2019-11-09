/**
 * @author  Raul Aguilar
 * @date    09 November 2019
 * VMWriter: Translates Jack language into VM commands
 */
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class XMLWriter {
    PrintWriter outputFile = null;

    public void XMLWriter(String fileName) {
        try {
            outputFile = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Could not open output file " + fileName);
            System.err.println("Run program again, make sure you have write permissions, etc.");
            System.err.println("Program exiting.");
            System.exit(0);
        }
    }

    /**
     * Closes the output file stream
     */
    public void close() {
        outputFile.close();
    }

    public void writeElement(String tag, String value) {
        outputFile.print("<"+tag+">");
        outputFile.print(" value ");
        outputFile.println("</"+tag+">");
    }
}