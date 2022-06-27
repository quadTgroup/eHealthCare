package eHealthCare;

public class NewAccountController {
    private Account account;

    public NewAccountController(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void createAccount(Account account) {
        account.getAccounts().update(account.getUsername(), account.getPassword(), account.getName(),
                account.getAccessLevel());
        account.getAccounts().write();
    }

}
