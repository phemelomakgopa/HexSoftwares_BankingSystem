import java.io.*;
import java.util.HashMap;

/**
 * Core banking logic and data management
 * @author PS Makgopa
 * @version 20250412
 */

public class Bank
{
    private HashMap<String, BankAccount> accounts;
    private final String DATA_FILE = "accounts.dat";

    // Initializes bank with account data
    public Bank()
    {
        accounts = loadAccounts();  // Attempt to load existing data
        if (accounts == null)
        {
            accounts = new HashMap<>(); // Initialize empty if no data exists
        }
    }

    // Creates new customer account
    public boolean createAccount(String name, String id, String pin)
    {
        if (accounts.containsKey(id)) return false;
        Customer customer = new Customer(name, id, pin);
        accounts.put(id, new BankAccount(customer));
        saveAccounts();
        return true;
    }

    // Authenticates user
    public BankAccount login(String id, String pin)
    {
        if (accounts.containsKey(id))
        {
            BankAccount acc = accounts.get(id);
            if (acc.getCustomer().getPin().equals(pin)) return acc;
        }
        return null;
    }

    // Saves all accounts to persistent storage
    public void saveAccounts()
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE)))
        {
            out.writeObject(accounts); // Serialize account data
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Loads accounts
    @SuppressWarnings("unchecked")
    public HashMap<String, BankAccount> loadAccounts()
    {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE)))
        {
            return (HashMap<String, BankAccount>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            return null; // No existing data
        }
    }
}
