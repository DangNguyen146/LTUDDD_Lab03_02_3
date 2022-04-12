package com.example.lab03_2_3;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class ContactTable {
    Context context;
    Database database;

    public ContactTable(Context context) {
        this.context = context;
        database = new Database(context, "contact.db", null, 1);
        CreateTable();
    }

    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS CONTACT (Id INTEGER PRIMARY KEY AUTOINCREMENT, Username VARCHAR(50), PhoneNumber VARCHAR(10))";
        database.QueryData(query);
    }

    public Contact AddContact(String name, String phoneNumber) {
//        Contact contact = null;
        String query = "INSERT INTO CONTACT VALUES(null, '" + name + "', '" + phoneNumber + "')";
        database.QueryData(query);
//        Cursor cursor = database.GetData("SELECT * FROM CONTACT WHERE Username = '" + name + "' AND PhoneNumber = '" + phoneNumber + "' ORDER BY Id DESC");
//        if (cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String Username = cursor.getString(1);
//            String PhoneNumber = cursor.getString(2);
//            contact = new Contact(id, Username, PhoneNumber);
//        }

        ArrayList<Contact> contacts = GetContactWithCondition("Username = '" + name + "' AND PhoneNumber = '" + phoneNumber + "' ORDER BY Id DESC");

        return contacts.get(0);
    }

    public Contact UpdateContact(int id, String name, String phoneNumber) {
        database.QueryData("UPDATE CONTACT SET Username = '" + name + "', PhoneNumber = '" + phoneNumber + "' WHERE Id = " + id);
        return new Contact(id, name, phoneNumber);
    }

    public void DeleteContact(int id) {
        database.QueryData("DELETE FROM CONTACT WHERE Id = " + id);
    }

    public ArrayList<Contact> ListContact() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.GetData("SELECT * FROM CONTACT");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String Username = cursor.getString(1);
            String PhoneNumber = cursor.getString(2);
            Contact contact = new Contact(id, Username, PhoneNumber);
            contacts.add(contact);
        }
        return contacts;
    }

    public ArrayList<Contact> GetContactWithCondition(String where) {
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.GetData("SELECT * FROM CONTACT WHERE " + where);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String Username = cursor.getString(1);
            String PhoneNumber = cursor.getString(2);
            Contact contact = new Contact(id, Username, PhoneNumber);
            contacts.add(contact);
        }
        return contacts;
    }

    public Contact GetContactById(int id){
        ArrayList<Contact> contacts  = GetContactWithCondition("Id = " + id);
        if(contacts == null){
            return null;
        }

        return contacts.get(0);
    }
}
