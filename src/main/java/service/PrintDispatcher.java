package service;

import model.Document;
import model.Envelope;
import model.Photo;
import model.Poster;
import util.PrintWorker;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrintDispatcher {
    private Queue<Document> notPrintedDocsQueue;
    private List<Document> printedDocs;
    private ExecutorService executorService;
    private Future<?> futurePrinting;

    public PrintDispatcher(int queueLength){
        this.notPrintedDocsQueue = new LinkedList<>();
        for (int i = 0; i < queueLength; i++) {
            this.notPrintedDocsQueue.add(generateRandomDocument());
        }
        this.printedDocs = new ArrayList<>();
    }

    public void launchPrintDispatcher() {
        this.executorService = Executors.newFixedThreadPool(2);
        // Thread that reads user input from scanner and cancels printing of current document or stops printing
        System.out.println("Print dispatcher is launched!");
        System.out.println("If you want to cancel printing current document, enter CANCEL");
        System.out.println("If you want to stop printing, enter STOP");
        Scanner scanner = new Scanner(System.in);
        Future<Integer> futureReadingInput = this.executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String entry = scanner.next();
                if (entry.equals("CANCEL")){
                    cancelPrinting();
                    System.out.println("Printing of current document is cancelled!");
                    return 1;
                }
                else if (entry.equals("STOP")){
                    stopPrinting();
                    System.out.println("Printing is stopped!");
                    return 0;
                }
                else return -1;
            }
        });

        // Thread that takes document from notPrintedDocsQueue and prints it
        this.futurePrinting = this.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    if (futureReadingInput.isDone()){
                        if (futureReadingInput.get() == 0){
                            stopPrinting();
                        }
                        else if (futureReadingInput.get() == 1){
                            cancelPrinting();
                        }
                    }
                    Document document = notPrintedDocsQueue.peek();
                    if (document != null){
                        Thread.sleep(document.getPrintingTime() * 1000L);
                        document.setTimeWhenPrinted(new Timestamp(System.currentTimeMillis()));
                        System.out.println("Document " + document.getDocType()
                                + " is printed on " + document.getTimeWhenPrinted());
                        takeDocForPrinting();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public Document generateRandomDocument(){
        int random = (int) (Math.random() * 3);
        return switch (random) {
            case 0 -> new Poster();
            case 1 -> new Photo();
            case 2 -> new Envelope();
            default -> null;
        };
    }

    // Остановка диспетчера. Печать документов в очереди отменяется. На выходе должен быть список ненапечатанных документов.
    public List<Document> stopPrinting(){
        this.executorService.shutdown();
        return this.notPrintedDocsQueue.stream().toList();
    }

    // Принять документ на печать. Метод не должен блокировать выполнение программы.
    public synchronized void takeDocForPrinting(){
        Document doc = notPrintedDocsQueue.peek();
        if (doc != null){
            printedDocs.add(doc);
            notPrintedDocsQueue.poll();
        }
    }

    // Отменить печать принятого документа, если он еще не был напечатан.
    public void cancelPrinting(){
        this.futurePrinting.cancel(true);
    }

    // Получить отсортированный список напечатанных документов
    public void sort(String choice){
        if (choice.equals("PO")){
            printedDocs.sort(new Document.timeWhenPrintedComparator());
        } else if (choice.equals("DT")){
            printedDocs.sort(new Document.docTypeComparator());
        } else if (choice.equals("PT")){
            printedDocs.sort(new Document.printingTimeComparator());
        } else if (choice.equals("PS")){
            printedDocs.sort(new Document.paperSizeComparator());
        }
    }

    // Рассчитать среднюю продолжительность печати напечатанных документов
    public double calculateAveragePrintingTime(){
        double sum = 0;
        for (Document doc : printedDocs){
            sum += doc.getPrintingTime();
        }
        return sum / printedDocs.size();
    }

    public Queue<Document> getNotPrintedDocsQueue() {
        return notPrintedDocsQueue;
    }

    public void setNotPrintedDocsQueue(Queue<Document> notPrintedDocsQueue) {
        this.notPrintedDocsQueue = notPrintedDocsQueue;
    }

    public List<Document> getPrintedDocs() {
        return printedDocs;
    }

    public void setPrintedDocs(List<Document> printedDocs) {
        this.printedDocs = printedDocs;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
