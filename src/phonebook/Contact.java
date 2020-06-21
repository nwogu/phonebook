/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook;

/**
 * This class holds the properties of the contact
 * @author gabriel nwogu
 */
public class Contact {
    
   public String firstName;
    
    public String lastName;
    
    public String address;
    
    public String city;
    
    public String phoneNumber;
    
    
    public Contact(
            String firstName, 
            String lastName, 
            String address, 
            String city,
            String phoneNumber
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * This method returns all the properties of the contact class
     *
     * @return fields
     */
    public static String[] fields()
    {
        String[] fields = {
            "firstName", 
            "lastName", 
            "address", 
            "city", 
            "phoneNumber"
        };
        
        return fields;
    }
    
    /**
     * This methods return all the fields/properties that can be searchable
     *
     * @return fields
     */
    public static String[] searchableFields()
    {
        String[] fields = {
            "lastName", 
            "city", 
            "phoneNumber"
        };
        
        return fields;
    }
    
    /**
     * Return the string representation of the contact object
     * @return 
     */
    @Override
    public String toString() 
    {
      return "First Name: " + this.firstName + 
              "\n Last Name: " + this.lastName + 
              "\n Address: " + this.address + 
              "\n City: " + this.city +
              "\n Phone Number: " + this.phoneNumber;
    }
    
}
