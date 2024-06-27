package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner categorySpinner;
    private Spinner fromUnit;
    private Spinner toUnit;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        categorySpinner = findViewById(R.id.categorySpinner);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        resultView = findViewById(R.id.resultView);
        Button convertButton = findViewById(R.id.convertButton);


        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.unit_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);


        ArrayAdapter<CharSequence> defaultAdapter = ArrayAdapter.createFromResource(this,
                R.array.default_array, android.R.layout.simple_spinner_item);
        defaultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromUnit.setAdapter(defaultAdapter);
        toUnit.setAdapter(defaultAdapter);


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        populateUnits(R.array.unit_categories);
                        break;
                    case 1: // Length
                        populateUnits(R.array.length_units);
                        break;
                    case 2: // Weight
                        populateUnits(R.array.weight_units);
                        break;
                    case 3: // Volume
                        populateUnits(R.array.volume_units);
                        break;
                    case 4: // Temperature
                        populateUnits(R.array.temperature_units);
                        break;
                    case 5: // Time
                        populateUnits(R.array.time_units);
                        break;
                    case 6: // Number Systems
                        populateUnits(R.array.number_system_units);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                String from = fromUnit.getSelectedItem().toString();
                String to = toUnit.getSelectedItem().toString();
                String category = categorySpinner.getSelectedItem().toString();

                String result = "";
                switch (category) {
                    case "Length":
                        double lengthValue = Double.parseDouble(inputStr);
                        result = String.valueOf(UnitConverter.convertLength(lengthValue, from, to));
                        break;
                    case "Weight":
                        double weightValue = Double.parseDouble(inputStr);
                        result = String.valueOf(UnitConverter.convertWeight(weightValue, from, to));
                        break;
                    case "Volume":
                        double volumeValue = Double.parseDouble(inputStr);
                        result = String.valueOf(UnitConverter.convertVolume(volumeValue, from, to));
                        break;
                    case "Temperature":
                        double tempValue = Double.parseDouble(inputStr);
                        result = String.valueOf(UnitConverter.convertTemperature(tempValue, from, to));
                        break;
                    case "Time":
                        double timeValue = Double.parseDouble(inputStr);
                        result = String.valueOf(UnitConverter.convertTime(timeValue, from, to));
                        break;
                    case "Number Systems":
                        if (from.equals("Decimal")) {
                            int decimalValue = Integer.parseInt(inputStr);
                            switch (to) {
                                case "Binary":
                                    result = UnitConverter.convertDecimalToBinary(decimalValue);
                                    break;
                                case "Hexadecimal":
                                    result = UnitConverter.convertDecimalToHexadecimal(decimalValue);
                                    break;
                                case "Octal":
                                    result = UnitConverter.convertDecimalToOctal(decimalValue);
                                    break;
                            }
                        } else if (to.equals("Decimal")) {
                            switch (from) {
                                case "Binary":
                                    result = UnitConverter.convertBinaryToDecimal(inputStr);
                                    break;
                                case "Hexadecimal":
                                    result = UnitConverter.convertHexadecimalToDecimal(inputStr);
                                    break;
                                case "Octal":
                                    result = UnitConverter.convertOctalToDecimal(inputStr);
                                    break;
                            }
                        }
                        break;
                }

                resultView.setText(result);
            }
        });
    }

    private void populateUnits(int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);
    }
}
