package carsharing;

import java.util.Scanner;

public class MenuController {
    public static int mainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit""");
        return scanner.nextInt();
    }

    public static int managerMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("""
                1. Company list
                2. Create a company
                0. Back""");
        return scanner.nextInt();
    }

    public static int companyMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
        return scanner.nextInt();
    }

    public static String readCompanyName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter the company name:");
        return scanner.nextLine();
    }

    public static int customerMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
        return scanner.nextInt();
    }

}
