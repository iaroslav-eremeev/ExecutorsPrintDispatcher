package util;

import model.Document;

import java.sql.Timestamp;
import java.util.concurrent.Callable;

public class PrintWorker implements Runnable {

    private Document document;

    public PrintWorker(Document document) {
        this.document = document;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(document.getPrintingDuration() * 1000L);
            document.setTimeOfPrinting(new Timestamp(System.currentTimeMillis()));
        } catch (Exception ignored){}
    }
}
