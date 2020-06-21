/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import phonebook.Contact;
import phonebook.helpers.MessageHelper;

/**
 * This class acts as the repository of the contacts class
 * It handles file operations of the contacts file database
 * 
 * The contacts are stored as a @json file
 * @author gabriel nwogu
 */
public class ContactRepository {
    
    /**
     * Path to the database of contacts
     */
    private static final String DATABASE_NAME = "src/phonebook/database/contacts.json";
    
    /**
     * Path to the text file of contact information
     * available after every execution
     */
    private static final String FILE_NAME = "src/phonebook/database/contacts";
    
    /**
     * Holds the absolute path to the database file
     */
    private final String dbPath;
    
    /**
     * Array of contacts
     */
    private final ArrayList<Contact> contacts;
    
    /**
     * This constructor takes no arguments. It loads
     * the contacts from the database
     */
    public ContactRepository()
    {
        this.dbPath = this.getDbPath();
        this.contacts = this.loadContacts();
    }
    
    /**
     * This constructor takes an array of contacts as an argument
     * It is used when contacts are loaded from memory
     * 
     * @param contacts
     */
    public ContactRepository(ArrayList<Contact> contacts)
    {
        this.dbPath = this.getDbPath();
        this.contacts = contacts;
    }
    
    /**
     * This method returns the absolute path to the database
     *
     * @return absolutePath
     */
    private String getDbPath()
    {
        Path currentRelativePath = Paths.get(ContactRepository.DATABASE_NAME);
        
        return currentRelativePath.toAbsolutePath().toString();
    }
    
    /**
     * Returns a unique file name to save the contacts
     * to a text file
     * 
     * @return contactsFilePath
     */
    private String getContactsFilePath()
    {
        Date date = new Date();
        long time = date.getTime();
        
        //To make the file name unique, We append the contact name
        //with the current time in milliseconds
        String fileName = ContactRepository.FILE_NAME + "-" + time + ".txt";
        
        Path currentRelativePath = Paths.get(fileName);
        
        return currentRelativePath.toAbsolutePath().toString();
    }
    
    /**
     * Returns all the contacts in the database
     * 
     * @return contacts
     */
    public ArrayList<Contact> all()
    {
        return this.contacts;
    }
    
    /**
     * This method checks and returns the contacts where a given attribute
     * strictly matches the given value
     *
     * @param field
     * @param value
     * 
     * @return ContactRepository
     */
    public ContactRepository where(String field, String value)
    {
        return this.conditon(field, "=", value);
    }
    
    /**
     * This method checks and returns the contacts based on a given condition
     * 
     * @param field
     * @param operator
     * @param value
     * 
     * @return ContactRepository
     */
    public ContactRepository conditon(String field, String operator, String value)
    {
        //Hold the results in an empty array
        ArrayList<Contact> result = new ArrayList<>();
        
        for (Contact contact: this.all()) {
            
            try {
                //Use reflection to get the property from the contact
                Field contactProperty = contact.getClass().getField(field);
                //Get the value of the contact from thr contact field
                String property = (String)contactProperty.get(contact);
                
                switch (operator) {
                    
                    /**
                     * If the operator is like, we want to check
                     * that the given value is contained in the contact.
                     * If the contact is found, we add to the result
                     */
                    case "like":
                        if (property.contains(value)) {
                            result.add(contact);
                        }
                        break;
                        
                    /**
                     * By default we want to check if the given contact
                     * matches strictly with the given value
                     */
                    default:
                        if (property.equals(value)) {
                            result.add(contact);
                        }
                        break;
                }
                
            } catch (Exception ex) {
                MessageHelper.printError("Field:" + field + "not found for contact");
            }
        }
        
        //We dont want to mutate this repository,
        //so we return a new instance with our results in memory.
        
        return new ContactRepository(result);
    }
    
    /**
     * This method checks that the given field loosely matches
     * the given value
     * 
     * @param field
     * @param value
     * 
     * @return ContactRepository
     */
    public ContactRepository whereLike(String field, String value)
    {
        return this.conditon(field, "like", value);
    }
    
