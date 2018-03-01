package cn.susc.bibtexreader.core;

import cn.susc.bibtexreader.exception.BibtexParseException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class BibtexParser {

    private static final char BEGIN_AT = '@';
    private static final char CURLY_BRACKET_LEFT = '{';
    private static final char CURLY_BRACKET_RIGHT = '}';
    private static final char COMMA = ',';
    private static final char EQUALS = '=';
    private static final char SPACE = ' ';
    private static final char NEW_LINE = '\n';

    public static List<BibtexEntity> fromString(String bibtexString) throws BibtexParseException {
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
        if (bibtexEntity == null) {
            return null;
        }
        if (bibtexEntity.getDocumentType() == null || "".equals(bibtexEntity.getDocumentType())) {
            throw new BibtexParseException("EMPTY_DOCUMENT_TYPE");
        }
        if (bibtexEntity.getDocumentId() == null || "".equals(bibtexEntity.getDocumentId())) {
            throw new BibtexParseException("EMPTY_DOCUMENT_ID");
        }
        if (bibtexEntity.getContentMap() == null || bibtexEntity.getContentMap().isEmpty()) {
            throw new BibtexParseException("EMPTY_ATTRIBUTES");
        }

        StringBuilder result = new StringBuilder();
        result.append("@").append(bibtexEntity.getDocumentType()).append("{");
        result.append(bibtexEntity.getDocumentId()).append(",\n");
        for (Map.Entry<String, String> attribute : bibtexEntity.getContentMap().entrySet()) {
            result.append("  ").append(attribute.getKey()).append("={").append(attribute.getValue()).append("},\n");
        }
        result.append("}");
        return result.toString();
    }

}
