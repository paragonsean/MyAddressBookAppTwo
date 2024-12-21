package com.sbake021.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/**
 * Adapter class for displaying contact data in a RecyclerView.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private final ArrayList<Contact> contactData;
    private View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private final Context parentContext;

    /**
     * Constructor for ContactAdapter.
     * @param arrayList The list of contacts to display.
     * @param context The context in which the adapter is being used.
     */
    public ContactAdapter(ArrayList<Contact> arrayList, Context context) {
        contactData = arrayList;
        parentContext = context;
    }

    /**
     * Sets the click listener for items in the RecyclerView.
     * @param itemClickListener The click listener to set.
     */
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    /**
     * Enables or disables delete functionality.
     * @param isDeleteEnabled True to enable delete functionality, false to disable.
     */
    public void setDelete(boolean isDeleteEnabled) {
        isDeleting = isDeleteEnabled;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ContactViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_layout, parent, false);
        return new ContactViewHolder(v);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact currentContact = contactData.get(position);
        holder.getTextName().setText(currentContact.getName());
        holder.getTextAddress().setText(currentContact.getAddress());
        holder.getTextCity().setText(currentContact.getCity());
        holder.getTextState().setText(currentContact.getState());
        holder.getTextZipCode().setText(currentContact.getZipCode());
        holder.getTextPhone().setText(currentContact.getPhone());
        holder.getTextEmail().setText(currentContact.getEmail());

        if (isDeleting) {
            holder.getDeleteButton().setVisibility(View.VISIBLE);
            holder.getDeleteButton().setOnClickListener(view -> deleteItem(position));
        } else {
            holder.getDeleteButton().setVisibility(View.INVISIBLE);
        }

        // Set a click listener for the item view
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(parentContext, ContactMapActivity.class);
            intent.putExtra("SELECTED_CONTACT", (Parcelable) currentContact); // Pass the selected contact
            parentContext.startActivity(intent);
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return contactData.size();
    }

    /**
     * Deletes a contact from the data set and the database.
     * @param position The position of the contact to delete.
     */
    private void deleteItem(int position) {
        Contact contact = contactData.get(position);
        ContactDataSource ds = new ContactDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteContact(contact.getId());
            ds.close();
            if (didDelete) {
                contactData.remove(position);
                notifyItemRemoved(position); // Notify only the removed item
                Toast.makeText(parentContext, "Contact deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(parentContext, "Error deleting contact", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * ViewHolder class for holding contact item views.
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName, textAddress, textCity, textState, textZipCode, textPhone, textEmail;
        private final Button deleteButton;

        /**
         * Constructor for ContactViewHolder.
         * @param itemView The view of the contact item.
         */
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textView_name);
            textAddress = itemView.findViewById(R.id.textView_address);
            textCity = itemView.findViewById(R.id.textView_city);
            textState = itemView.findViewById(R.id.textView_state);
            textZipCode = itemView.findViewById(R.id.textView_zip);
            textPhone = itemView.findViewById(R.id.textView_phone);
            textEmail = itemView.findViewById(R.id.textView_email);
            deleteButton = itemView.findViewById(R.id.button_delete);

            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getTextName() { return textName; }
        public TextView getTextAddress() { return textAddress; }
        public TextView getTextCity() { return textCity; }
        public TextView getTextState() { return textState; }
        public TextView getTextZipCode() { return textZipCode; }
        public TextView getTextPhone() { return textPhone; }
        public TextView getTextEmail() { return textEmail; }
        public Button getDeleteButton() { return deleteButton; }
    }
}