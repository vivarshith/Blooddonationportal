public class Request {
    private String patientName;
    private String hospital;
    private String bloodType;
    private int unitsNeeded;
    private String urgency;
    private String contactPerson;
    private String city;

    public Request(String patientName, String hospital, String bloodType, int unitsNeeded, 
                  String urgency, String contactPerson, String city) {
        this.patientName = patientName;
        this.hospital = hospital;
        this.bloodType = bloodType;
        this.unitsNeeded = unitsNeeded;
        this.urgency = urgency;
        this.contactPerson = contactPerson;
        this.city = city;
    }

    // Getters
    public String getPatientName() { return patientName; }
    public String getHospital() { return hospital; }
    public String getBloodType() { return bloodType; }
    public int getUnitsNeeded() { return unitsNeeded; }
    public String getUrgency() { return urgency; }
    public String getContactPerson() { return contactPerson; }
    public String getCity() { return city; }
}
