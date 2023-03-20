package model;

public class Poster extends Document {
    private long printingTime;
    private String docType;
    private int[] paperSize;
    public Poster() {
        this.printingTime = 10;
        this.docType = "Poster";
        this.paperSize = new int[]{30, 40};
    }
}
