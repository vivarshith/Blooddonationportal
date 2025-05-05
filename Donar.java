public class Donor {
    private String name;
    private int age;
    private String bloodType;
    private String contact;
    private String city;
    private int lastDonationDays;

    public Donor(String name, int age, String bloodType, String contact, String city, int lastDonationDays) {
        this.name = name;
        this.age = age;
        this.bloodType = bloodType;
        this.contact = contact;
        this.city = city;
        this.lastDonationDays = lastDonationDays;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getBloodType() { return bloodType; }
    public String getContact() { return contact; }
    public String getCity() { return city; }
    public int getLastDonationDays() { return lastDonationDays; }
}
