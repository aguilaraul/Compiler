/*
 * JackAnalyzer.java
 * Author:  Raul Aguilar
 * Date:    November 20, 2020
 * JackAnalyzer: The analyzer program operates on a given source, where source is either
 * a file name of the form Xxx.jack or a directory name containing one or more such files.
 * For each source Xxx.jack file, the analyzer goes through the following logic:
 *  1. Create a JackTokenizer from the Xxx.jack input file.
 *  2. Create an output file called Xxx.xml and prepare it for writing.
 *  3. Use the CompilationEngine to compile the input JackTokenizer into the output file.
 */
import java.util.Scanner;

public class JackAnalyzer {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        JackTokenizer jackTokenizer = new JackTokenizer();
        CompilationEngine compilationEngine = new CompilationEngine();
        String inputFileName;

        // Input file name
        System.out.println("Please enter the .jack file name you would like to compile.");
        System.out.println("Don't forget the .jack extension: ");
        inputFileName = keyboard.nextLine();
        keyboard.close();

        // Open jack file to read and tokenize
        jackTokenizer.tokenizer(inputFileName);
        while(jackTokenizer.hasMoreLines()) {
            jackTokenizer.advance();
        }
        // After tokenizing, write to xml file with tags
        jackTokenizer.writeToXML();

        // Feed the tokenized xml file into the Compilation Engine
        compilationEngine.compilationEngine(inputFileName);
        while(compilationEngine.hasMoreLines()) {
            compilationEngine.advance();
        }
        compilationEngine.close();

        System.out.println("Done compiling. Program exiting.");
    }
}