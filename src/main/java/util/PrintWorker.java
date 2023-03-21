package util;

import model.Document;

import java.sql.Timestamp;
import java.util.concurrent.Callable;

public class PrintWorker implements Callable<Document> {

    private Document document;

    public PrintWorker(Document document) {
        this.document = document;
    }

    @Override
    public Document call() throws Exception {
        try {
            Thread.sleep(document.getPrintingDuration() * 1000L);
            document.setTimeOfPrinting(new Timestamp(System.currentTimeMillis()));
            System.out.println("Document " + document.getDocType()
                    + " is printed on " + document.getTimeOfPrinting());
            return document;
        } catch (Exception e){
            return null;
        }
    }
}
