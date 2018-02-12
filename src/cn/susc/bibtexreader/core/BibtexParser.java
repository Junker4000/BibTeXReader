package cn.susc.bibtexreader.core;

import cn.susc.bibtexreader.exception.BibtexParseException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class BibtexParser {

    public static final char BEGIN_AT = '@';
    public static final char CURLY_BRACKET_LEFT = '{';
    public static final char CURLY_BRACKET_RIGHT = '}';
    public static final char COMMA = ',';
    public static final char VALUE_GIVEN = '=';
    public static final char SPACE = ' ';
    public static final char NEW_LINE = '\n';

    public static List<BibtexEntity> formString(String bibtexString) throws BibtexParseException {
        return fromStringInternal(bibtexString);
    }

    public static BibtexEntity fromStringSingle(String bibtexString) throws BibtexParseException {
        List<BibtexEntity> tempList = fromStringInternal(bibtexString);
        return tempList.isEmpty() ? null : tempList.get(0);
    }

    private static List<BibtexEntity> fromStringInternal(String bibtexString) throws BibtexParseException {
        Stack<Character> stack = new Stack<>();
        List<BibtexEntity> result = new LinkedList<>();
        BibtexEntity currentEntity = null;
        Map.Entry<String, String> currentMapEntry = null;
        StringBuilder buffer = null;
        for (char character : bibtexString.toCharArray()) {
            switch (character) {
                case BEGIN_AT:
                    if (stack.isEmpty()) {
                        currentEntity = new BibtexEntity();
                        buffer = new StringBuilder();
                        stack.push(character);
                    } else {
                        throw new BibtexParseException();
                    }
                    break;
                case CURLY_BRACKET_LEFT:
                    if (stack.size() == 1 && BEGIN_AT == stack.peek()) {
                        currentEntity.setDocumentType(buffer.toString().trim().toUpperCase());
                        buffer = new StringBuilder();
                    } else if (stack.size() == 3 && VALUE_GIVEN == stack.peek()) {
                        buffer = new StringBuilder();
                    } else {
                        throw new BibtexParseException();
                    }
                    stack.push(character);
                    break;
                case CURLY_BRACKET_RIGHT:

                    break;
            }
        }
        return result;
    }

    public static String fromEntity(BibtexEntity bibtexEntity) throws BibtexParseException {
        return null;
    }

}
