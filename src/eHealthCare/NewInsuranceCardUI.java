package eHealthCare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class NewInsuranceCardUI {
    private static Scanner scanner = new Scanner(System.in);
    private Actions command;
    private NewInsuranceCardController newInsuranceCardController;

    public NewInsuranceCardUI(NewInsuranceCardController newInsuranceCardController) {
        this.command = null;
        this.newInsuranceCardController = newInsuranceCardController;
    }

    public String handleCommands(String rep) {
        String cmd = rep.toUpperCase();
        this.command = Actions.valueOf(cmd);
        if (this.command.equals(Actions.RI)) {
            return "Enter information";
        } else {
            return "Unknown command.";
        }
    }

    public void handleInputs() {
        if (this.command.equals(Actions.RI)) {
            InsuranceCard insuranceCard = infoInputs();
            newInsuranceCardController.registerInsuranceCard(insuranceCard.getid(),
                    insuranceCard.getInsuranceCardType(),
                    insuranceCard.getName(), insuranceCard.getDateOfBirth(), insuranceCard.getAddress(),
                    insuranceCard.getPhoneNumber(), insuranceCard.getInfocustomer());
        }
    }

    private static InsuranceCard infoInputs() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Full name: ");
        String name = scanner.nextLine();
        Date dateOfBirth;
        do {
            try {
                System.out.println("Date of birth (dd/MM/yyyy): ");
                dateOfBirth = formatter.parse(scanner.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println("Invalid inputs. Please try again!");
            }
        } while (true);
        System.out.println("Address: ");
        String address = scanner.nextLine();
        System.out.println("Phone number: ");
        String phoneNumber = scanner.nextLine();
        int insuranceCardType;
        while (true) {
            System.out.println("Insurance type:");
            System.out.println("[1] Monthy fees: 100.000VND - Insurance discount: 50% (Maximum 500.000VND)");
            System.out.println("[2] Monthy fees: 300.000VND - Insurance discount: 80% (Maximum 1.000.000VND)");
            insuranceCardType = scanner.nextInt();
            if (insuranceCardType < 3 && insuranceCardType > 0)
                break;
        }
        scanner.nextLine();
        System.out.println("Gender: \n[M] Male\n[F] Female");
        boolean gender = true;
        do {
            String g = scanner.nextLine();
            if (g.equalsIgnoreCase("m") || g.equalsIgnoreCase("male")) {
                gender = false;
                break;
            } else if (g.equalsIgnoreCase("f") || g.equalsIgnoreCase("female")) {
                gender = true;
                break;
            } else
                System.out.println("Invalid inputs. Please try again!");
        } while (true);
        System.out.println("ABO blood group (A, B or O):\n");
        String bloodGroup = null;
        do {
            BloodGroup bGroup[] = BloodGroup.values();
            boolean bValid = false;
            bloodGroup = scanner.nextLine();
            for (BloodGroup b : bGroup) {
                if (b.toString().equalsIgnoreCase(bloodGroup)) {
                    bValid = true;
                    break;
                }
            }
            if (bValid)
                break;
        } while (true);
        System.out.println("Rhesus blood group:\n[-] Negative\n[+] Positive");
        boolean rhesus;
        do {
            String str = scanner.nextLine();
            if (str.equalsIgnoreCase("+") || str.equalsIgnoreCase("positive")) {
                rhesus = true;
                break;
            } else if (str.equalsIgnoreCase("-") || str.equalsIgnoreCase("negative")) {
                rhesus = false;
                break;
            }
        } while (true);
        System.out.println("Weight: ");
        Double weight = scanner.nextDouble();
        System.out.println("Height: ");
        Double height = scanner.nextDouble();
        HealthInfo infocustomer = new HealthInfo(gender, bloodGroup, rhesus, weight, height);
        InsuranceCard insuranceCard = new InsuranceCard(insuranceCardType, name, dateOfBirth, address, phoneNumber,
                infocustomer);
        System.out.println("info InsurancceCard" + insuranceCard.toString());
        return insuranceCard;
    }
}
