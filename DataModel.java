package com.example.agendaapplication.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class DataModel {
    private static DataModel instance = new DataModel();
    private DataModel() {

    }

    public static DataModel getInstance() {
        return instance;
    }

    public ArrayList<Contact> contacts = new ArrayList<>();

    public ArrayList<String> getStringContacts() {
        ArrayList<String> stringContacts = new ArrayList<>();
        for (Contact c : contacts) {
            stringContacts.add(c.getName());
        }
        return stringContacts;
    }

    public void loadFromFile(Context context) {
        try {
            InputStream stream = context.openFileInput("contacts.txt");
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(streamReader);
            contacts.clear();
            String line;

            while ((line = reader.readLine()) != null) {
                String []aux = line.split(";");
                contacts.add(new Contact(aux[0], aux[1]));
            }
            reader.close();
            streamReader.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(Context context) {
        try {
            OutputStream stream = context.openFileOutput("contacts.txt", Context.MODE_PRIVATE);
            OutputStreamWriter write = new OutputStreamWriter(stream);

            for (Contact c : contacts) {
                write.write(c.getName() + ";" + c.getPhone() + "\n");
            }
            write.flush();
            write.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
