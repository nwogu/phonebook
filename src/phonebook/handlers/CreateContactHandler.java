/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook.handlers;

import phonebook.contracts.ContactHandler;
import java.util.Scanner;
import phonebook.Contact;
import phonebook.database.ContactRepository;
import phonebook.helpers.ContactDisplayHelper;
import phonebook.helpers.MessageHelper;

/**
 * This class handles the creation of a new contact in the repository
 * 
 * @author gabriel nwogu
 */
public class CreateContactHandler extends ContactHandler {
    
    public CreateContactHandler(ContactRepository repository)
    {
        //Call the parent constructor to set the repository
        super(repository);
    }
    
    
    @Override
    public void handle(Scanner scanner)
    {
        //Collect all the contact information
        scanner.useDelimiter("\n");
        
        System.out.println("First Name: \n");
        
        String firstName = scanner.next();
        
        System.out.println("Last Name: \n");
        
        String lastName = scanner.next();
        
        System.out.println("Address: \n");
        
        String address = scanner.next();
        
        System.out.println("City: \n");
        
        String city = scanner.next();
        
        System.out.println("Phone Number: \n");
        
        String phoneNumber = scanner.next();
        
        //Validate the inputs given by the user
        this.validateInputs(
                firstName, 
                lastName, 
                city, 
                address, 
                phoneNumber
        );
        
        //Create a new contact object from the given information
        Contact contact = new Contact(
                firstName, lastName, city, 
                address, phoneNumber
        );
        
        //Add the contact to the repository and save to the database
        this.repository().add(contact).offloadContacts();
        
        //Create an instance of the contacts display helper
        ContactDisplayHelper display = new ContactDisplayHelper(
                this.repository(), this.repository().all().indexOf(contact)
        );
        
        //Display newly created contact information
        display.print(scanner);
    }
    
    /**
     * Validate the given input for contacts
     *
     * @param firstName
     * @param lastName
     * @param city
     * @param address
     * @param phoneNumber
     */
    private void validateInputs(
            String firstName, 
            String lastName, 
            String city, 
            String address, 
            String phoneNumber
    )
    {
        //Checks that all the values are not empty
        if (
                firstName.isEmpty() ||
                lastName.isEmpty() || 
                city.isEmpty() || 
                address.isEmpty() || 
                phoneNumber.isEmpty()
        ) {
            MessageHelper.printError("All fields are required");
        }
        
        //Checks that the phone number is unique to the database
        if (this.repository().where("phoneNumber", phoneNumber).exists()) {
            MessageHelper.printError("phoneNumber already exists");
        }
    }
}
