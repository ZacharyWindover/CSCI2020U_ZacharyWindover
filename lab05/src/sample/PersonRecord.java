package sample;

public class PersonRecord {
    private String firstName;
    private String lastName;
    private String address;

    public PersonRecord(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFirstName() { return this.firstName; }

    public String getLastName() { return this.lastName; }

    public String getAddress() { return this.address; }



}
