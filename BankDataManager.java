import java.io.*;
import java.util.*;

/**
 * Handles persistent data storage for bank accounts
 * Uses text file format for human-readable storage
 * @author PS Makgopa
 * @version 20250416
 */

public class BankDataManager
{
    // Data file location
    private static final String ACCOUNT_FILE = "accounts.txt";

    // Saves all accounts to persistent storage
    public static void saveAccounts(HashMap<String, BankAccount> accounts)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNT_FILE)))
        {
            for (BankAccount account : accounts.values())
            {
                Customer c = account.getCustomer();
                // Store account details
                writer.println(c.getId() + ";" + c.getName() + ";" + c.getPin() + ";" + account.getBalance());
                // Store transaction history
                for (String entry : account.getMiniStatement())
                {
                    writer.println("TRANSACTION:" + entry);
                }
                writer.println("END"); // Account delimiter
            }
        }
        catch (IOException e)
        {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Loads accounts from persistent storage
    public static HashMap<String, BankAccount> loadAccounts()
    {
        HashMap<String, BankAccount> accounts = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_FILE)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.contains(";")) continue;
                // Parse account details
                String[] parts = line.split(";");
                Customer c = new Customer(parts[1], parts[0], parts[2]);
                BankAccount acc = new BankAccount(c);

                // Temporary balance adjustment to initialize account
                acc.deposit(Double.parseDouble(parts[3])); 
                acc.withdraw(Double.parseDouble(parts[3])); 
                
                while (!(line = reader.readLine()).equals("END"))
                {
                    if (line.startsWith("TRANSACTION:"))
                    {
                        acc.getMiniStatement().add(line.substring("TRANSACTION:".length()));
                    }
                }
                accounts.put(c.getId(), acc);
            }
        }
        catch (IOException e)
        {
            System.out.println("No previous data found, starting fresh.");
        }
        return accounts;
    }
}
