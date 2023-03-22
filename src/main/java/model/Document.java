package model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

public class Document {
    private long printingDuration;
    private DocType docType;
    private int[] paperSize;
    private Timestamp timeOfPrinting;

    public Document(DocType docType) {
        this.docType = docType;
        if (docType == DocType.ENVELOPE) {
            this.printingDuration = 5;
            this.paperSize = new int[]{9, 12};
        } else if (docType == DocType.PHOTO) {
            this.printingDuration = 10;
            this.paperSize = new int[]{10, 15};
        } else if (docType == DocType.POSTER) {
            this.printingDuration = 15;
            this.paperSize = new int[]{18, 24};
        }
    }

    public long getPrintingDuration() {
        return printingDuration;
    }

    public void setPrintingDuration(long printingDuration) {
        this.printingDuration = printingDuration;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public int[] getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(int[] paperSize) {
        this.paperSize = paperSize;
    }

    public Timestamp getTimeOfPrinting() {
        return timeOfPrinting;
    }

    public void setTimeOfPrinting(Timestamp timeOfPrinting) {
        this.timeOfPrinting = timeOfPrinting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return printingDuration == document.printingDuration && docType == document.docType && Arrays.equals(paperSize, document.paperSize) && Objects.equals(timeOfPrinting, document.timeOfPrinting);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(printingDuration, docType, timeOfPrinting);
        result = 31 * result + Arrays.hashCode(paperSize);
        return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "printingDuration=" + printingDuration +
                ", docType=" + docType +
                ", paperSize=" + Arrays.toString(paperSize) +
                ", timeOfPrinting=" + timeOfPrinting +
                '}';
    }
}
