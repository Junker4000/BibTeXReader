package cn.susc.bibtexreader.core;

import java.util.Map;

public class BibtexEntity {

    private String documentType;
    private String documentId;
    private Map<String, String> contentMap;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Map<String, String> getContentMap() {
        return contentMap;
    }

    public void setContentMap(Map<String, String> contentMap) {
        this.contentMap = contentMap;
    }

    @Override
    public int hashCode() {
        return documentType.hashCode() * 31 + documentId.hashCode() * 15 + contentMap.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BibtexEntity) {
            BibtexEntity bibtexEntity = (BibtexEntity) obj;
            return documentType.equals(bibtexEntity.documentType) && documentId.equals(bibtexEntity.documentId)
                    && contentMap.equals(bibtexEntity.contentMap);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "@".concat(documentType).concat(" id=").concat(documentId);
    }

}
