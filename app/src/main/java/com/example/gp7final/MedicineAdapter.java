package com.example.gp7final;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp7final.R;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private List<Medicine> medicineList;

    public MedicineAdapter(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MedicineViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.bind(medicine);
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    static class MedicineViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView quantityTextView;
        private TextView frequencyTextView;
        private TextView morningTextView;
        private TextView afternoonTextView;
        private TextView nightTextView;
        private Button deleteButton;
        private MedicineAdapter adapter;

        public MedicineViewHolder(@NonNull View itemView, MedicineAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            nameTextView = itemView.findViewById(R.id.medname);
            quantityTextView = itemView.findViewById(R.id.quantity);
            frequencyTextView = itemView.findViewById(R.id.frequency);
            morningTextView = itemView.findViewById(R.id.morning);
            afternoonTextView = itemView.findViewById(R.id.afternoon);
            nightTextView = itemView.findViewById(R.id.night);
            deleteButton = itemView.findViewById(R.id.button19);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the position of the item to be deleted
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // Remove the item from the list
                            Medicine deletedMedicine = adapter.medicineList.remove(position);
                            // Remove corresponding data from SharedPreferences
                            removeMedicineFromSharedPreferences(itemView.getContext(), deletedMedicine);
                            // Notify adapter that an item is removed at the specified position
                            adapter.notifyItemRemoved(position);
                        }
                    }
                });
            }

            private void removeMedicineFromSharedPreferences(Context context, Medicine medicine) {
                // Get SharedPreferences instance
                SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                // Remove medicine data using its unique identifier (e.g., name)
                preferences.edit().remove(medicine.getName()).apply();
            }


            public void bind(Medicine medicine) {
            nameTextView.setText(medicine.getName());
            quantityTextView.setText(String.valueOf("Quantity:" + medicine.getQuantity()));
            frequencyTextView.setText(String.valueOf("Frequency:" + medicine.getFrequency()));
            //            morningTextView.setText(medicine.getMorning());
            //            afternoonTextView.setText(medicine.getAfternoon());
            //            nightTextView.setText(medicine.getNight());
        }
    }
}
