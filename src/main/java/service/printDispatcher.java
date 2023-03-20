package service;

import model.Document;
import util.printWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class printDispatcher {
    private Queue<Document> notPrintedDocsQueue;
    private List<Document> printedDocs;
    private ExecutorService executorService;

    public printDispatcher(List<Document> notPrintedDocs) {
        notPrintedDocsQueue.addAll(notPrintedDocs);
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        this.executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < notPrintedDocs.size(); i++) {
            Future<Integer> future = executorService.submit(new printWorker(notPrintedDocsQueue.peek()));
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
    public void sort(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What type of sort do you want? Print order (PO), " +
                "document type (DT), printing time (PT), paper size (PS)?");
        String choice = scanner.next();
        if (choice.equals("PO")){

        }
    }

    /**
     * 4. Диспетчер помещает в очередь печати неограниченное количество документов.
     * При этом каждый документ может быть обработан, только если в это же время не обрабатывается другой документ,
     * время обработки каждого документа равно продолжительности печати данного документа.
     * 5. Диспетчер должен иметь следующие методы:
     * * Остановка диспетчера. Печать документов в очереди отменяется. На выходе должен быть список ненапечатанных документов.
     * * Принять документ на печать. Метод не должен блокировать выполнение программы.
     * * Отменить печать принятого документа, если он еще не был напечатан.
     * * Получить отсортированный список напечатанных документов. Список может быть отсортирован на выбор:
     * по порядку печати, по типу документов, по продолжительности печати, по размеру бумаги.
     * * Рассчитать среднюю продолжительность печати напечатанных документов
     */
}
