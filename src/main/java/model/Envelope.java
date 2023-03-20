package model;

import java.util.Arrays;
import java.util.Objects;

public class Envelope extends Document {

    private long printingTime;
    private String docType;
    private int[] paperSize;
    public Envelope() {
        this.printingTime = 3;
        this.docType = "Envelope";
        this.paperSize = new int[]{22, 10};
    }

    @Override
    public long getPrintingTime() {
        return printingTime;
    }

    @Override
    public void setPrintingTime(long printingTime) {
        this.printingTime = printingTime;
    }

    @Override
    public String getDocType() {
        return docType;
    }

    @Override
    public void setDocType(String docType) {
        this.docType = docType;
    }

    @Override
    public int[] getPaperSize() {
        return paperSize;
    }

    @Override
    public void setPaperSize(int[] paperSize) {
        this.paperSize = paperSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Envelope envelope = (Envelope) o;
        return printingTime == envelope.printingTime && Objects.equals(docType, envelope.docType) && Arrays.equals(paperSize, envelope.paperSize);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), printingTime, docType);
        result = 31 * result + Arrays.hashCode(paperSize);
        return result;
    }

    @Override
    public String toString() {
        return "Envelope{" +
                "printingTime=" + printingTime +
                ", docType='" + docType + '\'' +
                ", paperSize=" + Arrays.toString(paperSize) +
                '}';
    }
}
