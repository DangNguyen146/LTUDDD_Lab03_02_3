package com.example.lab03_2_3;

import androidx.appcompat.app.AppCompatActivity;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    MaterialButton btnAdd;
    RecyclerView rvContact;
    ContactAdapter adapter;
    ArrayList<Contact> contacts;
    ContactTable contactTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitUiElement();
        GetContactsFromDatabase();
        InitRecyclerView();

        btnAdd.setOnClickListener(v -> HandleAddContact());
    }

    private void InitUiElement() {
        btnAdd = findViewById(R.id.btn_add);
        rvContact = findViewById(R.id.recyclerview);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void GetContactsFromDatabase() {
        contactTable = new ContactTable(this);
        ArrayList<Contact> oldContacts = contactTable.ListContact();
        if (oldContacts != null) {
            contacts = oldContacts;
        } else {
            contacts = new ArrayList<>();
        }
    }

    private void InitRecyclerView() {

        adapter = new ContactAdapter(this, contacts, new ContactAdapter.ItemClick() {
            @Override
            public void OnEditClick(int position) {
                HandleEditClick(position);
            }

            @Override
            public void OnDeleteClick(int position) {
                HandleDeleteClick(position);
            }
        });
        rvContact.setAdapter(adapter);
        rvContact.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
    }


    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void HandleAddContact() {
        LayoutInflater layoutInflater = android.view.LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.add_contact_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(view);
        TextInputEditText edtName = view.findViewById(R.id.edt_username);
        TextInputEditText edtPhone = view.findViewById(R.id.edt_password);
        TextView tvTitle = view.findViewById(R.id.dialog_title);

        tvTitle.setText("Add Contact");

        dialogBuilder.setNegativeButton("Finish", (dialog, which) -> {
            String name = Objects.requireNonNull(edtName.getText()).toString().trim();
            String phone = Objects.requireNonNull(edtPhone.getText()).toString().trim();

            if (name.equals("")) {
                Toast.makeText(MainActivity.this, "Missing Name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phone.equals("")) {
                Toast.makeText(MainActivity.this, "Missing Phone Number", Toast.LENGTH_SHORT).show();
                return;
            }

            Contact contact = contactTable.AddContact(name, phone);
            if (contact != null) {
                contacts.add(contact);
                adapter.notifyDataSetChanged();
            }
        });
        dialogBuilder.setPositiveButton("CANCEL", (dialog, which) -> Toast.makeText(MainActivity.this, "CANCEL", Toast.LENGTH_SHORT).show());
        dialogBuilder.create().show();
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void HandleEditClick(int position) {
        Contact contact = contacts.get(position);
        LayoutInflater layoutInflater = android.view.LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.add_contact_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(view);
        TextInputEditText edtName = view.findViewById(R.id.edt_username);
        TextInputEditText edtPhone = view.findViewById(R.id.edt_password);
        TextView tvTitle = view.findViewById(R.id.dialog_title);

        tvTitle.setText("Edit Contact");
        edtName.setText(contact.getName());
        edtPhone.setText(contact.getPhoneNumber());

        dialogBuilder.setNegativeButton("Save", (dialog, which) -> {
            String name = Objects.requireNonNull(edtName.getText()).toString().trim();
            String phone = Objects.requireNonNull(edtPhone.getText()).toString().trim();

            if (name.equals("")) {
                Toast.makeText(MainActivity.this, "Missing Name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phone.equals("")) {
                Toast.makeText(MainActivity.this, "Missing Phone Number", Toast.LENGTH_SHORT).show();
                return;
            }


            Contact newContact = contactTable.UpdateContact(contact.getId(), name, phone);
            contacts.remove(position);
            contacts.add(position, newContact);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
        });
        dialogBuilder.setPositiveButton("CANCEL", (dialog, which) -> Toast.makeText(MainActivity.this, "CANCEL", Toast.LENGTH_SHORT).show());
        dialogBuilder.create().show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void HandleDeleteClick(int position) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
        dialogBuilder.setTitle("Xác nhận");
        dialogBuilder.setMessage("Bạn có thực sự muốn xóa");
        dialogBuilder.setIcon(R.mipmap.ic_launcher);
        dialogBuilder.setPositiveButton("NO", (dialog, which) -> Toast.makeText(MainActivity.this, "NO", Toast.LENGTH_SHORT).show());
        dialogBuilder.setNegativeButton("YES", (dialog, which) -> {
            int id = contacts.get(position).getId();
            contactTable.DeleteContact(id);
            contacts.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
        });
        dialogBuilder.show();
    }
}
