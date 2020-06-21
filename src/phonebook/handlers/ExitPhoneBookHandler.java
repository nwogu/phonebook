/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook.handlers;

import phonebook.contracts.ContactHandler;
import java.util.Scanner;
import phonebook.database.ContactRepository;
import phonebook.helpers.MessageHelper;

/**
 * This class handles the exiting of the application
 * 
 * @author gabriel nwogu
 */
public class ExitPhoneBookHandler extends ContactHandler{
    
    
    public ExitPhoneBookHandler(ContactRepository repository)
    {
        //Call the parent constructor to set the repository
        super(repository);
    }
    
    @Override
    public void handle(Scanner scanner)
    {
        //Save the contacts to a text file
        String contactsPath = this.repository().saveContactsFile();
        
        System.out.println("Saved contacts to " + contactsPath);
        
        MessageHelper.printSuccess("Exiting PhoneBook....... Good Bye!");
    }
    
}
