package my_github_projects.bankatm;
import java.util.Date;

public class Transaction {
   private double amount;
    private Date timestamp;
    private String memo;

    //The account in which the transaction was made.
    private Account inAccount;

    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account account) {
        // call the two-arg constructor first
        this(amount, account);
        //set the memo
        this.memo = memo;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s : $%.2f : %s", this.timestamp.toString(),
                   this.amount, this.memo);
        } else {
            return String.format("%s : $(%.2f) : %s", this.timestamp.toString(),
                    -this.amount, this.memo);
        }
    }
}
