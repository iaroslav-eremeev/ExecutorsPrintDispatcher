package service;

import model.DocType;
import model.Document;

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

    public void waitForCancelOrStop(Scanner scanner) throws InterruptedException {
        System.out.println("To wait until current document is printed and stop printing enter STOP");
        System.out.println("To cancel current document printing enter CANCEL");
        String entry = scanner.next();
        while (true){
            if (entry.equals("STOP")){
                stopPrinting();
                break;
            }
            else if (entry.equals("CANCEL")){
                cancelPrinting();
                entry = scanner.next();
                System.out.println("Printing of current document is cancelled!");
            }
        }
    }

    public void waitForSortOrExit(Scanner scanner){
        System.out.println("If you want to sort printed documents, enter SORT");
        System.out.println("If you want to exit, enter EXIT");
        String entry = scanner.next();
        while (true) {
            if (entry.equals("SORT")) {
                System.out.println("What type of sort do you want? Printing duration (PD), " +
                        "document type (DT), time of printing (TP) or paper size (PS)?");
                entry = scanner.next();
                if (entry.equals("PD") || entry.equals("DT") || entry.equals("TP") || entry.equals("PS")){
                    sort(entry);
                    System.out.println(getPrintedDocs().toString());
                }
                else {
                    System.out.println("Wrong choice!");
                }
            }
            if (entry.equals("EXIT")){
                break;
            }
            entry = scanner.next();
        }
    }

    public List<Document> getPrintedDocs() {
        return printedDocs;
    }

    public void setPrintedDocs(List<Document> printedDocs) {
        this.printedDocs = printedDocs;
    }
}
