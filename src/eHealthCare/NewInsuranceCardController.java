package eHealthCare;

import java.util.Date;

public class NewInsuranceCardController {
    private InsuranceCard insuranceCard;

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public NewInsuranceCardController(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public void registerInsuranceCard(Integer id, Integer insuranceCardType, String name, Date dateOfBirth,
            String address, String phoneNumber, HealthInfo infoCustomer) {
        insuranceCard.getInsuranceCard().update(id, insuranceCardType, name, dateOfBirth, address, phoneNumber,
                infoCustomer);
        insuranceCard.getInsuranceCard().write();
    }
}
