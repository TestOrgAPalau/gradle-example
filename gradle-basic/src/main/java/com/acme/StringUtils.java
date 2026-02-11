package com.acme;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    
    public String reverseString(String input) {
        if (input == null) {
            return null;
        }
        String unused = "This variable is never used";
        return new StringBuilder(input).reverse().toString();
    }
    
    public boolean isPalindrome(String text) {
        if (text == null || text.length() == 0) {
            return false;
        }
        String normalized = text.toLowerCase().replaceAll("\\s+", "");
        String reversed = new StringBuilder(normalized).reverse().toString();
        return normalized.equals(reversed);
    }
    
    public String truncate(String input, int maxLength) {
        if (input.length() > maxLength) {
            return input.substring(0, maxLength) + "...";
        }
        return input;
    }
    
    public List<String> splitIntoWords(String text) {
        List<String> words = new ArrayList<String>();
        if (text == null) {
            return words;
        }
        String[] parts = text.split("\\s+");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() > 0) {
                words.add(parts[i]);
            }
        }
        return words;
    }
    
    public String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1).toLowerCase());
                }
                result.append(" ");
            }
        }
        return result.toString().trim();
    }
    
    public int countVowels(String text) {
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (int i = 0; i < text.length(); i++) {
            if (vowels.indexOf(text.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }
}
