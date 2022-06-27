package eHealthCare;

public class MedicalBill {

    private StoredFiles medicalBill = new StoredFiles("MedicalBills.json");
    private Specialties specialties;
    private String diagnosis;
    private Prescriptions prescriptions[];
    private int customersCardNumber;

    public MedicalBill() {
        diagnosis = null;
        prescriptions = new Prescriptions[10];
    }

    public MedicalBill(Specialties specialties, String diagnosis, Prescriptions[] prescriptions,
            int customersCardNumber) {
        this.specialties = specialties;
        this.diagnosis = diagnosis;
        this.prescriptions = prescriptions;
        this.customersCardNumber = customersCardNumber;
    }

    public Specialties getSpecialties() {
        return specialties;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Prescriptions[] getPrescriptions() {
        return prescriptions;
    }

    public int getCustomersCardNumber() {
        return customersCardNumber;
    }

    public StoredFiles getMedicalBill() {
        return medicalBill;
    }
}
