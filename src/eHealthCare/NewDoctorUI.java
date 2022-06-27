package eHealthCare;

import java.util.Scanner;

import com.google.gson.JsonArray;

public class NewDoctorUI {
    NewDoctorController newDoctorController;
    Actions command;
    Scanner scanner = new Scanner(System.in);

    public NewDoctorUI(NewDoctorController newDoctorController) {
        this.newDoctorController = newDoctorController;
        this.command = null;
    }

    public String handleCommands(String rep) {
        String cmd = rep.toUpperCase();
        this.command = Actions.valueOf(cmd);
        if (this.command.equals(Actions.ND))
            return "Creating a new doctor profile.";
        return "An error has occurred while creating a new doctor profile.";
    }

    public void handleInputs(String name) {
        if (this.command.equals(Actions.ND)) {
            Doctor doctor = doctorProfileForm(name);
            newDoctorController.newDoctorProfile(doctor);
        }
    }

    private Doctor doctorProfileForm(String name) {
        System.out.println("Fill in this form with the new doctor's informations to finish their profile:");
        boolean specialtyExists = false;
        Specialties s = null;
        do {
            System.out.println(
                    "Specialty:\n" + "[NEU] Neurology\n" + "[SUR] Surgery\n" + "[PSY] Psychology\n" + "[ONC] Oncology\n"
                            + "[OTO] Otolarhigology\n" + "[OPT] Opthalmology\n" + "[CAR] Cardiology\n"
                            + "[END] Endocriology\n" + "[NEP] Nephrology\n" + "[PUL] Pulmonology\n"
                            + "[GAS] gastroenterology\n" + "[URO] Urology\n" + "[PED] Pediatry\n"
                            + "[GYN] Gynaecology");
            String specialty = scanner.nextLine();
            specialty = specialty.substring(0, 3);
            specialty = specialty.toUpperCase();
            specialtyExists = true;
            try {
                s = Specialties.valueOf(specialty);
            } catch (IllegalArgumentException e) {
                specialtyExists = false;
            }
        } while (!specialtyExists);
        Shifts shifts[] = schedule(s);
        System.out.println("New doctor has been registered into the database.");
        return new Doctor(name, s, shifts, new int[20]);
    }

    public Shifts[] schedule(Specialties s) {
        Shifts shifts[] = new Shifts[35];
        String colorReset = "\u001B[0m";
        for (int j = 0; true; j++) {
            System.out.println("Shift schedule:");
            System.out.printf("%-20s %-11s %-11s %-11s %-11s %-11s %-11s %-11s\n", "", "Monday",
                    "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------------------");
            for (int i = 0; i < 5; i++) {
                String str;
                switch (i) {
                    case 0:
                        str = "Night (12PM-4AM)";
                        break;
                    case 1:
                        str = "Morning (4AM-9AM)";
                        break;
                    case 2:
                        str = "Noon (9AM-2PM)";
                        break;
                    case 3:
                        str = "Afternoon (2PM-7PM)";
                        break;
                    case 4:
                        str = "Evening (7PM-12PM)";
                        break;
                    default:
                        str = "Error!";
                        break;
                }
                String monday = shiftColor(Shifts.valueOf("MO" + i), s) + "[" + "MO" + i + "]" + colorReset;
                String tuesday = shiftColor(Shifts.valueOf("TU" + i), s) + "[" + "TU" + i + "]" + colorReset;
                String wednesday = shiftColor(Shifts.valueOf("WE" + i), s) + "[" + "WE" + i + "]" + colorReset;
                String thursday = shiftColor(Shifts.valueOf("TH" + i), s) + "[" + "TH" + i + "]" + colorReset;
                String friday = shiftColor(Shifts.valueOf("FR" + i), s) + "[" + "FR" + i + "]" + colorReset;
                String saturday = shiftColor(Shifts.valueOf("SA" + i), s) + "[" + "SA" + i + "]" + colorReset;
                String sunday = shiftColor(Shifts.valueOf("SU" + i), s) + "[" + "SU" + i + "]" + colorReset;
                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", str, monday,
                        tuesday, wednesday, thursday, friday, saturday, sunday);
            }
            String red = "\u001B[31m" + "red" + colorReset;
            String yellow = "\u001B[33m" + "yellow" + colorReset;
            System.out.println("*" + red + " shifts currently have no doctor with the same specialty.");
            System.out.println("*" + yellow + " shifts currently only have 1 doctor with the same specialty.");
            System.out.println("Type 'Done' if you are done with the shifts.");
            String inputs = scanner.nextLine();
            if (inputs.equalsIgnoreCase("Done") || j == 34)
                break;
            else
                try {
                    shifts[j] = Shifts.valueOf(inputs.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Input invalid. Please try again!");
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException ee) {
                    }
                }
        }
        return shifts;
    }

    private String shiftColor(Shifts shifts, Specialties specialties) {
        int amount = 0;
        Doctor doctor = new Doctor();
        JsonArray tempMemory = doctor.getDoctor().getAll();
        for (int i = 0; i < tempMemory.size(); i++) {
            for (int j = 0; j < tempMemory.get(i).getAsJsonObject().get("shifts").getAsJsonArray().size(); j++) {
                if (shifts
                        .equals(Shifts.valueOf(tempMemory.get(i).getAsJsonObject().get("shifts").getAsJsonArray().get(j)
                                .getAsString()))
                        && specialties.equals(Specialties
                                .valueOf(tempMemory.get(i).getAsJsonObject().get("specialty").getAsString()))) {
                    amount++;
                }
            }
        }
        if (amount == 0)
            return "\u001B[31m";
        else if (amount == 1)
            return "\u001B[33m";
        else
            return "\u001B[32m";
    }
}
