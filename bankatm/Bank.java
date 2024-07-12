package my_github_projects.bankatm;

public class Bank {
  private String name;
    private List<User> users;
    private List<Account> accounts;

    public Bank(String bankName){
        this.name = bankName;
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public String getNewUserUUID() {
        // inits
        String uuid;
        Random random = new Random();
        int length = 6;
        boolean nonUnique;

        do {
            //generate the number
            uuid = "";
            for (int c = 0; c < length; c++) {
                uuid += ((Integer)random.nextInt(10)).toString();
            }

            nonUnique = false;
            //check if it is unique
            for(User user: this.users) {
                if (uuid.compareTo(user.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }


        } while (nonUnique);


        return uuid;
    }
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getNewAccountUUID() {
        // inits
        String uuid;
        Random random = new Random();
        int length = 10;
        boolean nonUnique;

        do {
            //generate the number
            uuid = "";
            for (int c = 0; c < length; c++) {
                uuid += ((Integer)random.nextInt(10)).toString();
            }

            nonUnique = false;
            //check if it is unique
            for(Account account : this.accounts) {
                if (uuid.compareTo(account.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }


        } while (nonUnique);


        return uuid;
    }

    public User addUser(String firstName, String lastName, String pin) {
        //create a new User object and add it to the list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //create a savings account for the user
        Account newAccount = new Account("Savings",newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin) {
        for (User u: this.users) {
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        //if we haven't found the user or have an incorrect pin
        return null;
    }

    public String getName() {
        return this.name;
    }
}
