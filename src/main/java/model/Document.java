package model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

public class Document {
    private long printingTime;
    private String docType;
    private int[] paperSize;
    private Timestamp timeWhenPrinted;

    public Document() {
    }

    public long getPrintingTime() {
        return printingTime;
    }

    public void setPrintingTime(long printingTime) {
        this.printingTime = printingTime;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
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
        return printingTime == document.printingTime && Objects.equals(docType, document.docType) && Arrays.equals(paperSize, document.paperSize);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(printingTime, docType);
        result = 31 * result + Arrays.hashCode(paperSize);
        return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "printingTime=" + printingTime +
                ", docType='" + docType + '\'' +
                ", paperSize=" + Arrays.toString(paperSize) +
                '}';
    }
}
