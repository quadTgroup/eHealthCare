package eHealthCare;

public class Account {
    private StoredFiles account = new StoredFiles("Accounts.json");
    private String username;
    private String password;
    private String name;
    private int accessLevel;

    public Account(String username, String password, String name, int accessLevel) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.accessLevel = accessLevel;
    }

    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public StoredFiles getAccounts() {
        return account;
    }

    @Override
    public String toString() {
        return "Account [accessLevel=" + accessLevel + ", account=" + account + ", password=" + password + ", username="
                + username + "]";
    }
}
