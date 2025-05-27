import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();
    private static TTL ttl = new TTL();
    private static clearscreen cls = new clearscreen();
    private static ArrayList<Order> allOrders = new ArrayList<>();
    private static int orderCount = 0;
    private static String name;

    public static void main(String[] args) {
        ttl.JAYTITLE();
        System.out.println("\n\n\n\n");
        System.out.print("Please enter your name: ");
        name = scanner.nextLine().trim();
        cls.clearScreen();
        ttl.JAYTITLE();

        JAY();
    }

    private static void JAY() {
        String choice;

        while (true) {
            System.out.println("\nHello, " + name + "! What do you want to do?");
            System.out.println("1. Make an Order");
            System.out.println("2. View Orders");
            System.out.println("3. Update Order");
            System.out.println("4. Exit");
            System.out.print("Enter choice (1-4): ");
            choice = scanner.nextLine().trim();

            if (!choice.isEmpty()) {
                choice = choice.substring(0, 1);
            }

            switch (choice) {
                case "1":
                    cls.clearScreen();
                    handleMakeOrder();
                    break;
                case "2":
                    cls.clearScreen();
                    handleViewOrders();
                    break;
                case "3":
                    cls.clearScreen();
                    handleUpdateOrder();
                    break;
                case "4":
                    cls.clearScreen();
                    ttl.JAYTITLE();
                    System.out
                            .println("                    Thank you for using our Billing System, " + name + "!\n\n\n");
                    System.exit(0);
                    break;
                default:
                    cls.clearScreen();
                    ttl.JAYTITLE();
            }
        }
    }

    private static void handleMakeOrder() {
        do {
            Order order = new Order(menu);
            boolean success = order.takeOrder();
            if (success) {
                order.printReceipt();
                allOrders.add(order);
                orderCount++;
            } else {
                System.out.println("\nNo items were added. Order cancelled.");
            }

            String again;
            while (true) {
                System.out.print("Do you want to make another order? (y/n): ");
                again = scanner.nextLine().trim().toLowerCase();
                if (again.equals("y")) {
                    cls.clearScreen();
                    break;
                } else if (again.equals("n")) {
                    cls.clearScreen();
                    ttl.JAYTITLE();
                    return;
                } else {
                    System.out.println("Invalid input. Please type 'y' or 'n'.");
                }
            }
        } while (true);
    }

    private static void handleViewOrders() {
        if (allOrders.isEmpty()) {
            System.out.println("                        No orders have been made yet.\n");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            cls.clearScreen();
            ttl.JAYTITLE();
            return;
        }

        double grandTotal = 0;
        for (int i = 0; i < allOrders.size(); i++) {
            System.out.println("                   ================== ORDER #" + (i + 1) + "==================");
            allOrders.get(i).viewOrders();
            grandTotal += allOrders.get(i).getTotal();
        }
        System.out.printf("                   ==============GRAND TOTAL: %.2f==============\n\n", grandTotal);
        System.out.print("\n\n\n\nPress Enter to continue...");
        scanner.nextLine();
        cls.clearScreen();
        ttl.JAYTITLE();
    }

private static void handleUpdateOrder() {
    if (allOrders.isEmpty()) {
        System.out.println("                        No orders available to update.\n");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        cls.clearScreen();
        ttl.JAYTITLE();
        return;
    }

    for (int i = 0; i < allOrders.size(); i++) {
        System.out.println(
                "                   =================== ORDER #" + (i + 1) + "==================");
        allOrders.get(i).viewOrders();
    }

    while (true) {
        System.out.print("Enter order number to update (1 to " + allOrders.size() + ") or 'x' to exit: ");
        String input = scanner.nextLine();

        if (input.matches("\\d+|x")) {
            if (input.equals("x")) {
                cls.clearScreen();
                ttl.JAYTITLE();
                break;
            }

            int orderNum = Integer.parseInt(input);
            if (orderNum >= 1 && orderNum <= allOrders.size()) {
                    Order selectedOrder = allOrders.get(orderNum - 1);
                    selectedOrder.updateOrder();
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    cls.clearScreen();
                    ttl.JAYTITLE();
                break;
                
            } else {
                System.out.println("Invalid order number.");
            }
        } else {
            System.out.println("Invalid input. Must be a number or X to exit.");
        }
    }
}

}