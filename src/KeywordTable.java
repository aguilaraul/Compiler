/**
 * KeywordTable.java
 * Author:  Raul Aguilar
 * Date:    November 20, 2020
 */
import java.util.HashMap;

public class KeywordTable {
    private HashMap<String, Integer> keywords = new HashMap<>();

    /**
     * Instantiate the table with reserved keywords and integer constants
     * representing them
     */
    public void KeywordTable() {
        keywords.put("class", 0);
        keywords.put("method", 1);
        keywords.put("function", 2);
        keywords.put("constructor", 3);
        keywords.put("int", 4);
        keywords.put("boolean", 5);
        keywords.put("char", 6);
        keywords.put("void", 7);
        keywords.put("var", 8);
        keywords.put("static", 9);
        keywords.put("field", 10);
        keywords.put("let", 11);
        keywords.put("do", 12);
        keywords.put("if", 13);
        keywords.put("else", 14);
        keywords.put("while", 15);
        keywords.put("return", 16);
        keywords.put("true", 17);
        keywords.put("false", 18);
        keywords.put("null", 19);
        keywords.put("this", 20);
    }

    /**
     * Returns boolean if the current token is a keyword that is in the table
     * @param token The current token to check
     * @return      True if the token is a keyword, otherwise return false
     */
    public boolean contains(String token) {
        return keywords.containsKey(token);
    }

    /**
     * Returns the keyword which is the current token, as a constant
     * @param keyword   The current keyword to retrieve
     * @return          Integer constant representing the current keyword
     */
    public int getKeyword(String keyword) {
        return keywords.get(keyword);
    }
}