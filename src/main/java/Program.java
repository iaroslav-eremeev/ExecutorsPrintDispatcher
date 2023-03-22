import service.PrintDispatcher;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Print dispatcher!");
        PrintDispatcher printDispatcher = new PrintDispatcher();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Print dispatcher is launched!");
                printDispatcher.launchPrintDispatcher();
            }
        });
        thread.start();
        System.out.println("To wait until current document is printed and stop printing enter STOP");
        System.out.println("To cancel current document printing enter CANCEL");
        String entry = scanner.next();
        while (true){
            if (entry.equals("STOP")){
                printDispatcher.stopPrinting();
                break;
            }
            else if (entry.equals("CANCEL")){
                printDispatcher.cancelPrinting();
                entry = scanner.next();
                System.out.println("Printing of current document is cancelled!");
            }
        }
        thread.interrupt();
        System.out.println("If you want to sort printed documents, enter SORT");
        System.out.println("If you want to exit, enter EXIT");
        entry = scanner.next();
        while (true) {
            if (entry.equals("SORT")) {
                System.out.println("What type of sort do you want? Printing duration (PD), " +
                        "document type (DT), time of printing (TP) or paper size (PS)?");
                entry = scanner.next();
                if (entry.equals("PD") || entry.equals("DT") || entry.equals("TP") || entry.equals("PS")){
                    printDispatcher.sort(entry);
                    System.out.println(printDispatcher.getPrintedDocs().toString());
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
        System.out.println("Average printing time is:");
        System.out.println(printDispatcher.calculateAveragePrintingTime());
    }
}
