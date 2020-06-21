/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook.helpers;

import java.util.ArrayList;
import java.util.Scanner;
import phonebook.Contact;
import phonebook.Menu;
import phonebook.database.ContactRepository;
import phonebook.handlers.DeleteContactHandler;
import phonebook.handlers.ModifyContactHandler;

/**
 * This helper class handles the display of a contact
 * to the user screen. It allows for the contact to be
 * displayed one by one, with navigation options to
 * move to next, go back, modify or delete contact
 *
 * @author gabriel nwogu
 */
public class ContactDisplayHelper {
    
    //Array to hold the contacts objects
    private final ArrayList<Contact> contacts;
    
    //Index, used to know the current contact being displayed
    //while in the loop
    private int contactIndex = 0;
    
    // The contact repository
    private final ContactRepository repository;
    
    /**
     * This constructor collects a repository and an index
     * It is used to jump to a particular index while displaying
     * contacts
     *
     * @param repository
     * @param contactIndex
     */
    public ContactDisplayHelper(ContactRepository repository, int contactIndex)
    {
        this.contactIndex = contactIndex;
        this.contacts = repository.all();
        this.repository = repository;
    }
    
    /**
     * This constructor collects only the repository. It is used
     * when displaying contacts from the beginning, hence index will be zero
     * @param repository
     */
    public ContactDisplayHelper(ContactRepository repository)
    {
        this.contacts = repository.all();
        this.repository = repository;
    }
    
    /**
     * Print a contact information to the screen
     * @param scanner
     */
    public void print(Scanner scanner)
    {
        //Loop through each contact and display the contact information
        //to the screen.
        for (int index = this.contactIndex; this.contactIndex < this.contacts.size(); this.contactIndex++) {
            
            //We want the contact display to continue in a cycle except the user
            //returns to the main menu, so we check for the index and reset to zero
            //if the index is last or has gone behind.
            if ((this.contactIndex < 0) || (this.contactIndex >= this.contacts.size())) {
                
                this.contactIndex = 0;
            }
            
            //Get a contact by the current index
            Contact firstContact = this.contacts.get(this.contactIndex);

            String navigation = this.printContact(firstContact, scanner);
            
            this.handleNavigation(navigation, scanner);
        }
        
        //If the contact information is empty, return
        //the user to the main menu
        mainMenu(scanner);
        
    }
    
    /**
     * This method controls the navigation when a contact has 
     * been displayed based on the user input
     * 
     * @param navigation
     * @param scanner
     */
    private void handleNavigation(String navigation, Scanner scanner)
    {
        switch (navigation) {
            
            /**
             * If the user selects to go back, we decrease the counter by
             * 2 so that the loop proceeds backwards
             */
            case "1":
                this.contactIndex -= 2;
                break;
                
            /**
             * If the user selects to go next, we check if this is the last
             * index. If yes, we set the index to -1 so that when it increments
             * in the loop, it will start from the beginning.
             */
            case "2":
                this.contactIndex = this.contactIndex >= (this.contacts.size() - 1) ? 
                        -1 : this.contactIndex;
                break;
                
            /**
             * If the user selects to modify the contact, we create an instance
             * of the ModifyContactHandler. Get the current contact by its index,
             *  and set it on the ModifyContactHandler instance.
             */
            case "3":
                ModifyContactHandler modifyHandler = new ModifyContactHandler(this.repository);
                modifyHandler.setContact(
                        this.contacts.get(this.contactIndex)
                ).handle(scanner);
                break;
                
            /**
             * If the user selects to delete the contact, we create an instance
             * of the DeleteContactHandler. Get the current contact by its index,
             *  and set it on the DeleteContactHandler instance.
             */
            case "4":
                DeleteContactHandler deleteHandler = new DeleteContactHandler(this.repository);
                deleteHandler.setContact(
                        this.contacts.get(this.contactIndex)
                ).handle(scanner);
                break;
                
             /**
              * By default, if no valid option is selected, we return the user
              * to the main menu
              */
            default:
                mainMenu(scanner);
                
        }
    }
    
    /**
     * This method prints the contact info to the screen
     *
     * @param contact
     * @param scanner
     * 
     * @return input
     */
    private String printContact(Contact contact, Scanner scanner)
    {
        System.out.println("====================================================================");
        
        System.out.println("First Name      Last Name       Address     City        Phone Number");
        
        System.out.println("====================================================================");
        
        System.out.println(contact.firstName + " || " + contact.lastName + " || " + contact.address + " || " + contact.city + " || " + contact.phoneNumber);
            
        System.out.println("====================================================================");
        
        System.out.println("1. Back     2. Next    3. Modify    4. Delete   5. Main Menu");
        
        return scanner.next();
    }
    
    /**
     * This method returns the user to the main menu
     *
     * @param scanner
     */
    public static void mainMenu(Scanner scanner)
    {
        System.out.println("Press enter to go back to main menu");
        
        scanner.useDelimiter("\n");
        
        scanner.next();
        
        System.out.println("Returning to main menu....");
        
        Menu menu = Menu.getInstance();
        
        menu.displayMenu();
        
        String input = scanner.next();
        
        menu.getHandler(input).handle(scanner);
    }
}
