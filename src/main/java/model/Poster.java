package model;

import java.util.Arrays;
import java.util.Objects;

public class Poster extends Document {
    private long printingTime;
    private String docType;
    private int[] paperSize;
    public Poster() {
        this.printingTime = 6;
        this.docType = "Poster";
        this.paperSize = new int[]{30, 40};
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
        Poster poster = (Poster) o;
        return printingTime == poster.printingTime && Objects.equals(docType, poster.docType) && Arrays.equals(paperSize, poster.paperSize);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), printingTime, docType);
        result = 31 * result + Arrays.hashCode(paperSize);
        return result;
    }

    @Override
    public String toString() {
        return "Poster{" +
                "printingTime=" + printingTime +
                ", docType='" + docType + '\'' +
                ", paperSize=" + Arrays.toString(paperSize) +
                '}';
    }
}
