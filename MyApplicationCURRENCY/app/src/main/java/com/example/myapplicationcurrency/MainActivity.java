package com.example.myapplicationcurrency;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerFromCurrency;
    Spinner spinnerToCurrency;
    EditText etAmount;
    Button btnConvert;
    TextView tvResult;

    String[] currencies = {"USD", "EUR", "GBP", "JPY", "CAD"}; // Add more currencies if needed

    double[][] conversionRates = {
            {1.0, 0.93, 0.73, 110.43, 1.21},
            {1.18, 1.0, 0.86, 132.92, 1.45},
            {1.37, 1.16, 1.0, 154.15, 1.69},
            {0.0091, 0.0075, 0.0065, 1.0, 0.011},
            {0.83, 0.69, 0.58, 89.29, 1.0}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFromCurrency = findViewById(R.id.spinner_from_currency);
        spinnerToCurrency = findViewById(R.id.spinner_to_currency);
        etAmount = findViewById(R.id.et_amount);
        btnConvert = findViewById(R.id.btn_convert);
        tvResult = findViewById(R.id.tv_result);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerFromCurrency = findViewById(R.id.spinner_from_currency);
        Spinner spinnerToCurrency = findViewById(R.id.spinner_to_currency);

        spinnerFromCurrency.setAdapter(currencyAdapter);
        spinnerToCurrency.setAdapter(currencyAdapter);

        spinnerFromCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateConversionResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerToCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateConversionResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateConversionResult();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConversionResult();
            }
        });
    }

    private void updateConversionResult() {
        String fromCurrency = spinnerFromCurrency.getSelectedItem().toString();
        String toCurrency = spinnerToCurrency.getSelectedItem().toString();
        String amountString = etAmount.getText().toString();

        if (amountString.isEmpty()) {
            tvResult.setText("");
            return;
        }

        double amount = Double.parseDouble(amountString);

        int fromCurrencyIndex = getIndexForCurrency(fromCurrency);
        int toCurrencyIndex = getIndexForCurrency(toCurrency);

        double conversionRate = conversionRates[fromCurrencyIndex][toCurrencyIndex];
        double convertedAmount = amount * conversionRate;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String result = decimalFormat.format(convertedAmount);

        tvResult.setText(result);
    }

    private int getIndexForCurrency(String currency) {
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].equals(currency)) {
                return i;
            }
        }
        return -1;
    }
}