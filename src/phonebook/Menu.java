/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook;

import phonebook.handlers.SearchContactHandler;
import phonebook.handlers.CreateContactHandler;
import phonebook.contracts.ContactHandler;
import phonebook.handlers.AllContactHandler;
import phonebook.helpers.MessageHelper;
import java.util.Hashtable;
import java.util.Set;
import phonebook.database.ContactRepository;
import phonebook.handlers.CountContactHandler;
import phonebook.handlers.ExitPhoneBookHandler;

/**
 * This class holds the logic for displaying the menu
 * items for the phone book and also handles the selected menu
 * @author gabriel nwogu
 */
public class Menu {
    
    //Here I create static constants for the menu names
    private static final String CREATE = "Create Contact";
    
    private static final String ALL = "All Contacts";
    
    private static final String SEARCH = "Search Contact";
    
    private static final String COUNT = "Count Total Contacts";
    
    private static final String EXIT = "Exit Phone Book";
    
    private static Menu instance;
    
    /**
     * @var Hash table to hold the different handlers for each menu item
     */
    private static Hashtable<String, ContactHandler> menuHandlers;
    
    /**
     * A private constructor
     */
    private Menu(){}
    
    /**
     * This returns an instance of the Menu class. 
     * I want to return only one instance of this class
     * anytime it is been called.
     * 
     * @return Menu
     */
    public static Menu getInstance()
    {
        if (Menu.instance == null) {
            Menu.instance = new Menu();
            
            Menu.instance.setHandlers();
        }
        
        return Menu.instance;
    }
    
    /**
     * This method checks the input of the user 
     * to see that it matches a given menu item
     * 
     * @param input
     */
    public void parseInput(String input)
    {
        Hashtable menuItems = this.menuItems();
        
        Set<String> menuKeys = menuItems.keySet();
        
        if (! menuKeys.contains(input)) {
            MessageHelper.printError("Invalid menu item selected");
        }
    }
    
    /**
     * This method displays the menu item to the screen
     */
    public void displayMenu()
    {
        //Get all the menu items, loop through them and display to the screen
        
        Hashtable menuItems = this.menuItems();
        
        System.out.println("Phone Book Menu");
        
        System.out.println("================");
        
        menuItems.forEach((menu, item)->System.out.println(
                menu + ": " + item + "\n" + "================"
        ));
        
        System.out.println("Select Menu. eg: 1, 2, 3" + "\n");
    }
    
    /**
     * This method holds the 5 menu items available
     * on this phone book
     * 
     * @return menuItem
     */
    public Hashtable<String, String> menuItems()
    {
        Hashtable<String, String> menuItem = new Hashtable<>();
        menuItem.put("1", Menu.ALL);
        menuItem.put("2", Menu.CREATE);
        menuItem.put("3", Menu.SEARCH);
        menuItem.put("4", Menu.COUNT);
        menuItem.put("5", Menu.EXIT);
        
        return menuItem;
    }
    
    /**
     * This method holds the menu handlers for each menu item
     * It maps each menu item to a menu handler
     * 
     * @return menuHandler
     */
    public Hashtable<String, ContactHandler> menuHandlers()
    {
        Hashtable<String, ContactHandler> menuHandler = new Hashtable<>();
        
        ContactRepository repository = new ContactRepository();
        
        menuHandler.put("1", new AllContactHandler(repository));
        menuHandler.put("2", new CreateContactHandler(repository));
        menuHandler.put("3", new SearchContactHandler(repository));
        menuHandler.put("4", new CountContactHandler(repository));
        menuHandler.put("5", new ExitPhoneBookHandler(repository));
        
        return menuHandler;
    }
    
    /**
     * This method sets the handlers to the static variable
     * 
     */
    private void setHandlers()
    {
        Menu.menuHandlers = this.menuHandlers();
    }
    
    /**
     * This returns a single menu handler based on the user input
     * 
     * @param input
     * @return ContactHandler
     */
    public ContactHandler getHandler(String input)
    {
        this.parseInput(input);
        
        return (ContactHandler) Menu.menuHandlers.get(input);
        
        
    }
    
}
