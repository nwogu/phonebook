/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook.handlers;

import phonebook.contracts.ContactHandler;
import java.util.Scanner;
import phonebook.database.ContactRepository;
import phonebook.helpers.ContactDisplayHelper;

/**
 * This class handles the searching of contacts in the repository
 * @author gabriel nwogu
 */
public class SearchContactHandler extends ContactHandler {
    
    public SearchContactHandler(ContactRepository repository)
    {
        //Call the parent constructor to set the repository
        super(repository);
    }
    
    @Override
    public void handle(Scanner scanner)
    {
        scanner.useDelimiter("\n");
        
        System.out.println("Enter search info. Eg: Last name, Phone number, City \n");
        
        //Get the search term
        String term = scanner.next();
        
        System.out.println("Searching for contact");
        
        //Search the repository by the search term
        ContactRepository repository = this.repository().search(term);
        
        //Display the total number of reults found
        System.out.println("Search completed...." + repository.count() + " contact(s) found");
        
        //Create new instance of contact display helper
        ContactDisplayHelper display = new ContactDisplayHelper(repository);
        
        //Display the search results
        display.print(scanner);
    }
    
}
