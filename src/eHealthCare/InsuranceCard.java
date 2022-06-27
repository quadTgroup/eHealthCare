package eHealthCare;

import java.util.Date;
import java.util.Random;

import com.google.gson.JsonArray;

public class InsuranceCard {
    private StoredFiles insuranceCard = new StoredFiles("insurancecards.json");
    private Integer id;
    private Integer type;
    private String name;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private HealthInfo healthInfo;

    public InsuranceCard() {
        this.id = null;
        this.type = null;
        this.name = null;
        this.dateOfBirth = null;
        this.address = null;
        this.phoneNumber = null;
        this.healthInfo = null;
    }

    public InsuranceCard(Integer insuranceCardType, String name, Date dateOfBirth, String address,
            String phoneNumber, HealthInfo infocustomer) {
        this.id = createID();
        this.type = insuranceCardType;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.healthInfo = infocustomer;
    }

    public Integer createID() {
        Random r = new Random();
        int newID;
        JsonArray tempMemory = getInsuranceCard().getAll();
        boolean existed = false;
        do {
            newID = r.nextInt((99999 - 10000) + 1) + 10000;
            for (int i = 0; i < tempMemory.size(); i++) {
                if (tempMemory.get(i).getAsJsonObject().get("id").getAsInt() == newID)
                    existed = true;
            }
        } while (existed);
        System.out.println(newID);
        return newID;
    }

    public StoredFiles getInsuranceCard() {
        return insuranceCard;
    }

    public Integer getid() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getInsuranceCardType() {
        return type;
    }

    public HealthInfo getInfocustomer() {
        return healthInfo;
    }

    @Override
    public String toString() {
        return "  [id=" + id + ", address=" + address + ", dateOfBirth=" + dateOfBirth + ", insuranceCardType="
                + type + ", name=" + name + ", phoneNumber=" + phoneNumber + ", infocustomer="
                + healthInfo + "]";
    }

}
