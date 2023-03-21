package model;

import java.util.Arrays;
import java.util.Objects;

public class Photo extends Document {

    private long printingTime;
    private String docType;
    private int[] paperSize;
    public Photo() {
        this.printingTime = 4;
        this.docType = "Photo";
        this.paperSize = new int[]{13, 8};
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
        Photo photo = (Photo) o;
        return printingTime == photo.printingTime && Objects.equals(docType, photo.docType) && Arrays.equals(paperSize, photo.paperSize);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), printingTime, docType);
        result = 31 * result + Arrays.hashCode(paperSize);
        return result;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "printingTime=" + printingTime +
                ", docType='" + docType + '\'' +
                ", paperSize=" + Arrays.toString(paperSize) +
                '}';
    }
}
