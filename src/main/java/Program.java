import service.PrintDispatcher;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Print dispatcher!");
        System.out.println("Enter the number of documents you want to print:");
        int numberOfDocs = scanner.nextInt();
        PrintDispatcher printDispatcher = new PrintDispatcher(numberOfDocs);
        System.out.println(printDispatcher.getNotPrintedDocsQueue().toString());
        printDispatcher.launchPrintDispatcher();
        if (printDispatcher.getExecutorService().isShutdown()){
            System.out.println("If you want to sort printed documents, enter SORT");
            System.out.println("If you want to exit, enter EXIT");
            String entry = scanner.next();
            while (!entry.equals("EXIT")) {
                if (entry.equals("SORT")) {
                    System.out.println("What type of sort do you want? Print order (PO), " +
                            "document type (DT), printing time (PT), paper size (PS)?");
                    String choice = scanner.next();
                    printDispatcher.sort(choice);
                    System.out.println(printDispatcher.getPrintedDocs().toString());
                }
            }
            System.out.println("Average printing time is:");
            System.out.println(printDispatcher.calculateAveragePrintingTime());
        }

    }
}
