package eHealthCare;

import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class LogInUI {
    private static Scanner scanner = new Scanner(System.in);

    private LogInController loginController;
    private Actions command;

    public LogInUI(LogInController loginController) {
        this.loginController = loginController;
        this.command = null;
    }

    public LogInController getLoginController() {
        return loginController;
    }

    public String handleCommands(String rep) {
        String cmd = rep.toUpperCase();
        this.command = Actions.valueOf(cmd);
        if (this.command.equals(Actions.LO)) {
            return "Logging out ...";
        } else if (this.command.equals(Actions.LI)) {
            return "Enter a username, a password";
        } else {
            return "Unknown command.";
        }
    }

    public void handleInputs() {
        if (this.command.equals(Actions.LI)) {
            Account account = new Account();
            System.out.println("*To return to Main Menu, type 'Back'");
            account = logInPrompt();
            if (account == null) {
                return;
            }
            loginController.logIn(account);
        } else if (this.command.equals(Actions.LO)) {
            logOut();
            loginController.logOut();
        }
    }

    private void logOut() {
        System.out.println("You have logged out successfully!");
    }

    private Account logInPrompt() {
        Account account = new Account();
        do {
            System.out.println("Enter Username:");
            String username = scanner.nextLine();
            if (username.equalsIgnoreCase("back")) {
                return null;
            }
            JsonArray tempMemory = account.getAccounts().getAll();
            try {
                JsonObject tempObject = tempMemory.get(account.getAccounts().search("username", username))
                        .getAsJsonObject();
                System.out.println("Password:");
                String password = scanner.nextLine();
                if (tempObject.get("password").getAsString().equals(password)) {
                    account = new Account(username, password,
                            tempObject.get("name").getAsString(),
                            tempObject.get("accessLevel").getAsInt());
                    return account;
                } else
                    System.out.println("Password invalid. Please try again!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Username invalid. Please try again!");
            }
        } while (true);
    }

}
