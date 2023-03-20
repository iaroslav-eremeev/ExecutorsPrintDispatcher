package model;

public class Envelope extends Document {

    private long printingTime;
    private String docType;
    private int[] paperSize;
    public Envelope() {
        this.printingTime = 3;
        this.docType = "Envelope";
        this.paperSize = new int[]{22, 10};
    }
}
