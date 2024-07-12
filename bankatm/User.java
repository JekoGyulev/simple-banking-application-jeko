package my_github_projects.bankatm;

public class User {
  private String firstName;
    private String lastName;

    //THe ID number of the user;
    private String uuid;
    //The MD5 hash of the user's pin number
    private byte[] pinHash;
    private List<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        //store the pin's MD5 hash, rather than the original value, for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());

        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new, unique universal ID for the user
        this.uuid = theBank.getNewUserUUID();

        //create empty list of accounts
        this.accounts = new ArrayList<>();

        //print log message
        System.out.printf("New user %s, %s with ID %s created.\n", lastName,
                firstName, this.uuid);


    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String pin) {
        try {
            MessageDigest md =  MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for(int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("  %d) %s\n", a + 1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAccountTransHistory(int accIndex) {
        this.accounts.get(accIndex).printTransHistory();
    }

    public double getAccBalance(int accIndex) {
        return this.accounts.get(accIndex).getBalance();
    }

    public void addAccTransaction(int accIndex, double amount, String memo) {
        this.accounts.get(accIndex).addTransaction(amount, memo);
    }

    public String getAccUUID(int accIndex) {
        return this.accounts.get(accIndex).getUUID();
    }
}
