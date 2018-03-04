package cn.susc.bibtexreader.exception;

public class TemplateParseException extends Exception {

    private TemplateParseExceptionCause cause;

    public TemplateParseException(TemplateParseExceptionCause cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

}
