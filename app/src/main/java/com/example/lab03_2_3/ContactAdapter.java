package com.example.lab03_2_3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{
    Context context;
    ArrayList<Contact> contacts;
    ItemClick itemClick;

    public ContactAdapter(Context context, ArrayList<Contact> contacts, ItemClick itemClick) {
        this.context = context;
        this.contacts = contacts;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        int stt = position + 1;
        holder.tvSTT.setText(stt + "");
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhoneNumber());
        holder.btnEdit.setOnClickListener(v -> itemClick.OnEditClick(position));
        holder.btnDelete.setOnClickListener(v -> itemClick.OnDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        if (contacts == null)
            return 0;
        return contacts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvSTT;
        AppCompatImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSTT = itemView.findViewById(R.id.tv_stt);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_sdt);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    public interface ItemClick {
        void OnEditClick(int position);
        void OnDeleteClick(int position);
    }
}
