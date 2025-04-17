import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Handles user authentication and account creation
 * @author PS Makgopa
 * @version 20250416
 */

public class LoginGUI
{
    /** 
    * Declaring the main application window, 
    * reference to bank logic & 
    * for navigation
    */
    private JFrame frame;
    private Bank bank;
    private DashboardGUI dashboardGUI;
    public String name;

    // Initializes login interface
    public LoginGUI(Bank bank)
    {
        this.bank = bank;
        initialize();
    }

    // getter
    public String getName()
    {
        return name;
    }

    // Building and configuring the login window
    private void initialize()
    {
        frame = new JFrame("South African Banking System - Login");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main panel using BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input fields panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel userLabel = new JLabel("Customer ID:");
        JTextField userText = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinText = new JPasswordField();

        inputPanel.add(userLabel);
        inputPanel.add(userText);
        inputPanel.add(pinLabel);
        inputPanel.add(pinText);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(new Color(0, 123, 255));  
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(0, 123, 255));  
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);  
        
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);  
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);
        buttonPanel.add(exitButton);

        // Add panels to main panel
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);

        // Event handlers
        loginButton.addActionListener(e ->
        {
            String id = userText.getText();
            String pin = new String(pinText.getPassword());
            BankAccount account = bank.login(id, pin);
            if (account != null)
            {
                frame.setVisible(false);
                if (dashboardGUI == null)
                {
                    dashboardGUI = new DashboardGUI(bank, this);
                }
                dashboardGUI.showDashboard(account);
            }
            else
            {
                JOptionPane.showMessageDialog(frame, "Invalid credentials");
            }
        });

        signUpButton.addActionListener(e -> showSignUp());

        exitButton.addActionListener(e -> System.exit(0));
    }

    private void showSignUp()
    {
        frame.getContentPane().removeAll();
    
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField nameText = new JTextField();
        JTextField idText = new JTextField();
        JPasswordField pinText = new JPasswordField();
    
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameText);
        inputPanel.add(new JLabel("Customer ID:"));
        inputPanel.add(idText);
        inputPanel.add(new JLabel("PIN:"));
        inputPanel.add(pinText);
    
        JButton createButton = new JButton("Create Account");
        createButton.setBackground(new Color(0, 123, 255)); 
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createButton);
    
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        frame.setContentPane(mainPanel);
        frame.revalidate();
        frame.repaint();
    
        createButton.addActionListener(e ->
        {
            name = nameText.getText();
            String id = idText.getText();
            String pin = new String(pinText.getPassword());
    
            if (bank.createAccount(name, id, pin)) {
                JOptionPane.showMessageDialog(frame, "Account created!");
                showLogin();
            } else {
                JOptionPane.showMessageDialog(frame, "Customer ID already exists!");
            }
        });
    }
    

    public void showLogin()
    {
        frame.getContentPane().removeAll();
        initialize();
        frame.setVisible(true);
    }

    public void showFrame()
    {
        frame.setVisible(true);
    }
}