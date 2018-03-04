package cn.susc.bibtexreader.template;

import cn.susc.bibtexreader.core.BibtexEntity;
import cn.susc.bibtexreader.exception.TemplateParseException;

public abstract class TemplateBase {

    protected boolean empty = false;

    protected boolean valid = false;

    public TemplateBase() {}

    public TemplateBase(BibtexEntity bibtexEntity) {
        fromBibtexEntity(bibtexEntity);
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean isValid() {
        return valid;
    }

    public abstract void fromBibtexEntity(BibtexEntity bibtexEntity);

    public abstract void fromBibtexEntityStrict(BibtexEntity bibtexEntity) throws TemplateParseException;

}
