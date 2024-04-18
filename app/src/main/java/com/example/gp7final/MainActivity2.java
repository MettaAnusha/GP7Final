package com.example.gp7final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gp7final.R;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {

    private EditText nameEditText;
    private Spinner frequencySpinner;
    private Spinner morningSpinner;
    private Spinner afternoonSpinner;
    private Spinner eveningSpinner;
    private Spinner quantitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nameEditText = findViewById(R.id.name);
        frequencySpinner = findViewById(R.id.spinner2);
        morningSpinner = findViewById(R.id.spinner3);
        afternoonSpinner = findViewById(R.id.spinner4);
        eveningSpinner = findViewById(R.id.spinner5);
        quantitySpinner = findViewById(R.id.spinner);

        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the EditText and spinners
                String name = nameEditText.getText().toString().trim();
                String frequency = frequencySpinner.getSelectedItem().toString();
                String morning = morningSpinner.getSelectedItem().toString();
                String afternoon = afternoonSpinner.getSelectedItem().toString();
                String evening = eveningSpinner.getSelectedItem().toString();
                String quantity = quantitySpinner.getSelectedItem().toString();

                // Check if any of the fields are empty
                if (name.isEmpty() || frequency.isEmpty() || morning.isEmpty() || afternoon.isEmpty() || evening.isEmpty() || quantity.isEmpty()) {
                    // Show a toast message indicating that all fields are required
                    Toast.makeText(MainActivity2.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    // Save data to SharedPreferences
                    saveDataToSharedPreferences();
                    scheduleNotification();

                    // Move to another activity
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    startActivity(intent);
                }
            }
        });


        String[] frequency = {"1", "2", "3"};
        ArrayAdapter<String> adapterFrequency = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, frequency);
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(adapterFrequency);

        String[] morning = {"5:00","6:00","7:00","8:00","9:00","10:00","11:00"};
        ArrayAdapter<String> adapterMorning = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, morning);
        adapterMorning.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        morningSpinner.setAdapter(adapterMorning);

        String[] evening = {"19:00","20:00","21:00","22:00","23:00"};
        ArrayAdapter<String> adapterEvening = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, evening);
        adapterEvening.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eveningSpinner.setAdapter(adapterEvening);

        String[] afternoon = {"12:00","13:00","14:00","15:00","16:00","17:00","18:00"};
        ArrayAdapter<String> adapterAfternoon = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, afternoon);
        adapterAfternoon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        afternoonSpinner.setAdapter(adapterAfternoon);

        String[] quantity = {"1", "2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> adapterQuantity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantity);
        adapterQuantity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(adapterQuantity);
        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFrequency = (String) parent.getItemAtPosition(position);
                updateSpinnerVisibility(selectedFrequency);
            }


            // Initially set spinner visibility based on selected frequency
//        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedFrequency = (String) parent.getItemAtPosition(position);
//                updateSpinnerVisibility(selectedFrequency);
//            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where nothing is selected
            }
        });
    }

    private void updateSpinnerVisibility(String selectedFrequency) {
        int spinner3Visibility = View.INVISIBLE;
        int spinner4Visibility = View.INVISIBLE;
        int spinner5Visibility = View.INVISIBLE;

        if ("1".equals(selectedFrequency)) {
            spinner3Visibility = View.VISIBLE;
        } else if ("2".equals(selectedFrequency)) {
            spinner3Visibility = View.VISIBLE;
            spinner4Visibility = View.VISIBLE;
        } else if ("3".equals(selectedFrequency)) {
            spinner3Visibility = View.VISIBLE;
            spinner4Visibility = View.VISIBLE;
            spinner5Visibility = View.VISIBLE;
        }

        morningSpinner.setVisibility(spinner3Visibility);
        afternoonSpinner.setVisibility(spinner4Visibility);
        eveningSpinner.setVisibility(spinner5Visibility);
    }

    private void saveDataToSharedPreferences() {
        String medName = nameEditText.getText().toString();
        String frequency = frequencySpinner.getSelectedItem().toString();
        String morning = morningSpinner.getSelectedItem().toString();
        String afternoon = afternoonSpinner.getSelectedItem().toString();
        String evening = eveningSpinner.getSelectedItem().toString();
        String quantity = quantitySpinner.getSelectedItem().toString();

        if (medName.isEmpty()) {
            Toast.makeText(this, "Please enter medicine name", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("name", medName);
        editor.putString("frequency", frequency);
        editor.putString("morning", morning);
        editor.putString("afternoon", afternoon);
        editor.putString("evening", evening);
        editor.putString("quantity", quantity);

        editor.apply();

        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
    }
    private void scheduleNotification() {
        // Get selected time from the spinners
        String morningTime = morningSpinner.getSelectedItem().toString();
        String afternoonTime = afternoonSpinner.getSelectedItem().toString();
        String eveningTime = eveningSpinner.getSelectedItem().toString();

        // Convert time strings to Calendar instances
        Calendar morningCalendar = getTimeCalendar(morningTime);
        Calendar afternoonCalendar = getTimeCalendar(afternoonTime);
        Calendar eveningCalendar = getTimeCalendar(eveningTime);

        // Schedule notifications for each time
        scheduleNotification(morningCalendar);
        scheduleNotification(afternoonCalendar);
        scheduleNotification(eveningCalendar);
    }

    private Calendar getTimeCalendar(String timeString) {
        String[] parts = timeString.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

    private void scheduleNotification(Calendar calendar) {
        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}
