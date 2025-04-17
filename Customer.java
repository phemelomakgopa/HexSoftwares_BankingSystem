import java.io.Serializable;

/**
 * Represents a bank customer with personal identification
 * @author PS Makgopa
 * @version 20250416
 */

public class Customer implements Serializable
{
    // Customer details
    private String name;
    private String id;
    private String pin;

    // Creates a new customer profile
    public Customer(String name, String id, String pin)
    {
        this.name = name;
        this.id = id;
        this.pin = pin;
    }

    // Getters and setters
    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    public String getPin()
    {
        return pin;
    }

    // Updates customer's PIN
    public void setPin(String pin)
    {
        this.pin = pin;
    }
}