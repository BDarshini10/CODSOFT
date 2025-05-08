import java.util.Scanner;

// Represnt a class
class BankAccount {
    private double balance;

    // Constructor to set up the account 
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Method to add money 
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Rs. " + amount + " deposited successfully!");
        } else {
            System.out.println("Please enter a valid amount to deposit.");
        }
    }

    // Method to take out money 
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Rs. " + amount + " withdrawn successfully.");
        } else {
            System.out.println("Cannot withdraw. Either the amount is invalid or there's not enough balance.");
        }
    }

    // Method to check balance
    public double checkBalance() {
        return balance;
    }
}

// Class that represents the ATM machine
class ATM {
    private BankAccount account;

    // Constructor to link the ATM with a specific bank account
    public ATM(BankAccount account) {
        this.account = account;
    }

    // Method to show the ATM menu and choices
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the ATM!");

        do {
            System.out.println("\n------------------------------");
            System.out.println("          ATM MENU");
            System.out.println("------------------------------");
            System.out.println("1) Check Balance");
            System.out.println("2) Deposit Money");
            System.out.println("3) Withdraw Money");
            System.out.println("4) Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Your current balance is: Rs. " + account.checkBalance());
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: Rs. ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: Rs. ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;

                case 4:
                    System.out.println("Thank you. Have a great day!");
                    break;

                default:
                    System.out.println(" Invalid option. Please choose between 1 to 4.");
            }

        } while (choice != 4);

        scanner.close();
    }
}

// Main class
public class ATMInterface {
    public static void main(String[] args) {
        // Creating a bank account for the user
        BankAccount userAccount = new BankAccount(10000);

        // Creating an ATM connected to the user's account
        ATM atm = new ATM(userAccount);

        // Launch the ATM menu 
        atm.showMenu();
    }
}