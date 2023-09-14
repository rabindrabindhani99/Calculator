package com.grabs4buisness.calculator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultTv, queryTv, historyTv;
    MaterialButton clearBtn, percentageBtn, divideBtn, sevenBtn, eightBtn, nineBtn, multiplyBtn, charClearBtn, threeDotButton;
    MaterialButton fourBtn, fiveBtn, sixBtn, subtractBtn, oneBtn, twoBtn, threeBtn, powerBtn, addBtn, zeroBtn, dotBtn, resBtn;
    //Evaluating the result by sending the data to MathExpression Evaluator class
    MathExpressionEvaluator mathExpressionEvaluator = new MathExpressionEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextViews Initialization
        resultTv = findViewById(R.id.result_tv);
        queryTv = findViewById(R.id.query_tv);
        historyTv = findViewById(R.id.history_tv);

        //Material Button Initialization
        AssignId(clearBtn, R.id.clear_btn);
        AssignId(percentageBtn, R.id.percentage_btn);
        AssignId(charClearBtn, R.id.charClear_btn);
        AssignId(divideBtn, R.id.divide_btn);
        AssignId(sevenBtn, R.id.seven_btn);
        AssignId(eightBtn, R.id.eight_btn);
        AssignId(nineBtn, R.id.nine_btn);
        AssignId(multiplyBtn, R.id.multiply_btn);
        AssignId(fourBtn, R.id.four_btn);
        AssignId(fiveBtn, R.id.five_btn);
        AssignId(sixBtn, R.id.six_btn);
        AssignId(subtractBtn, R.id.subtract_btn);
        AssignId(oneBtn, R.id.one_btn);
        AssignId(twoBtn, R.id.two_btn);
        AssignId(threeBtn, R.id.three_btn);
        AssignId(powerBtn, R.id.to_the_power);
        AssignId(addBtn, R.id.add_btn);
        AssignId(zeroBtn, R.id.zero_btn);
        AssignId(dotBtn, R.id.dot_btn);
        AssignId(resBtn, R.id.res_btn);
        AssignId(threeDotButton, R.id.threeDot_btn);
    }

    void AssignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = queryTv.getText().toString();

        if (buttonText.equals("AC")) {
            queryTv.setText("");
            resultTv.setText("0");
            historyTv.setText("");
            return;
        } else if (buttonText.equals("=")) {
            String question = queryTv.getText().toString();
            String result = resultTv.getText().toString();
            String historyItem = question + result;
            // Add the calculation to the SharedPreferences history

            if (question.equals("") || result.equals("Can't divide by zero")) {
                return;
            } else {
                String show = question + "\n=";
                historyTv.setText(show);
                addToDatabaseHistory(historyItem);
            }
            dataToCalculate = result.substring(1);
        } else if (buttonText.equals("C")) {
            if (dataToCalculate.equals("")) {
                return;
            } else {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else if (buttonText.equals("T")) {
            setupPopupMenu(view);
        } else if (buttonText.equals("+")) {
            if (dataToCalculate.equals("")) {
                return;
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        } else if (buttonText.equals("*")) {
            if (dataToCalculate.equals("")) {
                return;
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        } else if (buttonText.equals("/")) {
            if (dataToCalculate.equals("")) {
                return;
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        } else if (buttonText.equals("%")) {
            if (dataToCalculate.equals("")) {
                return;
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        } else if (buttonText.equals("^")) {
            if (dataToCalculate.equals("")) {
                return;
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        } else {
            if (dataToCalculate.equals("0")) {
                dataToCalculate = "";
                return;
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        }

        queryTv.setText(dataToCalculate);
        if (dataToCalculate.equals("")) {
            dataToCalculate = "0";
        } else if (dataToCalculate.contains("/0")) {
            resultTv.setText(R.string.cantDivide_byZero);
        } else {
            String finalResult = getResult(dataToCalculate);


            //While data output
            if (!finalResult.equals("Error")) {
                String finalAnswer;
                if (finalResult.equals("")) {
                    finalAnswer = "0";
                } else if (finalResult.equals("0")) {
                    finalAnswer = "0";
                } else {
                    finalAnswer = "=" + finalResult;
                }
                resultTv.setText(finalAnswer);
            }
        }
    }

    private void setupPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_history) {
                    // Start the HistoryActivity when "History" is clicked
                    Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_about) {
                    // Create an instance of the AboutUsDialogFragment
                    AboutUsDialogFragment aboutUsDialogFragment = new AboutUsDialogFragment();
                    aboutUsDialogFragment.show(getSupportFragmentManager(), "aboutUsDialog");
                    return true;
                }
                return false;
            }
        });
        Log.d("PopupDebug", "Showing popup menu");
        popupMenu.show();
    }

    String getResult(String data) {
        String res = String.valueOf(mathExpressionEvaluator.evaluateExpression(data));
        res = removeTrailingZeros(res);
        Log.d("YourTag", "The value of myVariable is: " + res);
        return res;


    }

    //Remove the trailing zeroes after the decimal point. Like: if the result is 5.63400000000000 it will make it 5.634
    String removeTrailingZeros(String input) {
        if (input.contains(".")) {
            // Remove trailing zeros after the decimal point
            return input.replaceAll("\\.?0*$", "");
        } else {
            return input;
        }
    }

    //Method to add the calculations into the history section
    private void addToDatabaseHistory(String historyItem) {
        HistoryDatabaseHelper dbHelper = new HistoryDatabaseHelper(this);
        dbHelper.insertHistoryItem(historyItem);
    }


}