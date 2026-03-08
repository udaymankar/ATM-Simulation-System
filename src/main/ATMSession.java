import java.util.List;
import java.util.Scanner;

public class ATMSession {

    private Bank bank;
    private Scanner scanner;
    private BankAccount currentAccount;

    public ATMSession(Bank bank, Scanner scanner) {
        this.bank = bank;
        this.scanner = scanner;
        this.currentAccount = null;
    }

    public boolean startSession() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        Welcome to JavaATM 💳          ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("🔢 Enter your Card Number: ");
        String cardNumber = scanner.nextLine().trim();

        if (!bank.cardExists(cardNumber)) {
            System.out.println("❌ Card not recognized. Please try again.");
            return false;
        }

        BankAccount account = bank.getAccount(cardNumber);

        if (account.isLocked()) {
            System.out.println("🔒 This account is LOCKED. Please contact your bank.");
            return false;
        }

        System.out.print("🔑 Enter your PIN: ");
        String pin = scanner.nextLine().trim();

        if (!account.verifyPin(pin)) {
            bank.saveData();
            return false;
        }

        this.currentAccount = account;
        System.out.println("\n✅ Login successful! Welcome, " + account.getAccountHolderName() + "!");
        return true;
    }

    public void showMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║              ATM MENU                ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. 💰 Check Balance                 ║");
            System.out.println("║  2. 📥 Deposit Money                 ║");
            System.out.println("║  3. 📤 Withdraw Money                ║");
            System.out.println("║  4. 🔄 Transfer Money                ║");
            System.out.println("║  5. 📜 Transaction History           ║");
            System.out.println("║  6. 🚪 Logout                        ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("👉 Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": checkBalance(); break;
                case "2": depositMoney(); break;
                case "3": withdrawMoney(); break;
                case "4": transferMoney(); break;
                case "5": showTransactionHistory(); break;
                case "6":
                    running = false;
                    endSession();
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please enter 1-6.");
            }
        }
    }

    private void checkBalance() {
        System.out.println("\n💰 ──────────────────────────────────");
        System.out.println("   Account Holder : " + currentAccount.getAccountHolderName());
        System.out.println("   Card Number    : " + maskCard(currentAccount.getCardNumber()));
        System.out.printf ("   Available Balance: $%.2f%n", currentAccount.getBalance());
        System.out.println("────────────────────────────────────");
    }

    private void depositMoney() {
        System.out.print("\n📥 Enter amount to deposit: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (currentAccount.deposit(amount)) {
                System.out.printf("✅ Successfully deposited $%.2f%n", amount);
                System.out.printf("   New Balance: $%.2f%n", currentAccount.getBalance());
                bank.saveData();
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount entered.");
        }
    }

    private void withdrawMoney() {
        System.out.print("\n📤 Enter amount to withdraw: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (currentAccount.withdraw(amount)) {
                System.out.printf("✅ Successfully withdrew $%.2f%n", amount);
                System.out.printf("   Remaining Balance: $%.2f%n", currentAccount.getBalance());
                bank.saveData();
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount entered.");
        }
    }

    private void transferMoney() {
        System.out.print("\n🔄 Enter recipient's Card Number: ");
        String recipientCard = scanner.nextLine().trim();

        if (recipientCard.equals(currentAccount.getCardNumber())) {
            System.out.println("❌ You cannot transfer to your own account.");
            return;
        }

        if (!bank.cardExists(recipientCard)) {
            System.out.println("❌ Recipient card not found.");
            return;
        }

        BankAccount recipient = bank.getAccount(recipientCard);
        System.out.println("   Recipient: " + recipient.getAccountHolderName());
        System.out.print("💵 Enter amount to transfer: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (currentAccount.transfer(recipient, amount)) {
                System.out.printf("✅ Successfully transferred $%.2f to %s%n", amount, recipient.getAccountHolderName());
                System.out.printf("   Your Remaining Balance: $%.2f%n", currentAccount.getBalance());
                bank.saveData();
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount entered.");
        }
    }

    private void showTransactionHistory() {
        List<String> history = currentAccount.getTransactionHistory();
        System.out.println("\n📜 ──── Transaction History ────");
        if (history.isEmpty()) {
            System.out.println("   No transactions found.");
        } else {
            int start = Math.max(0, history.size() - 10);
            for (int i = start; i < history.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + history.get(i));
            }
        }
        System.out.println("────────────────────────────────");
    }

    private void endSession() {
        System.out.println("\n👋 Thank you for using JavaATM, " + currentAccount.getAccountHolderName() + "!");
        System.out.println("   Please take your card. Have a great day! 💳");
        currentAccount = null;
    }

    private String maskCard(String cardNumber) {
        if (cardNumber.length() < 4) return cardNumber;
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}