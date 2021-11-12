package Use_case;

import Entities.Food;
import Entities.FoodMenu;
import Entities.FoodTruck;
import Entities.Order;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A Use_case.OrderManager that manages all the Orders.
 */

public class OrderManager implements CommandExecutable {

    private static HashMap<String, Order> orders; // a Hashmap mapping FoodTrucks' id to the FoodTrucks.

    /**
     * @param current_orders a map that maps s' id to the Entities.Order objects.
     *                       <p>
     *                       Create a Use_case.OrderManager with the given Orders.
     */
    public OrderManager(HashMap<String, Order> current_orders) {
        orders = current_orders;
    }

    /**
     * Create a Use_case.OrderManager with no given FoodTrucks.
     */
    public OrderManager() {
        orders = new HashMap<>();
    }

    /**
     * Create a unique id (0~999999) for the order and add it to the list.
     *
     * @param foodTruck      the foodtruck that is responsible for this order
     * @param foodList       a list of foods ordered by the customers
     * @param customerName   name of the customer who ordered the food
     * @param customerNumber contact number of the customer who ordered the food
     * @param sellerName     name of the seller who owns the food truck
     * @param sellerNumber   contact number of the seller who owns the food truck
     * @return the id of new order
     */

    public int createOrder(FoodTruck foodTruck, ArrayList<Food> foodList, String customerName,
                          String customerNumber, String sellerName, String sellerNumber) {
        int id = ThreadLocalRandom.current().nextInt(0, 999999 + 1);
        while (orders.containsKey(Integer.toString(id))) {
            id = ThreadLocalRandom.current().nextInt(0, 999999 + 1);
        }
        Order new_order = new Order(id, foodTruck, foodList, customerName,
                customerNumber, sellerName, sellerNumber);
        orders.put(Integer.toString(id), new_order);
        return id;
    }

    /**
     * Create a unique id (0~999999) for the order and add it to the list.
     *
     * @param trucks A FoodTruckManager stores all trucks.
     * @param truckName The truck name of the truck
     * @param foods       a list of foods' name ordered by the customers
     * @param customerName   name of the customer who ordered the food
     * @param customerNumber contact number of the customer who ordered the food
     * @param sellerName     name of the seller who owns the food truck
     * @param sellerNumber   contact number of the seller who owns the food truck
     * @return the id of new order
     */

    public int createOrder(FoodTruckManager trucks, String truckName, ArrayList<String> foods, String customerName,
                          String customerNumber, String sellerName, String sellerNumber) {
        FoodTruck foodTruck = trucks.getFoodTruckById(truckName);
        ArrayList<Food> foodList = getMenuFood(foods, foodTruck);
        return createOrder(foodTruck, foodList, customerName, customerNumber, sellerName, sellerNumber);
    }

    /**
     * Change the specific order's status.
     *
     * @param id the id of the specific order
     * @return true if the order status being changed successfully.
     */
    public boolean changeOrderStatus(String id) {
        return orders.get(id).changeOrderStatus();
    }

    /**
     * @param foods the list of foods' name
     * @param truck where these foods from
     * @return An ArrayList of Entities.Food from the given foods' names.
     */
    public ArrayList<Food> getMenuFood(ArrayList<String> foods, FoodTruck truck) {
        FoodMenu menu = truck.getMenu();
        ArrayList<Food> wish_food = new ArrayList<>();
        for (String item : foods) {
            wish_food.add(menu.createCopy(item));
        }
        return wish_food;
    }

    /**
     * @param foods the list of foods' name
     * @param trucks A FoodTruckManager stores all trucks.
     * @param truckName The truck name of the truck
     * @return An ArrayList of Entities.Food from the given foods' names.
     */
    public ArrayList<Food> getMenuFood(ArrayList<String> foods, FoodTruckManager trucks, String truckName) {
        FoodTruck truck = trucks.getFoodTruckById(truckName);
        return getMenuFood(foods, truck);
    }

    /**
     * @param foods the list of foods' name
     * @param truck where these foods from
     * @return The total price of the given food in the truck
     */
    public double getTotalPrice(ArrayList<String> foods, FoodTruck truck) {
        FoodMenu menu = truck.getMenu();
        double total_price = 0;
        for (String item : foods) {
            total_price += menu.createCopy(item).getPrice();
        }
        return total_price;
    }

    /**
     * @param foods the list of foods' name
     * @param trucks A FoodTruckManager stores all trucks.
     * @param truckName The truck name of the truck
     * @return The total price of the given food in the truck
     */
    public double getTotalPrice(ArrayList<String> foods, FoodTruckManager trucks, String truckName) {
        FoodTruck truck = trucks.getFoodTruckById(truckName);
        return getTotalPrice(foods, truck);
    }

    /**
     * @param id the Entities.Order's id.
     * @return A map that from the Entities.Order's id to the Entities.Order's information. If the Entities.Order doesn't
     * exist, return an empty map.
     */
    public HashMap<String, String> getOrderDetail(int id) {
        HashMap<String, String> information = new HashMap<>();
        if (orders.containsKey(String.valueOf(id))) {
            information.put(String.valueOf(id), getOrder(id).toString());
        }
        return information;
    }

    /**
     * @param id the Entities.Order's id.
     * @return The order with the given id.
     */
    public Order getOrder(int id) {
        return orders.get(String.valueOf(id));
    }

    @Override
    public HashMap<String, Method> getAvailableCommands() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public void constructOrderDataBase() throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./data/order info"));
            orders = (HashMap<String, Order>) ois.readObject();
            ois.close();
        }catch (EOFException e){
            // Do nothing, no order has been created
        }
    }

    public void saveOrderDataBase() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./data/order info.txt"));
        oos.writeObject(orders);
        oos.flush();
        oos.close();
    }
}
