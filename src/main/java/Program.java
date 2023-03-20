import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Print dispatcher!");
        System.out.println("Enter the number of documents you want to print:");
        int numberOfDocs = scanner.nextInt();


        System.out.println("What type of sort do you want? Print order (PO), " +
                "document type (DT), printing time (PT), paper size (PS)?");
        String choice = scanner.next();
    }
}
