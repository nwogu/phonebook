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
 * This class handles the editing of contacts in the repository
 * 
 * @author gabriel nwogu
 */
public class ModifyContactHandler extends ContactHandler {
    
    private Contact contact;
    
    public ModifyContactHandler(ContactRepository repository)
    {
        //Call the parent constructor to set the repository
        super(repository);
    }
    
    /**
     * Set the contact to be modified
     * 
     * @param contact
     * @return ModifyContactHandler
     */
    public ModifyContactHandler setContact(Contact contact)
    {
        this.contact = contact;
        
        return this;
    }
    
    @Override
    public void handle(Scanner scanner)
    {
        scanner.useDelimiter("\n");
        
        //Collect all the contact information
        //If the input is empty, use the original value instead
        System.out.println("First Name: \n");
        
        String firstName = scanner.next();
        firstName = firstName.isEmpty() ? this.contact.firstName : firstName;
        
        System.out.println("Last Name: \n");
        
        String lastName = scanner.next();
        lastName = lastName.isEmpty() ? this.contact.lastName : lastName;
        
        System.out.println("Address: \n");
        
        String address = scanner.next();
        address = address.isEmpty() ? this.contact.address : address;
        
        System.out.println("City: \n");
        
        String city = scanner.next();
        city = city.isEmpty() ? this.contact.city : city;
        
        System.out.println("Phone Number: \n");
        
        String phoneNumber = scanner.next();
        
        //If the user is changing the phone number, validate
        //that the number is unique
        if (! phoneNumber.isEmpty()) {
            
            this.validateInputs(
                firstName, 
                lastName, 
                city, 
                address, 
                phoneNumber
            );
        }
        
        //create new contact instance for the update
        Contact contact = new Contact(
                firstName, lastName, city, 
                address, phoneNumber
        );
        
        //Save update to the database
        this.repository().update(this.contact, contact).offloadContacts();
        
        //Create new contact display helper
        ContactDisplayHelper display = new ContactDisplayHelper(
                this.repository(), this.repository().all().indexOf(contact)
        );
        
        //display the recently modified contact
        display.print(scanner);
    }
    
    /**
     * Validate the user input
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
    ) {
        
        //Check that all the fields are not empty
        if (
                firstName.isEmpty() ||
                lastName.isEmpty() || 
                city.isEmpty() || 
                address.isEmpty() || 
                phoneNumber.isEmpty()
        ) {
            MessageHelper.printError("All fields are required");
        }
        
        //Check that the phone number is unique in the database
        if (this.repository().where("phoneNumber", phoneNumber).exists()) {
            MessageHelper.printError("phoneNumber already exists");
        }
    }
}
