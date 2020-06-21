/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook;

import java.util.Scanner;

/**
 *
 * @author gabriel nwogu
 */
public class PhoneBook {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Get instance of phone book menu
        Menu menu = Menu.getInstance();
        
        menu.displayMenu();
        
        Scanner scanner = new Scanner(System.in);
        
        String input = scanner.next();
        
        //handle selected menu item from the user input
        menu.getHandler(input).handle(scanner);
    }
    
}
