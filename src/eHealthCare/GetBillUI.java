package eHealthCare;

import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetBillUI {
    GetBillController getBillController;
    Actions command;

    Scanner scanner = new Scanner(System.in);

    public GetBillUI(GetBillController getBillController) {
        this.getBillController = getBillController;
        this.command = null;
    }

    public String handleCommands(String rep) {
        this.command = Actions.valueOf(rep.toUpperCase());
        if (Actions.GB.equals(this.command)) {
            return "Please enter your medical bill ID:";
        }
        return "Unknown command.";
    }

    public void handleInputs() {
        if (this.command.equals(Actions.GB)) {
            printBill();
        }
    }

    private void printBill() {
        int id = scanner.nextInt();
        MedicalBill medicalBill = new MedicalBill();
        int cardID = 0;
        String d;
        double mFees = 0;
        double sFees = 0;
        double discount = 0;
        try {
            JsonObject bill = medicalBill.getMedicalBill().getAll()
                    .get(medicalBill.getMedicalBill().search("id", String.valueOf(id))).getAsJsonObject();

            cardID = bill.get("card").getAsInt();
            System.out.println("Your insurance card ID: " + bill.get("card"));
            System.out.println("- Diagnosis: " + bill.get("diagnosis"));
            System.out.println("- Prescriptions:");
            for (int i = 0; i < bill.get("prescriptions").getAsJsonArray().size(); i++) {
                JsonObject tempObject = bill.get("prescriptions").getAsJsonArray().get(i).getAsJsonObject();
                System.out.printf("%s: %-20s %s: %s\n", "   + Medicine: ", tempObject.get("medicine"), "Unit price: ",
                        tempObject.get("unitPrice") + "VND");
                String str;
                if (tempObject.get("afterMeal").getAsBoolean())
                    str = "        Consume after: ";
                else
                    str = "        Consume before: ";
                System.out.printf("%-25s %-15s %-15s %-15s %-15s\n", str,
                        "Breakfast: " + tempObject.get("morningDosage"),
                        "Lunch: " + tempObject.get("noonDosage"),
                        "Afternoon meal: " + tempObject.get("afternoonDosage"),
                        "Dinner: " + tempObject.get("eveningDosage"));
                System.out.println("        Consume for: " + tempObject.get("days") + " days.");
                mFees += getBillController.getMedicineFees(tempObject);
            }
            sFees = getBillController.getSpecialtyFees(Specialties.valueOf(bill.get("specialty").getAsString()));
            InsuranceCard insuranceCard = new InsuranceCard();
            int cardType = insuranceCard.getInsuranceCard().getAll()
                    .get(insuranceCard.getInsuranceCard().search("id", String.valueOf(cardID))).getAsJsonObject()
                    .get("insuranceCardType").getAsInt();
            if (cardType == 2) {
                discount = (mFees + sFees) * 0.8;
                d = " (80% maximum of 1.000.000VND):";
                if (discount > 1000000)
                    discount = 1000000;
            } else {
                d = " (50% maximum of 500.000VND):";
                discount = (mFees + sFees) * 0.5;
                if (discount > 500000)
                    discount = 500000;
            }
            System.out.printf("%-40s %s\n", "Specialty fees:", sFees + "VND");
            System.out.printf("%-40s %s\n", "Medicine fees:", mFees + "VND");
            System.out.printf("%-40s %s\n", "Insurance discount" + d, discount + "VND");
            System.out.printf("%-40s %s\n", "Total fees:", (sFees + mFees - discount) + "VND");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid bill ID. Please try again later!");
        }
    }
}
