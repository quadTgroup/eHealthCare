package eHealthCare;

public class NewPrescriptionsController {
    MedicalBill medicalBill;
    Account account;

    public NewPrescriptionsController(Account account) {
        this.account = account;
    }

    public void savePrescriptions(MedicalBill medicalBill) {
        medicalBill.getMedicalBill().update(medicalBill.getSpecialties(), medicalBill.getDiagnosis(),
                medicalBill.getPrescriptions(), medicalBill.getCustomersCardNumber());
        medicalBill.getMedicalBill().write();
    }
}
