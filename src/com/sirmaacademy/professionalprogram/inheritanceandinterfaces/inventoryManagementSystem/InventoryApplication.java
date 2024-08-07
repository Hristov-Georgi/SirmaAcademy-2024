package com.sirmaacademy.professionalprogram.inheritanceandinterfaces.inventoryManagementSystem;

import com.sirmaacademy.professionalprogram.inheritanceandinterfaces.inventoryManagementSystem.enums.CardType;
import com.sirmaacademy.professionalprogram.inheritanceandinterfaces.inventoryManagementSystem.service.Card;
import com.sirmaacademy.professionalprogram.inheritanceandinterfaces.inventoryManagementSystem.service.implementation.CardClass;
import com.sirmaacademy.professionalprogram.inheritanceandinterfaces.inventoryManagementSystem.service.initialLoad.InitialData;
import com.sirmaacademy.professionalprogram.inheritanceandinterfaces.inventoryManagementSystem.service.ShoppingCart;
import com.sirmaacademy.professionalprogram.inheritanceandinterfaces.inventoryManagementSystem.service.implementation.InventoryItem;
import com.sirmaacademy.professionalprogram.inheritanceandinterfaces.inventoryManagementSystem.service.implementation.ShoppingCartData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryApplication {

    public static void main(String[] args) {

        /*
         * This list stores inventory items that have incorrect data or are entered in different format.
         * The information may be used to revise items data in storage file if needed.
         */
        List<String> wrongDataFormat = new ArrayList<>();

        /*
         * The list stores expired products and should be used for easier product handling if needed.
         */
        List<InventoryItem> expiredProductsList = new ArrayList<>();

        /*
         * Map stores all correctly entered and non expired products from data file.
         */
        Map<Integer, InventoryItem> inventoryStorageMap = InitialData.loadItems(wrongDataFormat, expiredProductsList);

        InitialData.printIndexPage(inventoryStorageMap);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(System.out)) {

            ShoppingCart shoppingCart = new ShoppingCartData();

            String input = reader.readLine().trim();

            while (!input.equals("exit")) {

                String[] command = input.split("\\s+");

                switch (command[0]) {

                    /*
                     * Adds item by id and quantity to the shopping cart. For example: add 11111 5 and then print
                     * if the item is added successfully or not. If not print why operation is not successful.
                     */
                    case "add":

                        try {
                            int id = Integer.parseInt(command[1]);
                            double quantity = Double.parseDouble(command[2]);

                            System.out.println("Item: \"" +
                                    shoppingCart.addItemToCart(inventoryStorageMap, id, quantity) +
                                    "\" added to shopping cart.");


                        } catch (ArrayIndexOutOfBoundsException ex) {
                            System.out.println("Enter product quantity.");

                        } catch (NumberFormatException ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("Enter valid id or product quantity. Check products list.");

                        } catch (NullPointerException e) {
                            System.out.println(e.getMessage());

                        }

                        break;

                        /*
                        * Sort and displays available items. Sort can be executed by name, id, category or price.
                         */
                    case "sort":

                        if (command[1].equals("name")) {

                            InitialData.sortByName(inventoryStorageMap);

                        } else if (command[1].equals("id")) {

                            InitialData.sortById(inventoryStorageMap);

                        } else if (command[1].equals("category")) {

                            InitialData.sortByCategory(inventoryStorageMap);

                        } else if (command[1].equals("price")) {

                            InitialData.sortByPrice(inventoryStorageMap);

                        } else {

                            System.out.println("Invalid sort command. Please check your input and make sure that every word is free space separated. See Menu for reference.");
                        }

                        break;

                        /*
                        * Remove item by id from shopping cart. For example: remove 11111
                        * if operation is not successful print reason on console.
                         */

                    case "remove":
                        try {
                            int idToRemove = Integer.parseInt(command[1]);

                            System.out.println("Item: \"" +
                                    shoppingCart.removeItemFromCart(idToRemove) +
                                    "\" was removed from shopping cart.");

                        } catch (NullPointerException ex) {
                            System.out.println("Enter valid id number. Id should be five digits long.");

                        } catch (NumberFormatException ex) {
                            System.out.println("Enter valid id number. Id should be five digits long.");
                            break;
                        }

                        break;

                        /*
                        * Print all items in the shopping cart.
                         */

                    case "cart":
                        try {
                            shoppingCart.printOrderedItems();

                        } catch (NullPointerException ex) {
                            System.out.println(ex.getMessage());
                        }

                        break;

                        /*
                        * Get and validate card details needed for payment process.
                        * Place new order. Return order number. Print ordered items and total cost.
                        * Clears shopping cart if order is successful and write order details to text file named
                        * with order number.
                         */

                    case "order":

                        Card card = null;

                        writer.println("Enter card type: VISA or MASTERCARD.");
                        writer.println("Enter first and last name from card.");
                        writer.println("Enter card number without space.");
                        writer.println("Enter card security code.");
                        writer.println("All data should be on new line in the given order!");
                        writer.flush();

                        boolean isTrue = true;
                        while (isTrue) {

                            String cardType = reader.readLine();
                            String names = reader.readLine();
                            String cardNumber = reader.readLine();
                            String securityCode = reader.readLine();

                            if (cardType.toUpperCase().equals(CardType.VISA.name()) ||
                                    cardType.toUpperCase().equals(CardType.MASTERCARD.name())) {

                                try {

                                    card = new CardClass(names, cardType, cardNumber, securityCode);
                                    isTrue = false;

                                } catch (IllegalArgumentException ex) {
                                    System.out.println("Invalid card details. Please check and enter again.");
                                    System.out.println(ex.getMessage());
                                }

                            }

                        }

                        try {
                            System.out.println("Order: " + shoppingCart.placeOrder(card) + " placed successfully");
                            shoppingCart.printOrderedItems();
                            System.out.println("Total cost: " + shoppingCart.getTotalCost());
                            shoppingCart.clearCart();

                            InitialData.writeItemsToFile(inventoryStorageMap);

                        } catch (NullPointerException ex) {
                            System.out.println(ex.getMessage());
                        }

                        break;

                        /*
                        * Print current message if incorrect command is entered.
                         */

                    default:

                        System.out.println("Incorrect command! Please check your input and make sure that every word is separated by free space.");
                        break;

                }


                input = reader.readLine();
            }


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
