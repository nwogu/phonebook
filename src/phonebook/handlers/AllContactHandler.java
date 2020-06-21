/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook.handlers;

import java.util.Scanner;
import phonebook.contracts.ContactHandler;
import phonebook.database.ContactRepository;
import phonebook.helpers.ContactDisplayHelper;

/**
 * This class handles the display of all contacts in the repository
 * 
 * @author gabriel nwogu
 */
public class AllContactHandler extends ContactHandler{
    
    public AllContactHandler(ContactRepository repository)
    {
        //Call the parent constructor to set the repository
        super(repository);
    }
    
    @Override
    public void handle(Scanner scanner)
    {
        System.out.println("Listing All Contacts");
        
        //Create an instance of the contact display helper
        ContactDisplayHelper contactDisplay = new ContactDisplayHelper(
                this.repository()
        );
        
        //dispay the contacts
        contactDisplay.print(scanner);
    }
}
