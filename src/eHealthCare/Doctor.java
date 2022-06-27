package eHealthCare;

public class Doctor {
    private StoredFiles doctor = new StoredFiles("Doctors.json");
    private String name;
    private Specialties specialty;
    private Shifts shifts[];
    private int patientsQueuing[];

    public Doctor() {
        name = null;
        specialty = null;
        shifts = new Shifts[35];
        patientsQueuing = new int[20];
    }

    public Doctor(String name, Specialties specialty, Shifts[] shifts, int[] patientsQueuing) {
        this.name = name;
        this.specialty = specialty;
        this.shifts = shifts;
        this.patientsQueuing = patientsQueuing;
    }

    public Specialties getspecialty() {
        return specialty;
    }

    public Shifts[] getShifts() {
        return shifts;
    }

    public String getName() {
        return name;
    }

    public int[] getPatientsQueuing() {
        return patientsQueuing;
    }

    public void setShifts(Shifts[] shifts) {
        this.shifts = shifts;
    }

    public StoredFiles getDoctor() {
        return doctor;
    }
}
