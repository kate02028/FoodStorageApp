/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package foodstorageapp;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FoodStorageApp {

    public static final String APP_NAME = "Fast-Food Tray Storage System";
    public static final String VERSION  = "1.0.0";

    private static final Scanner SC = new Scanner(System.in);
    private static final FoodStorage STORAGE = new FoodStorage();

    public static void main(String[] args) {

        try (SC) {
            banner();
            boolean running = true;

            while (running) {
                printMenu();
                int choice = readInt("Enter choice > ");

                switch (choice) {
                    case 1 -> addFlow();
                    case 2 -> removeFlow(FoodStorage.Side.FRONT);
                    case 3 -> removeFlow(FoodStorage.Side.OPPOSITE);
                    case 4 -> peekFlow();
                    case 5 -> displayFlow();
                    case 6 -> searchFlow();
                    case 7 -> statusFlow();
                    case 8 -> demoFlow();
                    case 0 -> {
                        System.out.print("Terminate the application? (y/n): ");
                        String ans = SC.nextLine().trim();
                        if (ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("yes")) {
                            running = false;
                            System.out.println("\nApplication terminated. Goodbye!");
                        } else {
                            System.out.println("Termination cancelled.\n");
                        }
                    }
                    default -> System.out.println("  !! Invalid option. Please enter a number from 0 to 8.\n");
                }
            }
        }
    }

    // ------------------------------------------------------------------ UI

    private static void banner() {
        System.out.println("==================================================");
        System.out.println("  " + APP_NAME + "  v" + VERSION);
        System.out.println("  Data structure : Deque (ArrayDeque), capacity " + FoodStorage.MAX_CAPACITY);
        System.out.println("==================================================");
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println(" 1. Add a food item");
        System.out.println(" 2. Remove item from FRONT side");
        System.out.println(" 3. Remove item from OPPOSITE side");
        System.out.println(" 4. Peek the top item(s)");
        System.out.println(" 5. Display all stored items");
        System.out.println(" 6. Search item by name");
        System.out.println(" 7. Storage status report");
        System.out.println(" 8. Load demo data");
        System.out.println(" 0. Terminate application");
        System.out.println("--------------------------------------");
    }

    // ------------------------------------------------------------ OPERATIONS

    private static void addFlow() {
        System.out.println("\n--- ADD FOOD ITEM ---");
        if (STORAGE.isFull()) {
            System.out.println("  !! Storage is already full (8/8). Remove an item first.\n");
            return;
        }
        try {
            System.out.print("Food name (Burger/Pizza/Fries/Sandwich/Hotdog): ");
            String name = Validator.validateName(SC.nextLine());

            System.out.print("Weight in grams (1 - 5000): ");
            double weight = Validator.validateWeight(SC.nextLine());

            System.out.print("Best-before date dd/MM/yyyy (max 14 days from today): ");
            LocalDate date = Validator.validateBestBefore(SC.nextLine());

            FoodItem item = new FoodItem(name, weight, date);
            STORAGE.add(item);

            System.out.println("  >> Added: " + item);
            System.out.printf("  >> Storage now: %d/%d%n%n", STORAGE.size(), FoodStorage.MAX_CAPACITY);

        } catch (IllegalArgumentException e) {
            System.out.println("  !! Invalid input: " + e.getMessage() + "\n");
        }
    }

    private static void removeFlow(FoodStorage.Side side) {
        System.out.println("\n--- REMOVE FROM " + side + " SIDE ---");
        try {
            FoodItem removed = STORAGE.remove(side);
            System.out.println("  >> Removed: " + removed);
            System.out.printf("  >> Storage now: %d/%d%n%n", STORAGE.size(), FoodStorage.MAX_CAPACITY);
        } catch (FoodStorageEmptyException e) {
            System.out.println("  !! " + e.getMessage() + "\n");
        }
    }

    private static void peekFlow() {
        System.out.println("\n--- TOP OF STORAGE ---");
        try {
            System.out.println("  FRONT side    : " + STORAGE.peek(FoodStorage.Side.FRONT).getName());
            System.out.println("  OPPOSITE side : " + STORAGE.peek(FoodStorage.Side.OPPOSITE).getName());
            System.out.println();
        } catch (FoodStorageEmptyException e) {
            System.out.println("  !! " + e.getMessage() + "\n");
        }
    }

    private static void displayFlow() {
        System.out.println("\n--- STORED ITEMS (" + STORAGE.size() + "/" + FoodStorage.MAX_CAPACITY + ") ---");
        if (STORAGE.isEmpty()) {
            System.out.println("  !! Storage is empty - nothing to display.\n");
            return;
        }
        System.out.print(STORAGE.display());
        System.out.println();
    }

    private static void searchFlow() {
        System.out.println("\n--- SEARCH BY NAME ---");
        if (STORAGE.isEmpty()) {
            System.out.println("  !! Storage is empty - nothing to search.\n");
            return;
        }
        System.out.print("Enter name or part of a name: ");
        String keyword = SC.nextLine();

        List<FoodItem> results = STORAGE.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("  !! No item found matching \"" + keyword.trim() + "\".\n");
        } else {
            System.out.println("  " + results.size() + " match(es) found:");
            for (FoodItem f : results) {
                System.out.printf("    - %s  [%s]%n", f, f.getStatus());
            }
            System.out.println();
        }
    }

    private static void statusFlow() {
        System.out.println("\n--- STORAGE STATUS ---");
        System.out.println(STORAGE.statusReport());
        System.out.println();
    }

    private static void demoFlow() {
        System.out.println("\n--- LOADING DEMO DATA ---");
        Object[][] demo = {
            { "Burger", 180.5, 3 },
            { "Pizza",  450.0, 5 },
            { "Fries",  120.0, 1 }
        };
        int added = 0;
        for (Object[] d : demo) {
            try {
                STORAGE.add(new FoodItem((String) d[0], (Double) d[1],
                        LocalDate.now().plusDays((Integer) d[2])));
                added++;
            } catch (IllegalArgumentException e) {
                System.out.println("  !! " + e.getMessage());
            }
        }
        System.out.printf("  %d demo item(s) added. Storage: %d/%d%n%n",
                added, STORAGE.size(), FoodStorage.MAX_CAPACITY);
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SC.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("  !! Please type a whole number.");
            }
        }
    }
}
