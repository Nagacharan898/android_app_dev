package com.example.currencyconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText etRupees;
    private Button btnConvert;
    private ListView listViewCurrency;
    private TextView tvError;
    private HashMap<String, Double> currencyRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI elements to variables
        etRupees = findViewById(R.id.etRupees);
        btnConvert = findViewById(R.id.btnConvert);
        listViewCurrency = findViewById(R.id.listViewCurrency);
        tvError = findViewById(R.id.tvError);

        // Initialize currency exchange rates
        currencyRates = new HashMap<>();
        currencyRates.put("USD (🇺🇸)", 0.012);
        currencyRates.put("Euro (🇪🇺)", 0.011);
        currencyRates.put("British Pound (🇬🇧)", 0.009);
        currencyRates.put("Japanese Yen (🇯🇵)", 1.74);
        currencyRates.put("Australian Dollar (🇦🇺)", 0.018);

        // Initially disable the convert button
        btnConvert.setEnabled(false);

        // Add TextWatcher to validate input dynamically
        etRupees.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateInput(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        // Set onClickListener for the convert button
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etRupees.getText().toString();
                double rupees = Double.parseDouble(input);
                ArrayList<String> conversionResults = new ArrayList<>();

                // Calculate conversion for each currency
                for (String country : currencyRates.keySet()) {
                    double rate = currencyRates.get(country);
                    double convertedValue = rupees * rate;
                    conversionResults.add(country + ": " + String.format("%.2f", convertedValue));
                }

                // Display conversion results
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, conversionResults);
                listViewCurrency.setAdapter(adapter);
            }
        });
    }

    // Method to validate input
    private void validateInput(String input) {
        if (input.isEmpty()) {
            tvError.setVisibility(View.GONE);
            btnConvert.setEnabled(false);
        } else if (!isNumeric(input)) {
            tvError.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Please enter only numbers", Toast.LENGTH_SHORT).show();
            btnConvert.setEnabled(false);
        } else {
            tvError.setVisibility(View.GONE);
            btnConvert.setEnabled(true);
        }
    }

    // Check if the input is numeric
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
