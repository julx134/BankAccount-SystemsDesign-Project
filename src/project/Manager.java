/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Jules
 */
public class Manager extends Users{
    private static Manager instance;

    
    public Manager(String username, String password, String role) {
        super(username, password, role);
    }
    
    //singleton pattern
    public static Manager getInstance() {
        if (instance == null)
            instance = new Manager("admin","admin","manager");
        return instance;
    }

    @Override
    public void handleAddCustomer(Client c, String username, String password) throws Exception {    
        if(!(username.equals("")) && !(password.equals(""))) {
            File newCustomerFile = new File("src\\project\\CustomerInformation\\"+username+".txt");  
            
            if (newCustomerFile.createNewFile()){
                FileWriter writeFile = new FileWriter(newCustomerFile);
                writeFile.write(username+"\n");
                writeFile.write(password+"\n");
                writeFile.write("customer");
                writeFile.close();
            }else {
                throw new Exception();
            }
            
            //creates a new SilverCustomer and adds it to array list of customers
            c.getCustomers().add(new SilverCustomer(username,password,"customer",new BankAccount(),newCustomerFile));
        }else {
            throw new Exception();
        }     
    }
    
    @Override
    public void handleDeleteCustomer(Client c,int i) {
        c.getCustomers().get(i).getCredentials().delete();
        c.getCustomers().remove(i);    
    }
 
    
    @Override
    public void handleLogin(Client c, String username, String password) throws Exception{
         
        if(username.equals("admin") && password.equals("admin"))
            c.setUser(getInstance());
        else
            throw new Exception();
    }
    
    @Override
    public void handleLogout(Client c) {
        c.setUser(null);
    }    
}
