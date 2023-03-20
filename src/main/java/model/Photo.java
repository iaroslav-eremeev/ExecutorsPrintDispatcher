package model;

public class Photo extends Document {

    private long printingTime;
    private String docType;
    private int[] paperSize;
    public Photo() {
        this.printingTime = 5;
        this.docType = "Photo";
        this.paperSize = new int[]{13, 8};
    }
}
