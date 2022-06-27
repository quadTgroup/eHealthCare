package eHealthCare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RegisterCheckupsUI {
    private static Scanner scanner = new Scanner(System.in);
    private Actions command;
    private RegisterCheckupsController registerCheckupsController;

    public RegisterCheckupsUI(RegisterCheckupsController medicalCheckupController) {
        this.command = null;
        this.registerCheckupsController = medicalCheckupController;
    }

    public String handleCommands(String rep) {
        String cmd = rep.toUpperCase();
        this.command = Actions.valueOf(cmd);
        if (this.command.equals(Actions.RC)) {
            return "Insert customer's card";
        } else {
            return "Unknown command.";
        }
    }

    public void handleInputs() {
        if (this.command.equals(Actions.RC)) {
            int card = insuranceCard();
            if (card != 0) {
                Specialties specialty = chooseSpecialty();
                JsonArray tempMemory = listOfDoctors(specialty, card);
                registerCheckupsController.finishCheckupRegistry(tempMemory);
            }
        }
    }

    private int insuranceCard() {
        System.out.println("Enter your insurance card ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        InsuranceCard insuranceCard = new InsuranceCard();
        try {
            JsonObject patient = insuranceCard.getInsuranceCard().getAll()
                    .get(insuranceCard.getInsuranceCard().search("id", String.valueOf(id))).getAsJsonObject();

            System.out.println("Informations:");
            System.out.println("- ID: " + patient.get("id"));
            System.out.println("- Name: " + patient.get("name"));
            System.out.println("- Date of birth: " + patient.get("dateOfBirth"));
            System.out.println("- Phone number: " + patient.get("phoneNumber"));
            System.out.println("Are the informations above correct?\n[Y] Yes\n[N] No");
            String str = scanner.nextLine();
            if (str.equalsIgnoreCase("N") || str.equalsIgnoreCase("No")) {
                invalidID();
                return 0;
            } else if (str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("Yes")) {
                String temp = "";
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    if (patient.get("infoCustomer").getAsJsonObject().get("gender").getAsBoolean()) {
                        if (calAge(formatter
                                .parse(patient.get("dateOfBirth").getAsString())) > 30)
                            temp = "Mrs.";
                        else
                            temp = "Ms.";
                    } else
                        temp = "Mr.";
                } catch (ParseException e) {
                }
                System.out.println("Welcome, " + temp + patient.get("name").getAsString() + " to eHeathCare!");
                return id;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.print("ID doesn't exist. ");
            invalidID();
        }
        return 0;
    }

    private JsonArray listOfDoctors(Specialties specialty, int card) {
        Date now = new Date();
        String str = now.toString();
        str = str.substring(0, 2);
        str = str.toUpperCase();
        int hr = Integer.parseInt(now.toString().substring(11, 13));
        str += ((hr + 1) / 5);
        Doctor doctor = new Doctor();
        JsonArray tempMemory = doctor.getDoctor().getAll();
        do {
            int index = 0;
            String doctorName[] = new String[20];
            for (int i = 0; i < tempMemory.size(); i++) {
                for (int j = 0; j < tempMemory.get(i).getAsJsonObject().get("shifts").getAsJsonArray().size(); j++) {
                    if (Shifts
                            .valueOf(
                                    tempMemory.get(i).getAsJsonObject().get("shifts").getAsJsonArray().get(j)
                                            .getAsString())
                            .equals(Shifts.valueOf(str))) {
                        if (tempMemory.get(i).getAsJsonObject().get("specialty").getAsString()
                                .equalsIgnoreCase(specialty.toString())) {
                            System.out.print("[" + (index + 1) + "] ");
                            System.out.printf("%-40s %-40s\n",
                                    tempMemory.get(i).getAsJsonObject().get("name").getAsString(),
                                    "Current amount of patients: "
                                            + tempMemory.get(i).getAsJsonObject().get("patientsQueuing")
                                                    .getAsJsonArray().size());
                            doctorName[index] = tempMemory.get(i).getAsJsonObject().get("name").getAsString();
                            index++;
                        }
                    }
                }
            }
            try {
                System.out.println("Please, choose the doctor that you want to register with!");
                int input = scanner.nextInt();
                String name = doctorName[input - 1];
                for (int i = 0; i < tempMemory.size(); i++) {
                    if (name.equals(tempMemory.get(i).getAsJsonObject().get("name").getAsString())) {
                        JsonObject tempObject = new JsonObject();
                        tempObject.addProperty("name", tempMemory.get(i).getAsJsonObject().get("name").getAsString());
                        tempObject.addProperty("specialty",
                                tempMemory.get(i).getAsJsonObject().get("specialty").getAsString());
                        tempObject.add("shifts", tempMemory.get(i).getAsJsonObject().get("shifts").getAsJsonArray());
                        JsonArray tempArray = tempMemory.get(i).getAsJsonObject().get("patientsQueuing")
                                .getAsJsonArray();
                        StoredFiles temp = new StoredFiles("Queue.json");
                        JsonObject tempQueue = new JsonObject();
                        tempQueue.addProperty("index", temp.getPatientsNumber(1));
                        tempQueue.addProperty("card", card);
                        tempArray.add(tempQueue);
                        tempObject.add("patientsQueuing", tempArray);
                        tempMemory.set(i, tempObject);
                        return tempMemory;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Input invalid. Please try again!");
                scanner.nextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Input invalid. Please try again!");
            }
        } while (true);
    }

    private Specialties chooseSpecialty() {
        boolean specialtyExists = false;
        Specialties specialty = null;
        do {
            System.out.printf("%-30s %s", "  Specialties:", "Fees");
            System.out.printf(
                    "\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n%-30s %s\n",
                    "[NEU] Neurology", "500.000VND", "[SUR] Surgery", "200.000VND", "[PSY] Psychology", "300.000VND",
                    "[ONC] Oncology",
                    "400.000VND",
                    "[OTO] Otolarhigology", "160.000VND", "[OPT] Opthalmology", "400.000VND", "[CAR] Cardiology",
                    "300.000VND",
                    "[END] Endocriology", "200.000VND",
                    "[NEP] Nephrology", "200.000VND", "[PUL] Pulmonology", "250.000VND", "[GAS] gastroenterology",
                    "200.000VND",
                    "[URO] Urology", "200.000VND",
                    "[PED] Pediatry", "400.000VND", "[GYN] Gynaecology", "300.000VND");
            String s = scanner.nextLine();
            s = s.substring(0, 3);
            s = s.toUpperCase();
            specialtyExists = true;
            try {
                specialty = Specialties.valueOf(s);
            } catch (IllegalArgumentException e) {
                specialtyExists = false;
            }
        } while (!specialtyExists);
        return specialty;
    }

    private void invalidID() {
        System.out.println("Would you like to re-enter your insurance card ID?\n[Y] Yes\n[N] No");
        String str = scanner.nextLine();
        if (str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("Yes"))
            handleInputs();
        else if (str.equalsIgnoreCase("N") || str.equalsIgnoreCase("No"))
            return;
        else
            invalidID();
    }

    private int calAge(Date birthday) {
        Date today = new Date();
        int age = (int) today.getYear() - birthday.getYear();
        if (birthday.getMonth() > today.getMonth())
            age -= 1;
        return age;
    }
}
