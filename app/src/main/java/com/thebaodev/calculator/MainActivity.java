package com.thebaodev.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edt_input;
    TextView txt_current_session;
    ListView listView;
    ArrayList<String> listResults = new ArrayList<>();
    ArrayAdapter<String> listAdapter ;



    private double currentNumber;
    private double lastNumber=0;
    private String lastOperator="";
    boolean isNextNumber=false;
    boolean isOperating = false;
    private String fullOperationHistory="";
    public static final String ADD = "+";
    public static final String SUB = "-";
    public static final String MULTIPLY = "x";
    public static final String DIVIDE = "/";
    public static final String CLEAR = "C" ;
    public static final String CLEARENTRY = "CE";
    public static final String EQUALSIGN = "=";

    public static final String PERCENTAGE = "%" ;
    public static final String SQRT = "√";
    public static final String SQUARED = "x²";
    public static final String INVERT = "1/x";
    public static final String TOGGLESIGN = "+/-";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_main);

        listAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listResults);
        listView =  findViewById(R.id.historyListView);
        listView.setAdapter(listAdapter);
        edt_input = findViewById(R.id.edt_input);
        txt_current_session = findViewById(R.id.txt_current_session);
    }


    private String getDisplayText(double a, double b, String operation, double result) {
        return a + operation + b + " = " + result;
    }

    public void inputNumber(View v) {
        isOperating = false;
        if(isNextNumber) {
            edt_input.setText("");
            isNextNumber = false;
        }
        switch (v.getId()) {
            case R.id.btn_0:
                edt_input.append("0");
                break;
            case R.id.btn_1:
                edt_input.append("1");
                break;
            case R.id.btn_2:
                edt_input.append("2");
                break;
            case R.id.btn_3:
                edt_input.append("3");
                break;
            case R.id.btn_4:
                edt_input.append("4");
                break;
            case R.id.btn_5:
                edt_input.append("5");
                break;
            case R.id.btn_6:
                edt_input.append("6");
                break;
            case R.id.btn_7:
                edt_input.append("7");
                break;
            case R.id.btn_8:
                edt_input.append("8");
                break;
            case R.id.btn_9:
                edt_input.append("9");
                break;
            default: break;
        }

    }
    public void doOperation(View v) {

        performOperation(((Button) v).getText().toString());
        edt_input.setText(Double.toString(currentNumber));

    }

    private void doLastOperation() {
        switch (lastOperator) {
            case ADD:
                currentNumber = lastNumber + currentNumber;

                break;
            case SUB:
                currentNumber = lastNumber - currentNumber;

                break;
            case MULTIPLY:
                currentNumber = lastNumber * currentNumber;

                break;
            case DIVIDE:
                if (currentNumber != 0) {
                    currentNumber = lastNumber / currentNumber;
                }
                break;
            default: break;
        }

    }
    private void clear() {
        edt_input.setText("0");
        fullOperationHistory = "";
        currentNumber = 0;
        lastOperator = "";
        lastNumber = 0;
    }
    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
    private void performOperation(String operator) {
        currentNumber = Double.parseDouble(edt_input.getText().toString());
        isNextNumber = true;

        switch (operator) {
            case CLEAR:
                clear();
                break;
            case CLEARENTRY:
                currentNumber = 0;
                break;
            case SQRT:
                currentNumber = Math.sqrt(currentNumber);

                break;
            case SQUARED:
                currentNumber = currentNumber * currentNumber;

                break;
            case PERCENTAGE:
                currentNumber = currentNumber / 100;
                break;
            case INVERT:
                if (currentNumber != 0) {
                    currentNumber = 1 / currentNumber;
                }
                break;
            case TOGGLESIGN:
                currentNumber = -currentNumber;
                break;
            case EQUALSIGN:
                double tempCurrentNumber = currentNumber;

                if(!isOperating){
                    doLastOperation();
                    fullOperationHistory += tempCurrentNumber + "=" + currentNumber;

                }else {
                    fullOperationHistory = removeLastChar(fullOperationHistory);
                    fullOperationHistory +=  "=" + currentNumber;

                }

                listResults.add(fullOperationHistory);
                listAdapter.notifyDataSetChanged();
                clear();
                break;
            default:

                fullOperationHistory+= currentNumber + operator;

                doLastOperation();
                isOperating = true;
                lastOperator = operator;
                lastNumber = currentNumber;

                break;
        }
    }


    public void resetHistory(View v){
        listResults.clear();
        listAdapter.notifyDataSetChanged();


    }
}
