package eHealthCare;

import com.google.gson.JsonObject;

public class GetBillController {

    public double getSpecialtyFees(Specialties specialty) {
        switch (specialty.toString()) {
            case "NEU":
                return 500000;
            case "SUR":
                return 200000;
            case "PSY":
                return 300000;
            case "ONC":
                return 400000;
            case "OTO":
                return 160000;
            case "OPT":
                return 400000;
            case "CAR":
                return 300000;
            case "END":
                return 200000;
            case "NEP":
                return 200000;
            case "PUL":
                return 250000;
            case "GAS":
                return 200000;
            case "URO":
                return 200000;
            case "PED":
                return 400000;
            case "GYN":
                return 300000;
            default:
                return 0;
        }
    }

    public double getMedicineFees(JsonObject jsonObject) {
        double fees = 0;
        fees += (jsonObject.get("unitPrice").getAsDouble() * (jsonObject.get("morningDosage").getAsDouble()
                + jsonObject.get("noonDosage").getAsDouble() + jsonObject.get("afternoonDosage").getAsDouble()
                + jsonObject.get("eveningDosage").getAsDouble())) * jsonObject.get("days").getAsDouble();
        return fees;
    }
}
