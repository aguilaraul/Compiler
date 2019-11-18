/*
 * @author  Raul Aguilar
 * @date    12 November 2019
 */
import java.util.Scanner;

public class JackAnalyzer {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        JackTokenizer jackTokenizer = new JackTokenizer();
        String inputFileName, outputFileName;

        // Input file name
        System.out.println("Please enter the .jack file name you would like to compile.");
        System.out.println("Don't forget the .jack extension: ");
        inputFileName = keyboard.nextLine();
        keyboard.close();

        // Open output file
        jackTokenizer.setFileName(inputFileName);

        // Open jack file to tokenize
        jackTokenizer.tokenizer(inputFileName);
        while(jackTokenizer.hasMoreLines()) {
            // Parsing each line into tokens
            jackTokenizer.parseNextLine();

            //@Debug: Print each line from the file
            System.out.println(jackTokenizer.getCleanLine());
        }
        System.out.println("TOKENS\n-------");
        jackTokenizer.printTokens();
        jackTokenizer.writeTokens();
        System.out.println("Done compiling. Program exiting.");
    }
}