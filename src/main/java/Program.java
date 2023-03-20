import service.PrintDispatcher;

import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Print dispatcher!");
        System.out.println("Enter the number of documents you want to print:");
        int numberOfDocs = scanner.nextInt();
        PrintDispatcher printDispatcher = new PrintDispatcher(numberOfDocs);
        printDispatcher.launchPrintDispatcher();
        System.out.println("Print dispatcher is launched!");
        System.out.println("If you want to cancel printing current document, enter CANCEL");
        System.out.println("If you want to stop printing, enter STOP");
        while (true){
            String entry = scanner.next();
            if (entry.equals("CANCEL")){
                printDispatcher.cancelPrinting();
                System.out.println("Printing of current document is cancelled!");
            }
            if (entry.equals("STOP")){
                printDispatcher.stopPrinting();
                System.out.println("Printing is stopped!");
                break;
            }
        }
        System.out.println("If you want to sort printed documents, enter SORT");
        System.out.println("If you want to exit, enter EXIT");
        String entry = scanner.next();
        while (!entry.equals("EXIT")) {
            if (entry.equals("SORT")) {
                System.out.println("What type of sort do you want? Print order (PO), " +
                        "document type (DT), printing time (PT), paper size (PS)?");
                String choice = scanner.next();
                printDispatcher.sort(choice);
            }
        }
    }
}
