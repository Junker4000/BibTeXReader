package cn.susc.bibtexreader.exception;

public class BibtexParseException extends Exception {

    private BibtexParseExceptionCause cause;

    public BibtexParseException(BibtexParseExceptionCause cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    public BibtexParseException() {
        this(BibtexParseExceptionCause.INCORRECT_FORMATION);
    }

}
