package com.appdevlab.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.itis.libs.parserng.android.expressParser.MathExpression;


public class MainActivity extends AppCompatActivity {

    final static String SHARED_PREF = "com.appdevlab.calculator.SHARED_PREF";
    SharedPreferences sharedPreferences;
    final static String TAG = "MY_LOG_TAG";
    final static ArrayList<String> errors = new ArrayList<>();
    private TextView primary;
    private TextView secondary;
    ArrayList<TextView> digits, operations, constants, others, collection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        getSupportActionBar().setElevation(0);

        primary = (TextView) findViewById(R.id.primary);
        secondary = (TextView) findViewById(R.id.secondary);

        digits = new ArrayList<TextView>();
        digits.add((TextView) findViewById(R.id.number_0));
        digits.add((TextView) findViewById(R.id.number_1));
        digits.add((TextView) findViewById(R.id.number_2));
        digits.add((TextView) findViewById(R.id.number_3));
        digits.add((TextView) findViewById(R.id.number_4));
        digits.add((TextView) findViewById(R.id.number_5));
        digits.add((TextView) findViewById(R.id.number_6));
        digits.add((TextView) findViewById(R.id.number_7));
        digits.add((TextView) findViewById(R.id.number_8));
        digits.add((TextView) findViewById(R.id.number_9));

        constants = new ArrayList<TextView>();
        constants.add((TextView) findViewById(R.id.constant_pi));
        constants.add((TextView) findViewById(R.id.constant_e));

        others = new ArrayList<TextView>();
        others.add((TextView) findViewById(R.id.decimal_point));
        others.add((TextView) findViewById(R.id.equal_to));
        others.add((TextView) findViewById(R.id.open_parenthesis));
        others.add((TextView) findViewById(R.id.close_parenthesis));

        operations = new ArrayList<TextView>();
        operations.add((TextView) findViewById(R.id.operation_add));
        operations.add((TextView) findViewById(R.id.operation_subtract));
        operations.add((TextView) findViewById(R.id.operation_multiply));
        operations.add((TextView) findViewById(R.id.operation_divide));
        operations.add((TextView) findViewById(R.id.operation_sin));
        operations.add((TextView) findViewById(R.id.operation_cos));
        operations.add((TextView) findViewById(R.id.operation_tan));
        operations.add((TextView) findViewById(R.id.operation_ln));
        operations.add((TextView) findViewById(R.id.operation_log));
        operations.add((TextView) findViewById(R.id.operation_factorial));
        operations.add((TextView) findViewById(R.id.operation_exponent));
        operations.add((TextView) findViewById(R.id.operation_square_root));

        collection = new ArrayList<TextView>();
        collection.addAll(digits);
        collection.addAll(operations);
        collection.addAll(constants);
        collection.addAll(others);

        errors.add("SYNTAX ERROR");
        errors.add("Infinity");
        errors.add("A SYNTAX ERROR OCCURED");

        primary.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    MathExpression expr = new MathExpression(s.toString());
                    String answer = expr.solve();
                    if (!s.toString().equals(""))
                        secondary.setText(answer);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        for (int i = 0; i < collection.size(); i++) {
            final String id = (String) collection.get(i).getText();
            collection.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String displayPrimary = primary.getText().toString();
                    String displaySecondary = secondary.getText().toString();

                    if (id.equals("=")) {

                        String historyPrimary, historySecondary;

                        historyPrimary = sharedPreferences.getString("primary","");
                        historySecondary = sharedPreferences.getString("secondary","");

                        historyPrimary = historyPrimary + ";" + displayPrimary;

                        if (errors.contains(displaySecondary)) {
                            primary.setText("");
                            historySecondary = historySecondary + ";" + "";
                        }
                        else {
                            primary.setText(displaySecondary);
                            historySecondary = historySecondary + ";" + displaySecondary;
                        }
                        secondary.setText("");

                        editor.putString("primary",historyPrimary);
                        editor.putString("secondary",historySecondary);
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Saved to history",Toast.LENGTH_SHORT).show();

                    } else
                        primary.setText(displayPrimary.concat(id));
                }
            });
        }

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!primary.getText().toString().equals("")) {
                    String displayPrimary = primary.getText().toString();
                    if (displayPrimary.length() == 1)
                        secondary.setText("");
                    primary.setText(displayPrimary.substring(0, displayPrimary.length() - 1));
                }
            }
        });

        findViewById(R.id.delete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                primary.setText("");
                secondary.setText("");
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                Toast.makeText(getApplicationContext(),"Showing calculator history", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.clear:
                sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(getApplicationContext(),"Cleared History", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }
}
