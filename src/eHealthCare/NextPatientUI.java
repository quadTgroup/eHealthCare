package eHealthCare;

import java.util.Scanner;

import com.google.gson.JsonObject;

public class NextPatientUI {
    NextPatientController nextPatientController;
    Scanner scanner = new Scanner(System.in);
    Actions command;

    public NextPatientUI(NextPatientController nextPatientController) {
        this.nextPatientController = nextPatientController;
        this.command = null;
    }

    public String handleCommands(String rep) {
        this.command = Actions.valueOf(rep.toUpperCase());
        if (this.command.equals(Actions.NP)) {
            return "Next Patient!";
        }
        return "Unknown command.";
    }

    public void handleInputs() {
        if (this.command.equals(Actions.NP)) {
            nextPatient();
            nextPatientController.queueProcessing();
        }
    }

    private void nextPatient() {
        Doctor doctor = new Doctor();
        try {
            JsonObject tempObject = doctor.getDoctor().getAll()
                    .get(doctor.getDoctor().search("name", nextPatientController.account.getName())).getAsJsonObject();
            if (tempObject.get("patientsQueuing").getAsJsonArray().size() > 1) {
                System.out.println("Your next patient's number: "
                        + tempObject.get("patientsQueuing").getAsJsonArray().get(1));
            } else
                System.out.println("Take a rest! There are no patients in queue, yet.");
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
