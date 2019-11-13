/**
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

        // Open jack file to tokenize
        jackTokenizer.tokenizer(inputFileName);
        while(jackTokenizer.hasMoreLines()) {
            jackTokenizer.readNextLine();

            //jackTokenizer.printSplit();
        }
        System.out.println("TOKENS\n-------");
        jackTokenizer.printTokens();
        //System.out.println("[:" + line + ":]");                 //@Debug: remove when done
        System.out.println("Done compiling. Program exiting.");
    }
}