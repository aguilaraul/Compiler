/*
 * @author  Raul Aguilar
 * @date    12 November 2019
 */
import java.util.Scanner;

public class JackAnalyzer {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        JackTokenizer jackTokenizer = new JackTokenizer();
        String inputFileName;

        // Input file name
        System.out.println("Please enter the .jack file name you would like to compile.");
        System.out.println("Don't forget the .jack extension: ");
        inputFileName = keyboard.nextLine();
        keyboard.close();

        // Open output file
        jackTokenizer.setFileName(inputFileName);

        // Open jack file to read and tokenize
        jackTokenizer.tokenizer(inputFileName);
        while(jackTokenizer.hasMoreLines()) {
            jackTokenizer.parseNextLine();
        }

        jackTokenizer.advance();

        jackTokenizer.close();
        System.out.println("Done compiling. Program exiting.");
    }
}