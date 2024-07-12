package my_github_projects.bankatm;

public class Account {
   private String name;
    private String uuid; //The account ID number.
    private User holder; //The object user that holds this account
    private List<Transaction> transactions;


    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;

        //get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        //init transactions;
        this.transactions = new ArrayList<>();

        // add to holder and bank lists

    }

    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {
        // get the account's balance
        double balance = this.getBalance();
        // format the summary line, depending on whether the balance is negative
        if (balance >= 0) {
            return String.format("%s : $%.2f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s: $(%.2f) : %s", this.uuid, balance, this.name);
        }
    }

    public double getBalance() {
        double balance = 0;
        for (Transaction transaction : this.transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    //print the transaction history of the account
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account: %s\n", this.uuid);
        for (int t = this.transactions.size() - 1; t >= 0 ; t--) {

            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo) {
        //create a new transaction object and add it to our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
