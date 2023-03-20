package util;

import model.Document;

import java.util.concurrent.Callable;

public class printWorker implements Callable<Integer> {

    Document document;

    @Override
    public Integer call() throws Exception {
        try {
            Thread.sleep(document.getPrintingTime());
            document.
            return 1;
        } catch (Exception e){
            return 0;
        }
    }
}
