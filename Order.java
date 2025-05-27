import java.util.Scanner;

public class Order {
    private Menu menu;
    private int[] foodQty;
    private int[] drinkQty;
    clearscreen cls = new clearscreen();

    private void handleOrder(Scanner scanner, String itemName, int index, int[] qtyArray, String type) {
        System.out.println(itemName + " is chosen.");
        System.out.print("Enter quantity: ");

        if (scanner.hasNextInt()) {
            int qty = scanner.nextInt();
            scanner.nextLine();

            if (qty > 0) {
                if (confirmItem(scanner, qty, itemName)) {
                    qtyArray[index] += qty;
                    System.out.println(itemName + " is added.");
                } else {
                    System.out.println("Item not added.");
                }
            } else {
                System.out.println("Quantity must be positive and not 0.");
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
    }

    private boolean confirmItem(Scanner scanner, int qty, String itemName) {
        while (true) {
            System.out.print("Are you sure you want to add " + qty + " x " + itemName + "? (y/n): ");
            String confirm = scanner.nextLine().toLowerCase().trim();
            if (confirm.equals("y"))
                return true;
            if (confirm.equals("n"))
                return false;
            System.out.println("Invalid input. Please enter 'y' or 'n' only.");
        }
    }

    private void updateItemQuantity(Scanner scanner, String itemName, int[] qtyArray, int index) {
        System.out.print("Enter new quantity for " + itemName + ": ");
        if (scanner.hasNextInt()) {
            int qty = scanner.nextInt();
            scanner.nextLine();
            if (qty >= 0) {
                qtyArray[index] = qty;
                System.out.println("Updated successfully.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                cls.clearScreen();
            } else {
                System.out.println("Quantity must be 0 or more.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                cls.clearScreen();
            }
        } else {
            System.out.println("Invalid input. Quantity must be a number.");
                scanner.nextLine();
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                cls.clearScreen();
        }
    }

    public Order(Menu menu) {
        this.menu = menu;
        foodQty = new int[6];
        drinkQty = new int[6];
    }

    public boolean takeOrder() {
        Scanner scanner = new Scanner(System.in);
        String code = "";
        boolean orderStarted = false;

        System.out.println("                                 --- Menu Reference ---");
        menu.displayMenu();
        System.out.println("\nType the item code (like m1 or d2). Type 'x' to finish your order.");

        while (true) {
            System.out.print("Enter code: ");
            code = scanner.next().toLowerCase();

            if (!code.matches("m[1-6]|d[1-6]|x")) {
                System.out.println("Invalid code. Please enter a valid code (e.g., m1, d2) or 'x' to exit.");
                scanner.nextLine();
                continue;
            }

            if (code.equals("x"))
                break;

            if (!orderStarted) {
                resetOrder();
                orderStarted = true;
            }

            int index = Integer.parseInt(code.substring(1)) - 1;
            scanner.nextLine();

            switch (code.charAt(0)) {
                case 'm':
                    handleOrder(scanner, menu.getFoods()[index].getName(), index, foodQty, "food");
                    break;
                case 'd':
                    handleOrder(scanner, menu.getDrinks()[index].getName(), index, drinkQty, "drink");
                    break;
            }
        }
        return orderStarted;
    }

    public void resetOrder() {
        for (int i = 0; i < foodQty.length; i++) {
            foodQty[i] = 0;
        }
        for (int i = 0; i < drinkQty.length; i++) {
            drinkQty[i] = 0;
        }
    }

    public void printReceipt() {
    cls.clearScreen();
    double total = 0;
    double payment = 0;
    double change = 0;

    Food[] foods = menu.getFoods();
    Drink[] drinks = menu.getDrinks();

    Scanner scanner = new Scanner(System.in);

    System.out.println("                   =================== RECEIPT ==================");
    System.out.println();
    System.out.printf("                    %-6s %-25s %-5s %-10s\n", "Code", "Item", "Qty", "Price");
    System.out.println("                    ----------------------------------------------");

    for (int i = 0; i < foods.length; i++) {
        if (foodQty[i] > 0) {
            String code = "m" + (i + 1);
            double cost = foodQty[i] * foods[i].getPrice();
            System.out.printf("                    %-6s %-25s %-5d %-10.2f\n", code, foods[i].getName(),
                    foodQty[i], foods[i].getPrice());
            total += cost;
        }
    }

    for (int i = 0; i < drinks.length; i++) {
        if (drinkQty[i] > 0) {
            String code = "d" + (i + 1);
            double cost = drinkQty[i] * drinks[i].getPrice();
            System.out.printf("                    %-6s %-25s %-5d %-10.2f\n", code, drinks[i].getName(),
                    drinkQty[i], drinks[i].getPrice());
            total += cost;
        }
    }

    System.out.println("                    ----------------------------------------------");
    System.out.printf("                    %-22s %-4s %-8s %-10.2f\n", "", "", "Total  : P", total);

    while (true) {
        System.out.print("                    Enter payment amount: P");
        if (scanner.hasNextDouble()) {
            payment = scanner.nextDouble();
            scanner.nextLine();
            if (payment >= total) {
                change = payment - total;
                break;
            } else {
                System.out.println("                    Insufficient payment. Please enter at least P" + total);
            }
        } else {
            System.out.println("                    Invalid input. Please enter a number.");
            scanner.nextLine();
        }
    }

    System.out.printf("                    %-22s %-4s %-8s %-10.2f\n", "", "", "Payment: P", payment);
    System.out.printf("                    %-22s %-4s %-8s %-10.2f\n", "", "", "Change : P", change);
    System.out.println("                   =================================================");
    System.out.println("                     Thank you for ordering from Kainan ni Jay!");
    System.out.println("                   =================================================");
    System.out.println();
}

    public void viewOrders() {
        double total = 0;
        Food[] foods = menu.getFoods();
        Drink[] drinks = menu.getDrinks();
        System.out.printf("                    " + "%-6s %-25s %-5s %-10s\n", "Code", "Item", "Qty", "Price");
        System.out.println("                   ----------------------------------------------");
        boolean hasOrders = false;

        for (int i = 0; i < foods.length; i++) {
            if (foodQty[i] > 0) {
                hasOrders = true;
                String code = "m" + (i + 1);
                double cost = foodQty[i] * foods[i].getPrice();
                System.out.printf("                    " + "%-6s %-25s %-5d %-10.2f\n", code, foods[i].getName(),
                        foodQty[i], foods[i].getPrice());
                total += cost;
            }
        }

        for (int i = 0; i < drinks.length; i++) {
            if (drinkQty[i] > 0) {
                hasOrders = true;
                String code = "d" + (i + 1);
                double cost = drinkQty[i] * drinks[i].getPrice();
                System.out.printf("                    " + "%-6s %-25s %-5d %-10.2f\n", code, drinks[i].getName(),
                        drinkQty[i], drinks[i].getPrice());
                total += cost;
            }
        }

        if (!hasOrders) {
            System.out.println("                                No items in the current order yet.");
        }

        System.out.println("                   ----------------------------------------------");
        System.out.printf("                " + "%-25s %-5s %-10s %-10.2f\n", "", "", "Total: P", total);
        System.out.println("                   ==============================================");
    }

    public double getTotal() {
        double total = 0;
        Food[] foods = menu.getFoods();
        Drink[] drinks = menu.getDrinks();
        for (int i = 0; i < foods.length; i++) {
            total += foodQty[i] * foods[i].getPrice();
        }
        for (int i = 0; i < drinks.length; i++) {
            total += drinkQty[i] * drinks[i].getPrice();
        }
        return total;
    }

    public double calculateTotal() {
    double total = 0.0;
    Food[] foods = menu.getFoods();
    Drink[] drinks = menu.getDrinks();

    for (int i = 0; i < foods.length; i++) {
        total += foods[i].getPrice() * foodQty[i];
    }

    for (int i = 0; i < drinks.length; i++) {
        total += drinks[i].getPrice() * drinkQty[i];
    }

    return total;
}

public void updateOrder() {
    Scanner scanner = new Scanner(System.in);
    Food[] foods = menu.getFoods();
    Drink[] drinks = menu.getDrinks();

    double oldTotal = calculateTotal();
    boolean updated = false;

    while (true) {
        cls.clearScreen();
        viewOrders();
        System.out.println();
        menu.displayMenu();

        System.out.print("Enter the item code to update quantity (e.g., m1 or d2), or 'x' to cancel: ");
        String code = scanner.nextLine().toLowerCase().trim();

        if (code.equals("x")) {
            break;
        }

        if (!code.matches("m[1-6]|d[1-6]")) {
            System.out.println("Invalid item code.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            continue;
        }

        int index = Integer.parseInt(code.substring(1)) - 1;
        char type = code.charAt(0);

        if (type == 'm' && index < foods.length) {
            updateItemQuantity(scanner, foods[index].getName(), foodQty, index);
            updated = true;
        } else if (type == 'd' && index < drinks.length) {
            updateItemQuantity(scanner, drinks[index].getName(), drinkQty, index);
            updated = true;
        } else {
            System.out.println("Invalid input. Must be a number.");
        }
    }

    if (!updated) {
        System.out.println("\n                        No changes were made to your order.");
        System.out.println("                   ====================================================");
        return;
    }

    double newTotal = calculateTotal();
    double difference = newTotal - oldTotal;

    System.out.println("\n                   ================ ORDER ADJUSTMENT ==================");
    if (difference > 0) {
        System.out.printf("                   Please pay this amount for the added items: P %.2f%n", difference);
    } else if (difference < 0) {
        System.out.printf("                   Please collect this amount for the removed items: P %.2f%n", -difference);
    } else {
        System.out.println("                   Quantities were updated but total remains the same.");
    }
    System.out.println("                   ====================================================");
}
}
