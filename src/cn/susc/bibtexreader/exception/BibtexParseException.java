package cn.susc.bibtexreader.exception;

public class BibtexParseException extends Exception {

    public BibtexParseException(String errorMessage) {
        super(errorMessage);
    }

    public BibtexParseException() {
        this("INCORRECT_FORMATION");
    }

}
