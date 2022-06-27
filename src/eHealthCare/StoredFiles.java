package eHealthCare;

import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StoredFiles {
    private String storedFile;
    private JsonArray memory;

    public StoredFiles(String storedFile) {
        this.storedFile = storedFile;
        this.memory = read();
    }

    public void setMemory(JsonArray memory) {
        this.memory = memory;
    }

    public int search(String property, String value) {
        for (int i = 0; i < memory.size(); i++) {
            if (memory.get(i).getAsJsonObject().get(property).getAsString().equals(value))
                return i;
        }
        return memory.size();
    }

    public JsonArray read() {
        JsonArray jsonArray = null;

        try (FileReader reader = new FileReader(storedFile)) {
            jsonArray = (JsonArray) JsonParser.parseReader(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void update(Integer id, Integer insuranceCardType, String name, Date dateOfBirth, String address,
            String phoneNumber, HealthInfo infoCustomer) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        JsonObject info = new JsonObject();
        info.addProperty("gender", infoCustomer.getGender());
        info.addProperty("ABO", infoCustomer.getBloodGroup());
        info.addProperty("Rhesus", infoCustomer.getRhesus());
        info.addProperty("weight", infoCustomer.getWeight());
        info.addProperty("height", infoCustomer.getHeight());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("dateOfBirth", formatter.format(dateOfBirth));
        jsonObject.addProperty("address", address);
        jsonObject.addProperty("phoneNumber", phoneNumber);
        jsonObject.addProperty("insuranceCardType", insuranceCardType);
        jsonObject.add("infoCustomer", info);
        memory.add(jsonObject);
    }

    public void update(String username, String password, String name, int accessLevel) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("accessLevel", accessLevel);
        memory.add(jsonObject);
    }

    public void update(Specialties specialty, String diagnosis, Prescriptions[] prescriptions, int card) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("specialty", specialty.toString());
        if (memory.size() == 0) {
            jsonObject.addProperty("id", 10000);
        } else {
            jsonObject.addProperty("id", memory.get(memory.size() - 1).getAsJsonObject().get("id").getAsInt() + 1);
        }
        jsonObject.addProperty("diagnosis", diagnosis);
        jsonObject.addProperty("card", card);
        JsonArray tempArray = new JsonArray();
        for (Prescriptions p : prescriptions) {
            JsonObject tempObject = new JsonObject();
            try {
                tempObject.addProperty("medicine", p.getMedicine());
                tempObject.addProperty("morningDosage", p.getMorningDosage());
                tempObject.addProperty("noonDosage", p.getNoonDosage());
                tempObject.addProperty("afternoonDosage", p.getAfternoonDosage());
                tempObject.addProperty("eveningDosage", p.getEveningDosage());
                tempObject.addProperty("afterMeal", p.isAfterMeal());
                tempObject.addProperty("unitPrice", p.getUnitPrice());
                tempObject.addProperty("days", p.getConsumingDays());
            } catch (NullPointerException e) {
                break;
            }
            tempArray.add(tempObject);
        }
        jsonObject.add("prescriptions", tempArray);
        memory.add(jsonObject);
    }

    public void update(String name, Specialties specialty, Shifts[] shift) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("specialty", specialty.toString());
        JsonArray shifts = new JsonArray();
        for (int i = 0; shift[i] != null; i++)
            shifts.add(shift[i].toString());
        jsonObject.add("shifts", shifts);
        JsonArray tempArray = new JsonArray();
        jsonObject.add("patientsQueuing", tempArray);
        memory.add(jsonObject);
    }

    public void write() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(storedFile)) {
            gson.toJson(memory, writer);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public int getPatientsNumber(int i) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        if (!memory.get(0).getAsJsonObject().get("date").getAsString().equals(format.format(today))) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("currentNumber", 100);
            jsonObject.addProperty("date", format.format(today));
            JsonArray newMemory = new JsonArray();
            newMemory.add(jsonObject);
            setMemory(newMemory);
            write();
        }
        read();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("currentNumber", memory.get(0).getAsJsonObject().get("currentNumber").getAsInt() + i);
        jsonObject.addProperty("date", format.format(today));
        JsonArray newMemory = new JsonArray();
        newMemory.add(jsonObject);
        setMemory(newMemory);
        write();
        return memory.get(0).getAsJsonObject().get("currentNumber").getAsInt();
    }

    public JsonArray getAll() {
        return this.memory;
    }
}
