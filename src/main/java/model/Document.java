package model;

import javax.print.Doc;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class Document {
    private long printingTime;
    private DocType docType;
    private int[] paperSize;
    private Timestamp timeWhenPrinted;

    public Document(DocType docType) {
        this.docType = docType;
        if (docType == DocType.ENVELOPE) {
            this.printingTime = 2;
            this.paperSize = new int[]{9, 12};
        } else if (docType == DocType.PHOTO) {
            this.printingTime = 4;
            this.paperSize = new int[]{10, 15};
        } else if (docType == DocType.POSTER) {
            this.printingTime = 6;
            this.paperSize = new int[]{18, 24};
        }
    }

    public long getPrintingTime() {
        return printingTime;
    }

    public void setPrintingTime(long printingTime) {
        this.printingTime = printingTime;
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

    public Timestamp getTimeWhenPrinted() {
        return timeWhenPrinted;
    }

    public void setTimeWhenPrinted(Timestamp timeWhenPrinted) {
        this.timeWhenPrinted = timeWhenPrinted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return printingTime == document.printingTime && docType == document.docType && Arrays.equals(paperSize, document.paperSize) && Objects.equals(timeWhenPrinted, document.timeWhenPrinted);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(printingTime, docType, timeWhenPrinted);
        result = 31 * result + Arrays.hashCode(paperSize);
        return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "printingTime=" + printingTime +
                ", docType=" + docType +
                ", paperSize=" + Arrays.toString(paperSize) +
                ", timeWhenPrinted=" + timeWhenPrinted +
                '}';
    }
}
