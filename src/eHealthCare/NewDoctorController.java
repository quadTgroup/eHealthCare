package eHealthCare;

public class NewDoctorController {

    Doctor doctor;

    public NewDoctorController(Doctor doctor) {
        this.doctor = doctor;
    }

    public void newDoctorProfile(Doctor doctor) {
        doctor.getDoctor().update(doctor.getName(), doctor.getspecialty(), doctor.getShifts());
        doctor.getDoctor().write();
    }
}
