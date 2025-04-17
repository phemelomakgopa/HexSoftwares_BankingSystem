import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a bank account with transaction capabilities
 * @author PS Makgopa
 * @version 20250416
 */

public class BankAccount implements Serializable
{
    private Customer customer;
    private double balance;
    private ArrayList<String> miniStatement;

    /**
     * Constructor initializes a new bank account
     * parameter customer - The customer who owns this account
     */
    public BankAccount(Customer customer)
    {
        this.customer = customer;
        this.balance = 0;
        this.miniStatement = new ArrayList<>();
    }

    // Bank operations to the acoount and records the transaction
    public void deposit(double amount)
    {
        balance += amount;
        miniStatement.add("Deposited: R" + amount);
    }

    public boolean withdraw(double amount)
    {
        if (amount > balance) return false;
        balance -= amount;
        miniStatement.add("Withdrawn: R" + amount);
        return true;
    }

    // getters
    public double getBalance()
    {
        return balance;
    }

    public void changePin(String newPin)
    {
        customer.setPin(newPin);
    }

    public ArrayList<String> getMiniStatement()
    {
        return new ArrayList<>(miniStatement); 
    }

    public Customer getCustomer()
    {
        return customer;
    }
}