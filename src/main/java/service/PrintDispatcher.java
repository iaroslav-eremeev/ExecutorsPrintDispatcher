package service;

import model.DocType;
import model.Document;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintDispatcher {
    private Queue<Document> notPrintedDocsQueue;
    private List<Document> printedDocs;
    private ExecutorService executorService;

    public PrintDispatcher(){
        this.notPrintedDocsQueue = new LinkedList<>();
        this.printedDocs = new ArrayList<>();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void launchPrintDispatcher() {



        /*this.executorService = Executors.newFixedThreadPool(2);
        // Thread that reads user input from scanner and cancels printing of current document or stops printing
        System.out.println("Print dispatcher is launched!");
        System.out.println("If you want to cancel printing current document, enter CANCEL");
        System.out.println("If you want to stop printing, enter STOP");
        Scanner scanner = new Scanner(System.in);
        Future<Integer> futureReadingInput = this.executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try {
                    while (true){
                        if (scanner.hasNext()){
                            String entry = scanner.next();
                            if (entry.equals("CANCEL")){
                                cancelPrinting();
                                System.out.println("Printing of current document is cancelled!");
                                return 1;
                            }
                            else if (entry.equals("STOP")){
                                stopPrinting();
                                return 0;
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Thread that takes document from notPrintedDocsQueue and prints it
        this.futurePrinting = this.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        System.out.println("Printing is started!");
                        if (futureReadingInput.isDone()){
                            if (futureReadingInput.get() == 0){
                                stopPrinting();
                                throw new RuntimeException("Printing is stopped!");
                            }
                            else if (futureReadingInput.get() == 1){
                                cancelPrinting();
                            }
                        }
                        System.out.println("Here we know if futureReadingInput is done: " + futureReadingInput.isDone());
                        Document document = notPrintedDocsQueue.peek();
                        if (document != null){
                            Thread.sleep(document.getPrintingDuration() * 1000L);
                            document.setTimeOfPrinting(new Timestamp(System.currentTimeMillis()));
                            System.out.println("Document " + document.getDocType()
                                    + " is printed on " + document.getTimeOfPrinting());
                            takeDocForPrinting();
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });*/
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
            System.out.println("First in the queue now is " + notPrintedDocsQueue.peek().getDocType());
        }
    }

    // Отменить печать принятого документа, если он еще не был напечатан.
    public void cancelPrinting(){
        this.futurePrinting.cancel(true);
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

    // Рассчитать среднюю продолжительность печати напечатанных документов
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
