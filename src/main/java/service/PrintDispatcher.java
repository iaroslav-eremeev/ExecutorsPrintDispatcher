package service;

import model.DocType;
import model.Document;
import util.PrintWorker;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;

public class PrintDispatcher {
    private Queue<Document> notPrintedDocsQueue;
    private List<Document> printedDocs;
    private ExecutorService executorService;

    public PrintDispatcher(){
        this.notPrintedDocsQueue = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(5, 21); i++) {
            this.notPrintedDocsQueue.add(generateRandomDocument());
        }
        this.printedDocs = new ArrayList<>();
        /*this.executorService = Executors.newSingleThreadExecutor();*/
    }

    public void launchPrintDispatcher() {
        while (!this.notPrintedDocsQueue.isEmpty()) {
            if (this.executorService == null || this.executorService.isShutdown()) {
                this.executorService = Executors.newSingleThreadExecutor();
                Document document = this.notPrintedDocsQueue.peek();
                if (document != null){
                    System.out.println("First in the queue now is " + document.getDocType());
                    if (!this.executorService.isShutdown()){
                        executorService.execute(() -> {
                            try {
                                System.out.println("Document " + document.getDocType() + " is printing");
                                document.setTimeOfPrinting(new Timestamp(System.currentTimeMillis()));
                                Thread.sleep(document.getPrintingDuration() * 1000L);
                                takeDocForPrinting(document);
                                System.out.println("Document " + document.getDocType()
                                        + " is printed on " + document.getTimeOfPrinting());
                                cancelPrinting();
                            } catch (Exception ignored) {
                            }
                        });
                    }
                }
                else {
                    System.out.println("Queue is empty");
                }
            }
        }
    }

    // Generate random document
    public Document generateRandomDocument(){
        int random = (int) (Math.random() * 3);
        return switch (random) {
            case 0 -> new Document(DocType.POSTER);
            case 1 -> new Document(DocType.PHOTO);
            case 2 -> new Document(DocType.ENVELOPE);
            default -> null;
        };
    }

    // Stop dispatcher. Cancel printing of documents from the queue. Returns list of not printed documents
    public List<Document> stopPrinting(){
        System.out.println("Stopped on " + new Timestamp(System.currentTimeMillis()));
        List<Document> documents = this.notPrintedDocsQueue.stream().toList();
        cancelPrinting();
        this.notPrintedDocsQueue.clear();
        return documents;
    }

    // Accept document for printing
    public synchronized void takeDocForPrinting(Document doc){
        if (doc != null){
            printedDocs.add(doc);
            notPrintedDocsQueue.poll();
        }
    }

    // Cancel printing of current document if it is not printed yet
    public void cancelPrinting(){
        this.executorService.shutdownNow();
        this.notPrintedDocsQueue.poll();
    }

    // Sort printed documents by printing duration, document type, time of printing, paper size
    public void sort(String choice){
        switch (choice) {
            case "PD" -> printedDocs.sort(new Comparator<Document>() {
                @Override
                public int compare(Document o1, Document o2) {
                    return (int) (o1.getPrintingDuration() - o2.getPrintingDuration());
                }
            });
            case "DT" -> printedDocs.sort(new Comparator<Document>() {
                @Override
                public int compare(Document o1, Document o2) {
                    return o1.getDocType().compareTo(o2.getDocType());
                }
            });
            case "TP" -> printedDocs.sort(new Comparator<Document>() {
                @Override
                public int compare(Document o1, Document o2) {
                    return (int) (o1.getTimeOfPrinting().getTime() - o2.getTimeOfPrinting().getTime());
                }
            });
            case "PS" -> printedDocs.sort(new Comparator<Document>() {
                @Override
                public int compare(Document o1, Document o2) {
                    return o1.getPaperSize()[0] * o1.getPaperSize()[1] - o2.getPaperSize()[0] * o2.getPaperSize()[1];
                }
            });
        }
    }

    // Calculate average printing time of all printed documents
    public double calculateAveragePrintingTime(){
        double sum = 0;
        for (Document doc : printedDocs){
            sum += doc.getPrintingDuration();
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
