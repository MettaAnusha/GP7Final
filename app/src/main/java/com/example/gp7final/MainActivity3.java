package com.example.gp7final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        // Retrieve data from SharedPreferences
        // Retrieve data from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String medName = preferences.getString("name", "");
        String frequency = preferences.getString("frequency", "");
        String morning = preferences.getString("morning", "");
        String afternoon = preferences.getString("afternoon", "");
        String evening = preferences.getString("evening", "");
        String quantity = preferences.getString("quantity", "");

// Check if quantity is not empty before parsing it
        int parsedQuantity = 0;
        if (!quantity.isEmpty()) {
            parsedQuantity = Integer.parseInt(quantity);
        }
        int parsedQuantity1=0;
        if (!frequency.isEmpty()) {
            parsedQuantity1 = Integer.parseInt(frequency);
        }


// Create a new Medicine object with the parsed quantity
        Medicine medicine = new Medicine(medName, parsedQuantity, parsedQuantity1);

// Create a list to hold the Medicine object
        List<Medicine> medicineList = new ArrayList<>();
        medicineList.add(medicine);

// Set up RecyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MedicineAdapter adapter = new MedicineAdapter(medicineList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}