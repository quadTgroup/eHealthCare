package eHealthCare;

import java.util.Scanner;

import com.google.gson.JsonArray;

public class NewAccountUI {
    Scanner scanner = new Scanner(System.in);
    NewAccountController newAccountController;
    Actions command;

    public NewAccountUI(NewAccountController newAccountController) {
        this.newAccountController = newAccountController;
        command = null;
    }

    public String handleCommands(String rep) {
        String cmd = rep.toUpperCase();
        this.command = Actions.valueOf(cmd);
        if (this.command.equals(Actions.CA)) {
            return "Creating a new employee/employer account!";
        } else
            return "Unknown command.";
    }

    public void handleInputs() {
        if (this.command.equals(Actions.CA)) {
            Account account = createAccount();
            if (account != null) {
                if (account.getAccessLevel() == 1)
                    newDoctor(account);
                newAccountController.createAccount(account);
            }
        }
    }

    private Account createAccount() {
        System.out.println("Please re-enter your password for security reasons:");
        JsonArray tempMemory = newAccountController.getAccount().getAccounts().getAll();
        String p = scanner.nextLine();
        int tries = 0;
        while (!p.equals(newAccountController.getAccount().getPassword()) && tries < 6) {
            tries++;
            System.out.println("You have entered the wrong password. You have " + (6 - tries)
                    + " more tries until you are logged out!");
            System.out.println("Password:");
            p = scanner.nextLine();
        }
        if (tries >= 6)
            return null;
        else {
            System.out.println("Enter informations for your new account:");
            while (true) {
                System.out.println(" - Username:");
                String username = scanner.nextLine();
                try {
                    tempMemory.get(newAccountController.getAccount().getAccounts().search("username", username));
                    System.out.println("Username already exists. Please enter a different one!");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(" - Password:");
                    String password = scanner.nextLine();
                    System.out.println(" - Full name:");
                    String name = scanner.nextLine();
                    int accessLevel = getAccessLevel();
                    System.out.println("Account has been created successfully!");
                    return new Account(username, password, name, accessLevel);
                }
            }
        }
    }

    private int getAccessLevel() {
        int accessLevel = 0;
        do {
            System.out.println(
                    " - New account's authority:\n\t[NS] Nurse or Staff\n\t[DP] Doctor or Physician\n\t[DM] Director or Manager");
            String authority = scanner.nextLine();
            if (authority.equalsIgnoreCase("NS") || authority.equalsIgnoreCase("Nurse")
                    || authority.equalsIgnoreCase("Staff")) {
                accessLevel = 0;
                break;
            } else if (authority.equalsIgnoreCase("DP") || authority.equalsIgnoreCase("Doctor")
                    || authority.equalsIgnoreCase("Physician")) {
                accessLevel = 1;
                break;
            } else if (authority.equalsIgnoreCase("DM") || authority.equalsIgnoreCase("Director")
                    || authority.equalsIgnoreCase("Manager")) {
                accessLevel = 3;
                break;
            }
        } while (true);
        return accessLevel;
    }

    private void newDoctor(Account account) {
        Doctor doctor = new Doctor();
        NewDoctorController newDoctorController = new NewDoctorController(doctor);
        NewDoctorUI newDoctorUI = new NewDoctorUI(newDoctorController);
        String resCMD = newDoctorUI.handleCommands("ND");
        System.out.println(resCMD);
        if (resCMD != null && !resCMD.equals("An error has occurred while creating a new doctor profile."))
            newDoctorUI.handleInputs(account.getName());
    }

}
