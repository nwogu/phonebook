/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook.contracts;

import java.util.Scanner;
import phonebook.database.ContactRepository;

/**
 * This is the base abstract class for all contact handlers
 * @author gabriel nwogu
 */
public abstract class ContactHandler {
    
    private ContactRepository repository;
    
    /**
     *
     * @param repository
     */
    public ContactHandler(ContactRepository repository)
    {
        this.repository = repository;
    }
    
    /**
     * Handle the given input
     * @param scanner
     */
    public abstract void handle(Scanner scanner);
    
    /**
     * Get an instance of the contact repository
     * @return repository
     */
    public ContactRepository repository()
    {
        return this.repository;
    }
    
}
