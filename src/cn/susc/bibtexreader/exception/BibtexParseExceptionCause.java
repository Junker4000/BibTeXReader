package cn.susc.bibtexreader.exception;

public enum BibtexParseExceptionCause {

    INCORRECT_FORMATION("BibTeX formation not correct"),
    EMPTY_DOCUMENT_TYPE("document type cannot be empty"),
    EMPTY_DOCUMENT_ID("document id cannot be empty"),
    EMPTY_ATTRIBUTES("attribute list cannot be empty"),
    EMPTY_ATTRIBUTE_NAME("attribute name cannot be empty");

    private String message;

    BibtexParseExceptionCause(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
