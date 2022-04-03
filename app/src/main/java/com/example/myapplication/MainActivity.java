package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayAdapter arrayAdapter;
    EditText editText;
    int currentUnit = 0;

    TextView tvValue1;
    TextView tvUnit1;
    TextView tvValue2;
    TextView tvUnit2;
    TextView tvValue3;
    TextView tvUnit3;

    ImageView ivMetre;
    ImageView ivCelsius;
    ImageView ivKg;
    private static final DecimalFormat df = new DecimalFormat("#,###.##");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init data for spinner
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.units));
        arrayAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        // set callback for edittext
        editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvValue1.setText("");
                tvUnit1.setText("");
                tvValue2.setText("");
                tvUnit2.setText("");
                tvValue3.setText("");
                tvUnit3.setText("");
                if (!s.toString().isEmpty()) {

                    if (Integer.parseInt(s.toString()) == 0) {
                        editText.setText("");
                        showAlertWrongFormat();
                    } else {
                        switch (currentUnit) {
                            case 0:
                                tvUnit1.setText(getString(R.string.centimetre));
                                tvUnit2.setText(getString(R.string.foot));
                                tvUnit3.setText(getString(R.string.inch));
                                tvValue1.setText(String.format("%,d",Integer.parseInt(editText.getText().toString()) * 100));
                                tvValue2.setText(df.format(Integer.parseInt(editText.getText().toString()) * 3.28084));
                                tvValue3.setText(df.format(Integer.parseInt(editText.getText().toString()) * 39.3701));
                                break;
                            case 1:
                                tvUnit1.setText(getString(R.string.fahrenheit));
                                tvUnit2.setText(getString(R.string.kelvin));
                                tvValue1.setText(df.format(Float.parseFloat(editText.getText().toString()) * 9 / 5 + 32));
                                tvValue2.setText(df.format(Integer.parseInt(editText.getText().toString()) + 273.15));
                                break;
                            case 2:
                                tvUnit1.setText(getString(R.string.gram));
                                tvUnit2.setText(getString(R.string.ounce));
                                tvUnit3.setText(getString(R.string.pound));
                                tvValue1.setText(String.format("%,d",Integer.parseInt(editText.getText().toString()) * 1000));
                                tvValue2.setText(df.format(Integer.parseInt(editText.getText().toString()) * 35.274));
                                tvValue3.setText(df.format(Integer.parseInt(editText.getText().toString()) *  2.20462));

                                break;
                        }
                    }
                }
            }
        });

        tvValue1 = findViewById(R.id.tvValue1);
        tvUnit1 = findViewById(R.id.tvUnit1);
        tvValue2 = findViewById(R.id.tvValue2);
        tvUnit2 = findViewById(R.id.tvUnit2);
        tvValue3 = findViewById(R.id.tvValue3);
        tvUnit3 = findViewById(R.id.tvUnit3);

        ivMetre = findViewById(R.id.ivMetre);
        ivCelsius = findViewById(R.id.ivCelsius);
        ivKg = findViewById(R.id.ivKg);

        ivMetre.setOnClickListener(v -> {
            if(currentUnit!=0){
                showAlert();
            }
        });

        ivCelsius.setOnClickListener(v -> {
            if(currentUnit!=1){
                showAlert();
            }
        });
        ivKg.setOnClickListener(v -> {
            if(currentUnit!=2){
                showAlert();
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentUnit = position;
        editText.setText("");
        tvValue1.setText("");
        tvUnit1.setText("");
        tvValue2.setText("");
        tvUnit2.setText("");
        tvValue3.setText("");
        tvUnit3.setText("");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Please select the correct conversion icon");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private void showAlertWrongFormat(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Please enter a number which is greater than zero");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}