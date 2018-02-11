package cn.susc.bibtexreader.core;

import java.util.Map;

public class BibtexEntity {

    private String documentType;
    private String bibtexId;
    private Map<String, String> contentMap;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getBibtexId() {
        return bibtexId;
    }

    public void setBibtexId(String bibtexId) {
        this.bibtexId = bibtexId;
    }

    public Map<String, String> getContentMap() {
        return contentMap;
    }

    public void setContentMap(Map<String, String> contentMap) {
        this.contentMap = contentMap;
    }

    @Override
    public int hashCode() {
        return documentType.hashCode() * 31 + bibtexId.hashCode() * 15 + contentMap.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BibtexEntity) {
            BibtexEntity bibtexEntity = (BibtexEntity) obj;
            return documentType.equals(bibtexEntity.documentType) && bibtexId.equals(bibtexEntity.bibtexId)
                    && contentMap.equals(bibtexEntity.contentMap);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return BibtexParser.fromEntity(this);
    }

}
