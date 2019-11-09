/**
 * @author  Raul Aguilar
 * @date    09 November 2019
 */
import java.util.Scanner;

public class Compiler {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        XMLWriter xmlWriter = new XMLWriter();
        JackTokenizer jackTokenizer = new JackTokenizer();
        String inputFileName, outputFileName;

        // Input file name
        System.out.println("Please enter the .jack file name you would like to compile.");
        System.out.println("Don't forget the .jack extension: ");
        inputFileName = keyboard.nextLine();
        keyboard.close();
        // Create output file
        outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".xml";
        xmlWriter.XMLWriter(outputFileName);

        // Begin tokenizing
        jackTokenizer.JackTokenizer(inputFileName);
        while(jackTokenizer.hasMoreTokens()) {
            switch (jackTokenizer.getTokenType()) {
                case KEYWORD:
                    xmlWriter.writeElement(TokenType.KEYWORD.toString(), jackTokenizer.getAtom());
                    break;
                case SYMBOL:
                    xmlWriter.writeElement("symbol", jackTokenizer.getAtom());
                    break;
                case INT_CONST:
                    xmlWriter.writeElement("integerConstant", jackTokenizer.getAtom());
                    break;
                case STRING_CONST:
                    xmlWriter.writeElement("stringConstant", jackTokenizer.getAtom());
                    break;
                case IDENTIFIER:
                    xmlWriter.writeElement("identifier", jackTokenizer.getAtom());
                    break;
            }
        }



        // Done compiling
        xmlWriter.close();
        System.out.println("Done compiling. Program exiting.");
    }
}