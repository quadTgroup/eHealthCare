package eHealthCare;

public class Prescriptions {
    private String medicine;
    private int morningDosage;
    private int noonDosage;
    private int afternoonDosage;
    private int eveningDosage;
    private boolean afterMeal;
    private double unitPrice;
    private int consumingDays;

    public Prescriptions(String medicine, int morningDosage, int noonDosage, int afternoonDosage, int eveningDosage,
            boolean afterMeal, double unitPrice, int consumingDays) {
        this.medicine = medicine;
        this.morningDosage = morningDosage;
        this.noonDosage = noonDosage;
        this.afternoonDosage = afternoonDosage;
        this.eveningDosage = eveningDosage;
        this.afterMeal = afterMeal;
        this.unitPrice = unitPrice;
        this.consumingDays = consumingDays;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getMedicine() {
        return medicine;
    }

    public int getMorningDosage() {
        return morningDosage;
    }

    public int getNoonDosage() {
        return noonDosage;
    }

    public int getAfternoonDosage() {
        return afternoonDosage;
    }

    public int getEveningDosage() {
        return eveningDosage;
    }

    public boolean isAfterMeal() {
        return afterMeal;
    }

    public int getConsumingDays() {
        return consumingDays;
    }

}
