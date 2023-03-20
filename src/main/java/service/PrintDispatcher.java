package service;

import model.Document;
import util.PrintWorker;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrintDispatcher {
    private Queue<Document> notPrintedDocsQueue;
    private List<Document> printedDocs;
    private ExecutorService executorService;

    public PrintDispatcher() {
    }

    public PrintDispatcher(List<Document> notPrintedDocs) {
        notPrintedDocsQueue.addAll(notPrintedDocs);
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        this.executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < notPrintedDocs.size(); i++) {
            Future<Integer> future = executorService.submit(new PrintWorker(notPrintedDocsQueue.peek()));
            futures.add(future);
            takeDocForPrinting();
        }
    }

    // Остановка диспетчера. Печать документов в очереди отменяется. На выходе должен быть список ненапечатанных документов.
    public List<Document> stopPrinting(){
        this.executorService.shutdownNow();
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
        this.executorService.shutdown();
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
        System.out.println(printedDocs);
    }

    // Рассчитать среднюю продолжительность печати напечатанных документов
    public void calculateAveragePrintingTime(){
        long sum = 0;
        for (Document doc : printedDocs){
            sum += doc.getPrintingTime();
        }
        System.out.println("Average printing time: " + sum / printedDocs.size());
    }
}