    /**
     * This method searches the database based on the search term
     * 
     * @param term
     * @return ContactRepository
     */
    public ContactRepository search(String term)
    {
        ArrayList<Contact> result = new ArrayList<>();
        
        //We only want to search through the searchable fields
        for (String searchable: Contact.searchableFields()) {
            result.addAll(this.whereLike(searchable, term).all());
        }
        
        //In the event that we have duplicate values in our search result
        //We create a set to filter out the duplicates
        Set<Contact> uniqueContacts = new HashSet<>(result);
        ArrayList<Contact> uniqueResult = new ArrayList<>(uniqueContacts);
        
        //We dont want to mutate this repository,
        //so we return a new instance with our results in memory.
        return new ContactRepository(uniqueResult);
    }
    
    /**
     * This method returns the total contacts available
     * @return count
     */
    public int count()
    {
        return this.all().size();
    }
    
    /**
     * This method returns the first contact in the repository
     * @return contact
     */
    public Contact first()
    {
        return this.all().get(0);
    }
    
    /**
     * This method checks if the repository data is empty
     * 
     * @return boolean
     */
    public boolean exists()
    {
        return !this.all().isEmpty();
    }
    
    /**
     * This method gets the first contact that matches a given condition
     * 
     * @param field
     * @param value
     * 
     * @return contact
     */
    public Contact firstWhere(String field, String value)
    {
        return this.where(field, value).first();
    }
    
    /**
     * Add a new contact to the database
     *
     * @param contact
     * @return ContactRepository
     */
    public ContactRepository add(Contact contact)
    {
        this.contacts.add(contact);
        
        return this;
    }
    
    /**
     * Update the repository with contact information
     * 
     * @param old
     * @param recent
     * @return Contact
     */
    public ContactRepository update(Contact old, Contact recent)
    {
        return this.delete(old).add(recent);
    }
    
    /**
     * Remove a contact from the database
     * 
     * @param contact
     * @return ContactRepository
     */
    public ContactRepository delete(Contact contact)
    {
        //Get the index of the contact from the database
        int contactIndex = this.contacts.indexOf(contact);
        
        //Remove the contact by the index
        this.contacts.remove(contactIndex);
        
        return this;
    }
    
    /**
     * Load the database into memory
     * 
     * @return contacts
     */
    private ArrayList<Contact> loadContacts()
    {
        //Hold the array of contacts
        ArrayList<Contact> data = new ArrayList<>();
        
        try {
            //Create a new file instance with the database path
            File contactFile = new File(this.dbPath);
            
            //Read the entire database to a string
            String jsonContacts = new Scanner(contactFile).useDelimiter("\\Z").next();
            
            //Create a new instance of Gson (A helper library to handle json files)
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Contact>>(){}.getType();
            
            //Parse the given json into an array of contacts
            data =  gson.fromJson(jsonContacts, type);
            
        } catch (Exception exception) {
            MessageHelper.printError("Couldn't access contacts file");
        }
        
        return data;
    }
    
    /**
     * Write the entire repository back to database
     */
    public void offloadContacts()
    {
        //Create a file writer variable
        FileWriter writer = null;
        
        try {
            
            //Create new instance of Gson
            Gson gson = new Gson();
            
            //Read the entire repository to a json string
            String jsonContacts = gson.toJson(this.all());
            
            //Create a file writer instance
            writer = new FileWriter(this.dbPath);
            
            //Write to databse file
            writer.write(jsonContacts);
            
            //close the writer
            writer.close();
        } catch (IOException ex) {
            
            MessageHelper.printError("could not save contact information");
        } finally {
            
            try {
                //Attempt to close the writer
                writer.close();
                
            } catch (Exception ex) {
                MessageHelper.printError("could not save contact information");
            }
        }
    }
    
    /**
     *
     * @return filePath
     */
    public String saveContactsFile()
    {
        //Get a unique file name
        String contactsFilePath = this.getContactsFilePath();
        
        try {
            //Create a file instance
            File file = new File(contactsFilePath);
            
            //If file doesnt exist, create the file
            if (! file.exists()) file.createNewFile();

            //Create a file writer instance with append option set to true
            try (FileWriter writer = new FileWriter(file, true)) {
                
                //Loop through all the contacts in the repo and add to file
                for (Contact contact : this.all()) {
                    writer.write(contact.toString() + "\n\n");
                }
            }
            
        } catch (IOException ignored) {
            MessageHelper.printError(
                    "could not save contact information to text file"
            );
        }
        
        //Return the file path to the contacts.txt file
        return contactsFilePath;
    }
    
}
