package eHealthCare;

public class LogInController {
    private Account account;

    public Account getAccount() {
        return account;
    }

    public LogInController() {
    }

    public void logIn(Account account) {
        this.account = account;
    }

    public void logOut() {
        account = null;
    }

}
