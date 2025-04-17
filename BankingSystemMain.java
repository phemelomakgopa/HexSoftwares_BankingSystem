/**
 * Entry point for the banking application
 * @author PS Makgopa
 * @version 20250416
 */

public class BankingSystemMain {
    public static void main(String[] args)
    {
        // Initialize bank with data persistence
        Bank bank = new Bank();

        // Launch login interface
        LoginGUI loginGUI = new LoginGUI(bank);
        loginGUI.showLogin();
    }
}