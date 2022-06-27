package eHealthCare;

import java.util.Scanner;

public class BootStrap {

    public static String commandInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(" > ");
        String str = scanner.nextLine();
        if (str.equalsIgnoreCase("exit")) {
            System.out.println("Thank you for using EHealthCare! Come again soon!");
            System.exit(0);
        }
        return str;
    }

    public static void main(String[] args) throws InterruptedException {
        InsuranceCard insuranceCard = new InsuranceCard();
        NewInsuranceCardController newInsuranceCardController = new NewInsuranceCardController(insuranceCard);
        NewInsuranceCardUI newInsuranceCardUI = new NewInsuranceCardUI(newInsuranceCardController);
        RegisterCheckupsController registerCheckupsController = new RegisterCheckupsController();
        RegisterCheckupsUI registerCheckupsUI = new RegisterCheckupsUI(registerCheckupsController);
        LogInController loginController = new LogInController();
        LogInUI logInUI = new LogInUI(loginController);
        GetBillController getBillController = new GetBillController();
        GetBillUI getBillUI = new GetBillUI(getBillController);
        Account account = new Account();
        System.out.println("-------------------------------------------------------------");
        System.out.println("Welcome to the eHealthCare System!\n (To exit type 'exit')\n");

        while (true) {
            Thread.sleep(1500);
            if (loginController.getAccount() == null || loginController.getAccount() == new Account()) {
                displayOptions(newInsuranceCardController);
                String rep = commandInput();
                String resCMD;
                if (rep.toUpperCase().equals(Actions.RC.toString())) {
                    resCMD = registerCheckupsUI.handleCommands(rep);
                    System.out.println(resCMD);
                    if (resCMD != null && !resCMD.equals("Unknown command.")) {
                        registerCheckupsUI.handleInputs();
                    }
                } else if (rep.toUpperCase().equals(Actions.LI.toString())) {
                    resCMD = logInUI.handleCommands(rep);
                    System.out.println(resCMD);
                    if (resCMD != null && !resCMD.equals("Unknown command.")) {
                        logInUI.handleInputs();
                        loginController = logInUI.getLoginController();
                        account = loginController.getAccount();
                    }
                } else if (rep.toUpperCase().equals(Actions.GB.toString())) {
                    resCMD = getBillUI.handleCommands(rep);
                    System.out.println(resCMD);
                    if (resCMD != null && !resCMD.equals("Unknown command.")) {
                        getBillUI.handleInputs();
                    }
                }
            } else {
                String prompt = getPrompt(loginController);
                System.out.print(prompt);
                String rep = commandInput();
                String resCMD;
                if (rep.toUpperCase().equals(Actions.RI.toString())) {
                    resCMD = newInsuranceCardUI.handleCommands(rep);
                    System.out.println(resCMD);
                    if (resCMD != null && !resCMD.equals("Unknown command.")) {
                        newInsuranceCardUI.handleInputs();
                    }
                }
                if (rep.toUpperCase().equals(Actions.WP.toString())) {
                    NewPrescriptionsController newPrescriptionsController = new NewPrescriptionsController(account);
                    NewPrescriptionsUI newPrescriptionsUI = new NewPrescriptionsUI(newPrescriptionsController);
                    resCMD = newPrescriptionsUI.handleCommands(rep);
                    System.out.println(resCMD);
                    if (resCMD != null && !resCMD.equals("Unknown command.")) {
                        newPrescriptionsUI.handleInputs();
                    }
                } else if (rep.toUpperCase().equals(Actions.LO.toString())) {
                    resCMD = logInUI.handleCommands(rep);
                    System.out.println(resCMD);
                    if (resCMD != null && !resCMD.equals("Unknown command.")) {
                        logInUI.handleInputs();
                        account = null;
                    }
                } else if (rep.toUpperCase().equals(Actions.CA.toString())) {
                    NewAccountController newAccountController = new NewAccountController(account);
                    NewAccountUI newAccountUI = new NewAccountUI(newAccountController);
                    resCMD = newAccountUI.handleCommands(rep);
                    System.out.println(resCMD);
                    if (resCMD != null && !resCMD.equals("Unknown command.")) {
                        newAccountUI.handleInputs();
                    }
                } else if (rep.toUpperCase().equals(Actions.NP.toString())) {
                    NextPatientController nextPatientController = new NextPatientController(account);
                    NextPatientUI nextPatientUI = new NextPatientUI(nextPatientController);
                    resCMD = nextPatientUI.handleCommands(rep);
                    System.out.println(resCMD);
                    if (resCMD != null && !resCMD.equals("Unknown command.")) {
                        nextPatientUI.handleInputs();
                    }
                }
            }
        }
    }

    private static void displayOptions(NewInsuranceCardController newInsuranceCardController) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~ MENU ~~~~~~~~~~~~~~~~~~~");
        // check
        String str = "";
        str = "Enter one of the commands in the brackets:\n" +
                "[RC] Register Check-ups\n" +
                "[LI] Log-In as an employee\n" + "[GB] Get Bill";
        System.out.println(str);
    }

    private static String getPrompt(LogInController logInController) {
        String str = "";
        String prompt = "Enter one of the commands in the brackets:\n";
        if (logInController.getAccount().getAccessLevel() == 1) {
            str = "Doctor ";
            prompt += "[WP] Writing Prescriptions\n" + "[NP] Next Patient\n";
        } else {
            prompt += "[RI] Register Insurance cards\n";
            if (logInController.getAccount().getAccessLevel() > 1) {
                str = "Director ";
                prompt += "[CA] Create new Accounts\n";
            }
        }
        if (logInController.getAccount().getAccessLevel() < 0 || logInController.getAccount().getAccessLevel() > 2) {
            return "An error has occurred while processing your account. Please try again later!";
        }
        prompt += "[LO] Log Out\n";
        System.out.println("-------------------------------------------------------------");
        System.out
                .println("Welcome " + str + logInController.getAccount().getName() + " to eHealthCare System!");
        return prompt;
    }
}
