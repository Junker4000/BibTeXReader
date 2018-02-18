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
    public static final char EQUALS = '=';
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
        String currentAttrKey = null;
        String currentAttrValue = null;
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
                    } else if (stack.size() == 3 && EQUALS == stack.peek()) {
                        buffer = new StringBuilder();
                    } else {
                        throw new BibtexParseException();
                    }
                    stack.push(character);
                    break;
                case CURLY_BRACKET_RIGHT:
                    if (stack.size() == 2 && CURLY_BRACKET_LEFT == stack.peek()) {
                        if (currentEntity.getDocumentId() == null || "".equals(currentEntity.getDocumentId())) {
                            throw new BibtexParseException("EMPTY_DOCUMENT_ID");
                        }
                        result.add(currentEntity);
                    } else if (stack.size() == 4 && CURLY_BRACKET_LEFT == stack.peek()) {
                        currentAttrValue = buffer.toString().trim();
                        if (currentAttrKey == null || "".equals(currentAttrKey)) {
                            throw new BibtexParseException("EMPTY_ATTRIBUTE_NAME");
                        }
                        currentEntity.getContentMap().put(currentAttrKey, currentAttrValue);
                        currentAttrKey = null;
                        currentAttrValue = null;
                        buffer = new StringBuilder();
                    } else {
                        throw new BibtexParseException();
                    }
                    stack.pop();
                    stack.pop();
                    break;
                case EQUALS:
                    if (stack.size() == 2 && CURLY_BRACKET_LEFT == stack.peek()) {
                        currentAttrKey = buffer.toString().trim().toLowerCase();
                    } else {
                        throw new BibtexParseException();
                    }
                    stack.push(character);
                    break;
                case COMMA:
                    if (stack.size() == 2 && CURLY_BRACKET_LEFT == stack.peek()) {
                        if (currentEntity.getDocumentId() == null || "".equals(currentEntity.getDocumentId())) {
                            currentEntity.setDocumentId(buffer.toString());
                            buffer = new StringBuilder();
                        }
                    } else if (stack.size() == 4 && CURLY_BRACKET_LEFT == stack.peek()) {
                        buffer.append(character);
                    }
                    break;
                case SPACE:
                    if (stack.size() == 4 && CURLY_BRACKET_LEFT == stack.peek()) {
                        buffer.append(character);
                    }
                    break;
                case NEW_LINE:
                    break;
                default:
                    buffer.append(character);
                    break;
            }
        }
        return result;
    }

    public static String fromEntity(BibtexEntity bibtexEntity) throws BibtexParseException {
        return null;
    }

}
