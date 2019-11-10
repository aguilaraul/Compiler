/**
 * @author  Raul Aguilar
 * @date    09 November 2019
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
        
        // Create output file
        outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".xml";
        jackTokenizer.xmlWriter(outputFileName);

        // Begin tokenizing
        jackTokenizer.tokenizer(inputFileName);
        while(jackTokenizer.hasMoreTokens()) {
            jackTokenizer.advance();
            if(!jackTokenizer.getCleanLine().isEmpty())
                System.out.println(jackTokenizer.getLineNumber()+": " + jackTokenizer.getCleanLine());
                for(Keyword key: Keyword.values()) {
                    if(jackTokenizer.getCleanLine().startsWith(key.name().toLowerCase())) {
                        System.out.println(key.name());
                    }
                }
        }

        // Done compiling
        jackTokenizer.close();  // close output file
        System.out.println("Done compiling. Program exiting.");
    }
}