package com.appdevlab.calculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.itis.libs.parserng.android.expressParser.MathExpression;


public class MainActivity extends AppCompatActivity {
    private TextView primary;
    private TextView secondary;
    final static String TAG = "MY_LOG_TAG";
    final static ArrayList<String> errors = new ArrayList<>();

    ArrayList<TextView> digits, operations, constants, others, everything;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Light_Green);
        setContentView(R.layout.activity_main);
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

        everything = new ArrayList<TextView>();
        everything.addAll(digits);
        everything.addAll(operations);
        everything.addAll(constants);
        everything.addAll(others);

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

        for (int i = 0; i < everything.size(); i++) {
            final String id = (String) everything.get(i).getText();
            everything.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String display = primary.getText().toString();
                    if (id.equals("=")) {
                        if (errors.contains(secondary.getText().toString()))
                            primary.setText("");
                        else
                            primary.setText(secondary.getText().toString());
                        secondary.setText("");
                    } else
                        primary.setText(display.concat(id));
                }
            });
        }

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!primary.getText().toString().equals("")) {
                    String display = primary.getText().toString();
                    if (display.length() == 1)
                        secondary.setText("");
                    primary.setText(display.substring(0, display.length() - 1));
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
}
