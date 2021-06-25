package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Objects;

import soup.neumorphism.NeumorphButton;


public class MainActivity extends AppCompatActivity {

    NeumorphButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    TextView Expression;
    TextView Result;
    NeumorphButton btnSub, btnAdd, btnMul, btnDiv, btnEqual, btnClr, btnAC, btnDot, btnLeft, btnRight;
    String input;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        Expression = findViewById(R.id.CalculationView);
        Result = findViewById(R.id.ResultView);
        btnSub = findViewById(R.id.btnSub);
        btnAdd = findViewById(R.id.btnAdd);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        btnClr = findViewById(R.id.btnClr);
        btnDot = findViewById(R.id.btnDot);
        btnAC = findViewById(R.id.btnAC);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        btnEqual = findViewById(R.id.btnEqual);

        btn0.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "0");
        });
        btn1.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "1");
        });
        btn2.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "2");
        });
        btn3.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "3");
        });
        btn4.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "4");
        });
        btn5.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "5");
        });
        btn6.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "6");
        });
        btn7.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "7");
        });
        btn8.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "8");
        });
        btn9.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "9");
        });
        btnRight.setOnClickListener(v -> {
            input =  Expression.getText().toString();
            Expression.setText(input + ")");
        });
        btnLeft.setOnClickListener(v -> {
            input =  Expression.getText().toString();
            Expression.setText(input + "(");
        });
        btnClr.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(removeLastCharacter(input));
        });
        btnAC.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText("");
            Result.setText("");
        });
        btnAdd.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "+");
        });
        btnSub.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "-");
        });
        btnMul.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "×");
        });
        btnDiv.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + "÷");
        });
        btnDot.setOnClickListener(v -> {
            input = Expression.getText().toString();
            Expression.setText(input + ".");

        });
        btnEqual.setOnClickListener(v -> {
            String output = Objects.requireNonNull(evaluate(Expression.getText().toString())).toString();
            Result.setText(output);

        });







    }

    private static String removeLastCharacter(String str) {
        String result = null;
        if ((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 1);
        }
        return result;
    }


    private static String addSpaces(String exp){
        exp = exp.replaceAll("(?<=[0-9()])[\\÷]", " / ");
        exp = exp.replaceAll("(?<=[0-9()])[\\×]", " * ");
        exp = exp.replaceAll("(?<=[0-9()])[+]", " + ");
        exp = exp.replaceAll("(?<=[0-9()])[-]", " - ");

        exp = exp.replaceAll(" {2,}", " ");

        return exp;
    }
    public static Double evaluate(String expr){

        DecimalFormat df = new DecimalFormat("#.####");

        String expression = addSpaces(expr);

        try {


            int indexClose = expression.indexOf(")");
            int indexOpen;
            if (indexClose != -1) {
                String substring = expression.substring(0, indexClose);
                indexOpen = substring.lastIndexOf("(");
                substring = substring.substring(indexOpen + 1).trim();
                if(indexOpen != -1) {
                    Double result = evaluate(substring);
                    expression = expression.substring(0, indexOpen).trim() + " " + result + " " + expression.substring(indexClose + 1).trim();
                    return evaluate(expression.trim());
                }
            }

            String operation;
            if(expression.contains(" / ")){
                operation = "/";
            }else if(expression.contains(" * ")){
                operation = "*";
            } else if(expression.contains(" + ")){
                operation = "+";
            } else if(expression.contains(" - ")){ //Avoid negative numbers
                operation = "-";
            } else{
                return Double.parseDouble(expression);
            }

            int index = expression.indexOf(operation);
            if(index != -1){
                indexOpen = expression.lastIndexOf(" ", index - 2);
                indexOpen = (indexOpen == -1)?0:indexOpen;
                indexClose = expression.indexOf(" ", index + 2);
                indexClose = (indexClose == -1)?expression.length():indexClose;
                Double lhs = Double.parseDouble(expression.substring(indexOpen, index));
                Double rhs = Double.parseDouble(expression.substring(index + 2, indexClose));
                Double result = null;
                switch (operation){
                    case "/":
                        //Prevent divide by 0 exception.
                        if(rhs == 0){
                            return null;
                        }
                        result = lhs / rhs;
                        break;
                    case "*":
                        result = lhs * rhs;
                        break;
                    case "-":
                        result = lhs - rhs;
                        break;
                    case "+":
                        result = lhs + rhs;
                        break;
                    default:
                        break;
                }
                if(indexClose == expression.length()){
                    expression = expression.substring(0, indexOpen) + " " + result + " " + expression.substring(indexClose);
                }else{
                    expression = expression.substring(0, indexOpen) + " " + result + " " + expression.substring(indexClose + 1);
                }
                return Double.valueOf(df.format(evaluate(expression.trim())));
            }
        }catch(Exception exp){
            exp.printStackTrace();
        }
        return 0.0;
    }
}