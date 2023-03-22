import service.PrintDispatcher;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        PrintDispatcher printDispatcher = new PrintDispatcher();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Print dispatcher is launched!");
                printDispatcher.launchPrintDispatcher();
            }
        });
        thread.start();
        printDispatcher.waitForCancelOrStop(scanner);
        thread.interrupt();
        printDispatcher.waitForSortOrExit(scanner);
        System.out.println("Average printing time is:");
        System.out.println(printDispatcher.calculateAveragePrintingTime());
    }
}
