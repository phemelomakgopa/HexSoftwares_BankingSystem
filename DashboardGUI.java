import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Provides main banking operations interface
 * @author PS Makgopa
 * @version 20250416
 */



public class DashboardGUI
{
     /** 
    * Declaring the main application window, 
    * reference to bank logic & 
    * for navigation
    */
    private JFrame frame;
    private Bank bank;
    private LoginGUI loginGUI;

    // Initializes dashboard components
    public DashboardGUI(Bank bank, LoginGUI loginGUI)
    {
        this.bank = bank;
        this.loginGUI = loginGUI;
        this.frame = new JFrame(); 

        frame.setSize(350, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    // Displays banking operations interface
    public void showDashboard(BankAccount account)
    {
        frame.getContentPane().removeAll();
        frame.setTitle(account.getCustomer().getName() + " - Dashboard");

        JPanel panel = new JPanel(new GridLayout(6, 1));

        // Operation buttons
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");
        JButton miniStmtBtn = new JButton("Mini Statement");
        JButton changePinBtn = new JButton("Change PIN");

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(Color.RED); 
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false); 


        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(balanceBtn);
        panel.add(miniStmtBtn);
        panel.add(changePinBtn);
        panel.add(logoutBtn);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        // Attach event handlers
        depositBtn.addActionListener(e ->
        {
            String amt = JOptionPane.showInputDialog("Enter amount to deposit:");
            try
            {
                account.deposit(Double.parseDouble(amt));
                bank.saveAccounts();
                JOptionPane.showMessageDialog(frame, "You have successfully deposited R" + amt);

            }
            catch (Exception ignored) {}
        });

        withdrawBtn.addActionListener(e ->
        {
            String amt = JOptionPane.showInputDialog("Enter amount to withdraw:");
            if (!account.withdraw(Double.parseDouble(amt)))
            {
                JOptionPane.showMessageDialog(frame, "Insufficient balance!");
            } 
            else
            {
                JOptionPane.showMessageDialog(frame, "Successfully withdrew R" + amt);
                bank.saveAccounts();
            }

        });

        balanceBtn.addActionListener(e ->
        {
            JOptionPane.showMessageDialog(frame, "Balance: R" + account.getBalance());
        });

        miniStmtBtn.addActionListener(e ->
        {
            ArrayList<String> statement = account.getMiniStatement();
            StringBuilder msg = new StringBuilder("Mini Statement:\n");
            for (String line : statement)
            {
                msg.append(line).append("\n");
            }
            JOptionPane.showMessageDialog(frame, msg.toString());
        });

        changePinBtn.addActionListener(e ->
        {
            String newPin = JOptionPane.showInputDialog("Enter new PIN:");
            account.changePin(newPin);
            bank.saveAccounts();
            JOptionPane.showMessageDialog(frame, "PIN changed successfully!");
        });

        logoutBtn.addActionListener(e ->
        {
            frame.setVisible(false);
            loginGUI.showFrame();
        });

        frame.setVisible(true);
    }
}