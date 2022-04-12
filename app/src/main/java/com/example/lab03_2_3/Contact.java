package com.example.lab03_2_3;

public class Contact {
    private int Id;
    private String Name;
    private String PhoneNumber;

    public Contact() {
    }

    public Contact(int id, String name, String phoneNumber) {
        Id = id;
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
