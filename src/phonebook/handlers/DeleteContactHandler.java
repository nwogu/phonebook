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

/**
 * This class handles the deletion of contacts in the repository
 * 
 * @author gabriel nwogu
 */
public class DeleteContactHandler extends ContactHandler{
    
    private Contact contact;
    
    public DeleteContactHandler(ContactRepository repository)
    {
        //Call the parent constructor to set the repository
        super(repository);
    }
    
    @Override
    public void handle(Scanner scanner)
    {
        System.out.println("Deleting contact.......");
        
        //Delete the selected contact and save to database
        this.repository().delete(this.contact).offloadContacts();
        
        System.out.println("Contact deleted........");
        
        //Create a new instance of the contact display helper class
        ContactDisplayHelper contactDisplay = new ContactDisplayHelper(
                this.repository()
        );
        
        //Display all the contacts
        contactDisplay.print(scanner);
    }
    
    /**
     * Set the contact to be deleted
     *
     * @param contact
     * @return
     */
    public DeleteContactHandler setContact(Contact contact)
    {
        this.contact = contact;
        
        return this;
    }
    
}
