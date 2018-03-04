package cn.susc.bibtexreader.exception;

public enum TemplateParseExceptionCause {

    DOCUMENT_TYPE_NOT_MATCH("Document type not match");

    private String message;

    TemplateParseExceptionCause(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
