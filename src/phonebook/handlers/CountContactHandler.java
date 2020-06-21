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
 * This class handles the counting of all contacts in the repository
 * @author gabriel nwogu
 */
public class CountContactHandler extends ContactHandler{
    
    public CountContactHandler(ContactRepository repository)
    {
        //Call the parent constructor to set the repository
        super(repository);
    }
    
    @Override
    public void handle(Scanner scanner)
    {
        //Get the total count
        int total = this.repository().count();
        
        System.out.println("You have " + total + " total contact(s)");
        
        //Return user to the main menu
        ContactDisplayHelper.mainMenu(scanner);
    }
}
