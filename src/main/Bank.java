import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DATA_FILE = "bank_data.ser";

    private Map<String, BankAccount> accounts;

    public Bank() {
        accounts = new HashMap<>();
        loadData();
        if (accounts.isEmpty()) {
            seedDefaultAccounts();
        }
    }

    // Seed some default test accounts
    private void seedDefaultAccounts() {
        accounts.put("1111222233334444", new BankAccount("1111222233334444", "1234", "Uday", 5000.00));
        accounts.put("5555666677778888", new BankAccount("5555666677778888", "5678", "Rahul", 3000.00));
        accounts.put("9999000011112222", new BankAccount("9999000011112222", "9999", "Atharv", 7500.00));
        saveData();
        System.out.println("✅ Default accounts loaded.");
    }

    // Get account by card number
    public BankAccount getAccount(String cardNumber) {
        return accounts.get(cardNumber);
    }

    // Check if card exists
    public boolean cardExists(String cardNumber) {
        return accounts.containsKey(cardNumber);
    }

    // Save data to file
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
            System.out.println("💾 Data saved successfully.");
        } catch (IOException e) {
            System.out.println("⚠️  Warning: Could not save data. " + e.getMessage());
        }
    }

    // Load data from file
    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            accounts = (Map<String, BankAccount>) ois.readObject();
            System.out.println("✅ Data loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️  Warning: Could not load data. Starting fresh. " + e.getMessage());
            accounts = new HashMap<>();
        }
    }
}
