package eHealthCare;

import com.google.gson.JsonArray;

public class RegisterCheckupsController {
    Doctor doctor = new Doctor();

    public RegisterCheckupsController() {
    }

    public void finishCheckupRegistry(JsonArray tempMemory) {
        doctor.getDoctor().setMemory(tempMemory);
        doctor.getDoctor().write();
        StoredFiles queue = new StoredFiles("Queue.json");
        System.out.println("Your queuing number: " + queue.getPatientsNumber(0));
    }
}
