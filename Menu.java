public class Menu {
    private Food[] foods;
    private Drink[] drinks;

    public Menu() {
        foods = new Food[6];
        foods[0] = new Food("Adobong Manok", 60);
        foods[1] = new Food("Sinigang Baboy", 60);
        foods[2] = new Food("Paksiw na Bangus", 60);
        foods[3] = new Food("Fried Galunggong", 50);
        foods[4] = new Food("Bicol Express", 60);
        foods[5] = new Food("Rice", 15);

        drinks = new Drink[6];
        drinks[0] = new Drink("Sprite (bottle)", 13);
        drinks[1] = new Drink("Coke (bottle)", 15);
        drinks[2] = new Drink("Royal (bottle)", 15);
        drinks[3] = new Drink("Iced Tea (glass)", 10);
        drinks[4] = new Drink("Gulaman (glass)", 10);
        drinks[5] = new Drink("Mekus Mekus (glass)", 15);

    }

    public void displayMenu() {
        System.out.printf("         "+"%-5s %-18s %-10s     %-5s %-19s %-20s\n",
                "Code", "Food Item", "Price", "Code", "Drink Item", "Price");
        System.out.println(
                "         ------------------------------------------------------------------------");

        for (int i = 0; i < 6; i++) {
            String foodCode = "m" + (i + 1);
            String foodName = foods[i].getName();
            double foodPrice = foods[i].getPrice();

            String drinkCode = "";
            String drinkName = "";
            double drinkPrice = 0;

            if (i < drinks.length) {
                drinkCode = "d" + (i + 1);
                drinkName = drinks[i].getName();
                drinkPrice = drinks[i].getPrice();
            }

            System.out.printf("         "+"%-5s %-18s %-10.2f     %-5s %-19s %-14.2f\n",
                    foodCode, foodName, foodPrice, drinkCode, drinkName, drinkPrice);
        }
    }

    public Food[] getFoods() {
        return foods;
    }

    public Drink[] getDrinks() {
        return drinks;
    }
    
}
