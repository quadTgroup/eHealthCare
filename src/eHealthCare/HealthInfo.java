package eHealthCare;

public class HealthInfo {
    private boolean gender;
    private String bloodGroup;
    private boolean rhesus;
    private Double weight;
    private Double height;

    public HealthInfo() {
        this.bloodGroup = null;
        this.weight = null;
        this.height = null;
    }

    public HealthInfo(boolean gender, String bloodGroup, boolean rhesus, Double weight, Double height) {
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.rhesus = rhesus;
        this.weight = weight;
        this.height = height;
    }

    public boolean getGender() {
        return gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public boolean getRhesus() {
        return rhesus;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return " [bloodGroup=" + bloodGroup + ", gender=" + gender + ", height=" + height
                + ", weight=" + weight + "]";
    }

}
