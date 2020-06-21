/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook.helpers;

/**
 * This helper class handles printing and exiting
 * the phone book with either a success or an error
 * message.
 *
 * @author gabriel nwogu
 */
public class MessageHelper {
    
    /**
     * Print an error message to the screen and exit
     * @param message
     */
    public static void printError(String message)
    {
        System.err.println(message);
        System.exit(1);
    }
    
    /**
     * Print a success message to the screen and exit
     * @param message
     */
    public static void printSuccess(String message)
    {
        System.out.println(message);
        System.exit(0);
    }
    
}
