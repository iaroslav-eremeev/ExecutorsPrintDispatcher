package util;

import model.Document;

import java.sql.Timestamp;
import java.util.concurrent.Callable;

public class PrintWorker implements Callable<Integer> {

    private Document document;

    public PrintWorker(Document document) {
        this.document = document;
    }

    @Override
    public Integer call() throws Exception {
        try {
            Thread.sleep(this.document.getPrintingTime() * 1000L);
            this.document.setTimeWhenPrinted(new Timestamp(System.currentTimeMillis()));
            return 1;
        } catch (Exception e){
            return null;
        }
    }
}
