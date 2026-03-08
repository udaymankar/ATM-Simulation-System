import java.util.Scanner;

public class ATMMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   Java ATM Simulation System 🏦           ║");
        System.out.println("║   Console-Based Banking Application       ║");
        System.out.println("╚══════════════════════════════════════════╝");

        boolean systemRunning = true;

        while (systemRunning) {
            System.out.println("\n========================================");
            System.out.println("  🏧 Insert Card to Begin");
            System.out.println("  Type 'exit' to shutdown ATM");
            System.out.println("========================================");
            System.out.print("👉 Press ENTER to insert card (or type 'exit'): ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("\n🔌 ATM Shutting down... Goodbye!");
                bank.saveData();
                systemRunning = false;
                break;
            }

            ATMSession session = new ATMSession(bank, scanner);

            try {
                if (session.startSession()) {
                    session.showMainMenu();
                } else {
                    System.out.println("⚠️  Session could not be started. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
                bank.saveData();
            }
        }

        scanner.close();
        System.out.println("✅ System shutdown complete.");
    }
}