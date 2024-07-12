package my_github_projects.bankatm;
import java.util.Scanner;

public class ATM {
  public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Bank bank = new Bank("Bank of Sofia");

        User user = bank.addUser("Ivan", "Georgiev", "1234");

        Account newAccount = new Account("Checking", user, bank);
        user.addAccount(newAccount);
        bank.addAccount(newAccount);


        User curUser;
        while(true) {
            //stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(bank, scanner);

            //stay in main menu until user quits
            ATM.printUserMenu(curUser, scanner);
        }
    }

    private static void printUserMenu(User curUser, Scanner scanner) {
        //print a summary of the user's accounts
        curUser.printAccountsSummary();
        int choice;
        //user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", curUser.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while (choice < 1 || choice > 5);


        switch (choice) {
            case 1:
                ATM.showTransHistory(curUser, scanner);
                break;
            case 2:
                ATM.withdrawFunds(curUser, scanner);
                break;
            case 3:
                ATM.depositFunds(curUser, scanner);
                break;
            case 4:
                ATM.transferFunds(curUser, scanner);
                break;
            case 5:
                scanner.nextLine();
                break;
        }

        // redisplay this menu unless the user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(curUser, scanner);
        }

    }

    private static void depositFunds(User theUser, Scanner sc) {
        int toAcc;
        double amount;
        double accBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", theUser.numAccounts());
            toAcc = sc.nextInt() - 1;
            if (toAcc < 0 || toAcc >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcc < 0 || toAcc >= theUser.numAccounts());

        accBal = theUser.getAccBalance(toAcc);
        do {
            System.out.printf("Enter the amount to transfer (max: $%.2f): $",
                    accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }

        } while (amount < 0);

        sc.nextLine();
        System.out.print("Enter a memo:");
        memo = sc.nextLine();

        //do the withdraw
        theUser.addAccTransaction(toAcc,amount, memo);

    }

    private static void withdrawFunds(User theUser, Scanner sc) {
        int fromAcc;
        double amount;
        double accBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ", theUser.numAccounts());
            fromAcc = sc.nextInt() - 1;
            if (fromAcc < 0 || fromAcc >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBal = theUser.getAccBalance(fromAcc);
        do {
            System.out.printf("Enter the amount to withdraw (max: $%.2f): $",
                    accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > accBal) {
                System.out.printf("Amount must not be greater than\n"
                        + "balance of $%.2f.", accBal);
            }
        } while (amount < 0 || amount > accBal);

        sc.nextLine();
        System.out.print("Enter a memo:");
        memo = sc.nextLine();

        //do the withdraw
        theUser.addAccTransaction(fromAcc,-1 * amount, memo);
    }
    public static void transferFunds(User theUser, Scanner sc) {
        int fromAcc;
        int toAcc;
        double amount;
        double accBal;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transform from: ", theUser.numAccounts());
            fromAcc = sc.nextInt() - 1;
            if (fromAcc < 0 || fromAcc >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBal = theUser.getAccBalance(fromAcc);

        //get the acc to transfer to:
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transform to: ", theUser.numAccounts());
            toAcc = sc.nextInt() - 1;
            if (toAcc < 0 ||toAcc >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcc < 0 || toAcc >= theUser.numAccounts());

        //get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max: $%.2f): $",
                    accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > accBal) {
                System.out.printf("Amount must not be greater than"
                + " balance of $%.2f.\n", accBal);
            }
        } while (amount < 0 || amount > accBal);

        //finally do the transfer
        theUser.addAccTransaction(fromAcc, -1 * amount, String.format("Transfer to account %s", theUser.getAccUUID(toAcc)));
        theUser.addAccTransaction(toAcc, amount, String.format("Transfer to account %s", theUser.getAccUUID(fromAcc)));

    }
    private static void showTransHistory(User theUser, Scanner scanner) {
        int theAcc;
        //get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transactions you want to see: ", theUser.numAccounts());

            theAcc = scanner.nextInt() - 1;
            if (theAcc < 0 || theAcc >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }

        } while (theAcc < 0 || theAcc >= theUser.numAccounts());

        //print the transaction history
        theUser.printAccountTransHistory(theAcc);


    }

    private static User mainMenuPrompt(Bank bank, Scanner scanner) {
        String userID;
        String pin;
        User authUser;

        //prompt the user for user ID or PIN combo until a correct one is reached
        do {
            System.out.printf("\nWelcome to %s\n\n", bank.getName());
            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter pin: ");
            pin = scanner.nextLine();

            //try to get the user object corresponding to the ID and pin combo
            authUser = bank.userLogin(userID, pin);

            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination. " +
                        "Please try again.");
            }
        } while (authUser == null); //continue looping until successful login

        return authUser;
    }
}
