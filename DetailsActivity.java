package com.example.agendaapplication.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.example.agendaapplication.R;
import com.example.agendaapplication.model.Contact;
import com.example.agendaapplication.model.DataModel;

public class DetailsActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText phoneEditText;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);

        Bundle extra = getIntent().getExtras();
        index = extra.getInt("index");
        if (index != -1) {
            Contact c = DataModel.getInstance().contacts.get(index);
            nameEditText.setText(c.getName());
            phoneEditText.setText(c.getPhone());
        }
    }

    @Override
    public void onBackPressed() {
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if (name.length() > 1 && phone.length() > 1) {
            if (index == -1) {
                DataModel.getInstance().contacts.add(
                        new Contact(name, phone)
                );
            } else {
                Contact c = DataModel.getInstance().contacts.get(index);
                c.setName(name);
                c.setPhone(phone);
            }
            DataModel.getInstance().saveToFile(DetailsActivity.this);
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.empty_contact_alert_msg);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNegativeButton(android.R.string.no, null);
            builder.create().show();
        }

    }
}
