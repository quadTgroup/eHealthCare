package eHealthCare;

import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NewPrescriptionsUI {
    // [{"diagnosis":"","prescriptions1":{"medicines":"","morningDosage":0,"noonDosage":0,"afternoonDosage":0,"eveningDosage":0,"afterMeal":true}},{"diagnosis":"idk","prescriptions1":{"medicines":"idk
    // man","morningDosage":0,"noonDosage":0,"afternoonDosage":0,"eveningDosage":0,"afterMeal":true}}]
    private NewPrescriptionsController newPrescriptionsController;
    private Actions command;
    Scanner scanner = new Scanner(System.in);

    public NewPrescriptionsUI(NewPrescriptionsController newPrescriptionsController) {
        this.newPrescriptionsController = newPrescriptionsController;
    }

    public String handleCommands(String rep) {
        String cmd = rep.toUpperCase();
        this.command = Actions.valueOf(cmd);
        if (this.command.equals(Actions.WP)) {
            return "Fill in this form.";
        } else
            return "Unknown command.";
    }

    public void handleInputs() {
        if (this.command.equals(Actions.WP)) {
            MedicalBill medicalBill = prescriptionsForm();
            if (medicalBill != null) {
                newPrescriptionsController.savePrescriptions(medicalBill);
                outMedicalBillID(medicalBill);
            }
        }
    }

    private void outMedicalBillID(MedicalBill medicalBill) {
        JsonArray tempMemory = medicalBill.getMedicalBill().getAll();
        System.out.println(
                "Your prescription's ID: "
                        + tempMemory.get(tempMemory.size() - 1).getAsJsonObject().get("id").getAsInt());
    }

    private MedicalBill prescriptionsForm() {
        int card = 0;
        Prescriptions prescriptions[] = new Prescriptions[10];
        boolean more = true;
        Doctor doctor = new Doctor();
        JsonArray doctorMemory = doctor.getDoctor().getAll();
        Specialties specialty = null;
        String diagnosis = new String();
        try {
            JsonObject tempObject = doctorMemory
                    .get(doctor.getDoctor().search("name", newPrescriptionsController.account.getName()))
                    .getAsJsonObject();
            if (tempObject.get("patientsQueuing").getAsJsonArray().size() == 0) {
                System.out.println("There are no patients currently in your care!");
                return null;
            }
            try {
                specialty = Specialties
                        .valueOf(tempObject.get("specialty").getAsString());
                int index = tempObject.get("patientsQueuing").getAsJsonArray().get(0)
                        .getAsJsonObject().get("index").getAsInt();
                System.out.println("Your diagnosis for patient " + index + ": ");
                card = tempObject.get("patientsQueuing").getAsJsonArray().get(0)
                        .getAsJsonObject().get("card").getAsInt();
            } catch (NullPointerException e) {
                System.out.println("There are no patients currently in your care!");
                return null;
            }
            diagnosis = scanner.nextLine();
            int index = 0;
            do {
                if (index > 9)
                    break;
                System.out.println("Medicine:");
                String medicines = scanner.nextLine();
                System.out.println("Dosage in the morning:");
                int mD = scanner.nextInt();
                System.out.println("Dosage in the noon:");
                int nD = scanner.nextInt();
                System.out.println("Dosage in the afternoon:");
                int aD = scanner.nextInt();
                System.out.println("Dosage in the evening:");
                int eD = scanner.nextInt();
                scanner.nextLine();
                boolean afterMeal;
                while (true) {
                    System.out.println("This medicine is used:\n[A] After meal\n[B] Before meal");
                    String str = scanner.nextLine();
                    if (str.equalsIgnoreCase("A") || str.equalsIgnoreCase("After meal")
                            || str.equalsIgnoreCase("After")) {
                        afterMeal = true;
                        break;
                    } else if (str.equalsIgnoreCase("B") || str.equalsIgnoreCase("Before meal")
                            || str.equalsIgnoreCase("Before")) {
                        afterMeal = false;
                        break;
                    }
                }
                System.out.println("Price per pill:");
                double unitPrice = scanner.nextDouble();
                System.out.println("Amount of days consuming this medicine:");
                int days = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Do you want to add more medicines?\n[Y] Yes\n[N] No");
                String temp = scanner.nextLine();
                while (true) {
                    if (temp.equalsIgnoreCase("Y") || temp.equalsIgnoreCase("Yes")) {
                        more = true;
                        break;
                    } else if (temp.equalsIgnoreCase("N") || temp.equalsIgnoreCase("No")) {
                        more = false;
                        break;
                    } else {
                        System.out.println("An error has occurred!");
                        break;
                    }
                }
                prescriptions[index] = new Prescriptions(medicines, mD, nD, aD, eD, afterMeal, unitPrice, days);
                if (index == 20) {
                    System.out.println("There can only be 20 or less medicines in 1 medical bill.");
                    break;
                }
                index++;
            } while (more);
        } catch (IndexOutOfBoundsException e) {
        }
        return new MedicalBill(specialty, diagnosis, prescriptions, card);
    }
}
