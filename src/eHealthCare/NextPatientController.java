package eHealthCare;

import com.google.gson.JsonArray;

public class NextPatientController {
    Account account = new Account();

    public NextPatientController(Account account) {
        this.account = account;
    }

    public void queueProcessing() {
        Doctor doctor = new Doctor();
        JsonArray newMemory = doctor.getDoctor().getAll();
        for (int i = 0; i < newMemory.size(); i++) {
            if (this.account.getName()
                    .equals(newMemory.get(i).getAsJsonObject().get("name").getAsString())) {
                newMemory.get(i).getAsJsonObject().get("patientsQueuing").getAsJsonArray().remove(0);
                break;
            }
        }
        doctor.getDoctor().setMemory(newMemory);
        doctor.getDoctor().write();
    }

}
