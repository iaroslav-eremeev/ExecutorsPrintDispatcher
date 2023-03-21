package service;

import model.Document;
import model.Envelope;
import model.Photo;
import model.Poster;
import util.PrintWorker;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrintDispatcher {
    private Queue<Document> notPrintedDocsQueue;
    private List<Document> printedDocs;
    private ExecutorService executorService;
    ArrayList<Future<Integer>> futures;
    Future<Integer> future;

    public PrintDispatcher(int queueLength){
        this.notPrintedDocsQueue = new LinkedList<>();
        for (int i = 0; i < queueLength; i++) {
            this.notPrintedDocsQueue.add(generateRandomDocument());
        }
        this.printedDocs = new ArrayList<>();
    }

    public void launchPrintDispatcher() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.futures = new ArrayList<>();
        while (!executorService.isTerminated()) {
            this.future = executorService.submit(new PrintWorker(notPrintedDocsQueue.peek()));
            this.futures.add(future);
            takeDocForPrinting();
        }
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
        this.future.cancel(true);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintDispatcher that = (PrintDispatcher) o;
        return Objects.equals(notPrintedDocsQueue, that.notPrintedDocsQueue) && Objects.equals(printedDocs, that.printedDocs) && Objects.equals(executorService, that.executorService) && Objects.equals(futures, that.futures) && Objects.equals(future, that.future);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notPrintedDocsQueue, printedDocs, executorService, futures, future);
    }

    @Override
    public String toString() {
        return "PrintDispatcher{" +
                "notPrintedDocsQueue=" + notPrintedDocsQueue +
                ", printedDocs=" + printedDocs +
                ", executorService=" + executorService +
                ", futures=" + futures +
                ", future=" + future +
                '}';
    }
}
