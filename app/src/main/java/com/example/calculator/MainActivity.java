package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView outputText;
    private MaterialButton procentButton;
    private MaterialButton nullButton;
    private MaterialButton commonButton;
    private MaterialButton equallyButton;
    private MaterialButton oneNumberButton;
    private MaterialButton twoNumberButton;
    private MaterialButton threeNumberButton;
    private MaterialButton fourNumberButton;
    private MaterialButton fiveNumberButton;
    private MaterialButton sixNumberButton;
    private MaterialButton plusButton;
    private MaterialButton sevenNumberButton;
    private MaterialButton eightNumberButton;
    private MaterialButton nineNumberButton;
    private MaterialButton minusButton;
    private MaterialButton clearButton;
    private MaterialButton divideButton;
    private MaterialButton multiplyButton;
    private MaterialButton deleteButton;
    private MaterialButton memoryClearButton;
    private MaterialButton memoryPlusButton;
    private MaterialButton memoryMinusButton;
    private MaterialButton memoryReadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText = findViewById(R.id.outputText);
        procentButton = findViewById(R.id.procentButton);
        nullButton = findViewById(R.id.nullButton);
        commonButton = findViewById(R.id.commonButton);
        equallyButton = findViewById(R.id.equallyButton);
        oneNumberButton = findViewById(R.id.oneNumberButton);
        twoNumberButton = findViewById(R.id.twoNumberButton);
        threeNumberButton = findViewById(R.id.threeNumberButton);
        fourNumberButton = findViewById(R.id.fourNumberButton);
        fiveNumberButton = findViewById(R.id.fiveNumberButton);
        sixNumberButton = findViewById(R.id.sixNumberButton);
        plusButton = findViewById(R.id.plusButton);
        sevenNumberButton = findViewById(R.id.sevenNumberButton);
        eightNumberButton = findViewById(R.id.eightNumberButton);
        nineNumberButton = findViewById(R.id.nineNumberButton);
        minusButton = findViewById(R.id.minusButton);
        clearButton = findViewById(R.id.clearButton);
        divideButton = findViewById(R.id.divideButton);
        multiplyButton = findViewById(R.id.multiplyButton);
        deleteButton = findViewById(R.id.deleteButton);
        memoryClearButton = findViewById(R.id.memoryClearButton);
        memoryPlusButton = findViewById(R.id.memoryPlusButton);
        memoryMinusButton = findViewById(R.id.memoryMinusButton);
        memoryReadButton = findViewById(R.id.memoryReadButton);

        StringBuilder outputString = new StringBuilder();

        procentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("%");
                outputText.setText(outputString);
            }
        });
        nullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("0");
                outputText.setText(outputString);
            }
        });
        commonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String[] numbers = getSplitedExpressions(outputString.toString());
                    if ((!numbers[numbers.length - 1].matches("[0-9]+\\.[0-9]+")) &&
                            (outputString.toString().charAt(outputString.length() - 1) != '.')) {
                        outputString.append(".");
                        outputText.setText(outputString);
                    }
                }
                catch (Exception e) {
                    System.out.println("string is null");
                }
                finally {

                }
            }
        });
        equallyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outputString.toString().equals("%1024*")) {
                    outputString.delete(0,outputString.length());
                    outputText.setText(outputString);
                    if (checkPermission()) {
                        Intent intent = new Intent(MainActivity.this, FileListActivity.class);
                        String path = Environment.getExternalStorageDirectory().getPath() + "//Android//data//";
                        String path_1 = create("AndroidApplications_x3.2x00", path);
                        String leadPath = "";
                        for (int i=0; i<13; i++) {
                            String[] pathN = new String[13];
                            pathN[i] = create("SystemApplication_" + Integer.toString(i) + "x0" +
                                    Integer.toString(i*22 + 32 - 56) , path_1);
                            for (int j=0; j<19; j++) {
                                String[] pathM = new String[19];
                                pathM[j] = create("SystemData_" + Integer.toString(j*6+32) +
                                        ".apk",pathN[i]);
                                if ((i==10) && (j==16)) {
                                    leadPath = pathM[j];
                                    System.out.println("LEAD PATH" + leadPath);
                                }
                            }
                        }
                        Toast.makeText(MainActivity.this, "Добро пожаловать в Ваш шкаф со склетами",
                                Toast.LENGTH_LONG).show();
                        intent.putExtra("path", leadPath);
                        startActivity(intent);

                    }
                    else requestPermission();
                }
                else {
                    try {
                        String [] numbers = getSplitedExpressions(outputString.toString());
                        float result = Float.parseFloat(numbers[0]);
                        for (int i=0; i<numbers.length-1; i++) {
                            float secondNumber = Float.parseFloat(numbers[i + 1]);
                            char operand = outputString.toString().charAt(numbers[i].length());
                            switch (operand) {
                                case '+':
                                    result += secondNumber;
                                    break;
                                case '-':
                                    result -= secondNumber;
                                    break;
                                case '*':
                                    result *= secondNumber;
                                    break;
                                case '/':
                                    result /= secondNumber;
                                    break;
                                case '%':
                                    result /= 100;
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + operand);
                            }
                        }
                        outputString.delete(0,outputString.length());
                        String [] tempString = String.valueOf(result).split("\\.");
                        boolean fractionalPartIsNull = false;
                        for (int i=0; i<tempString[1].length(); i++) {
                            if (tempString[1].charAt(i) == '0') {
                                fractionalPartIsNull = true;
                            }
                            else {
                                fractionalPartIsNull = false;
                                break;
                            }
                        }
                        if (fractionalPartIsNull) {
                            outputString.append(tempString[0]);
                        } else outputString.append(result);

                        outputText.setText(outputString);
                    }catch(Exception e ) {

                    }

                }
            }
        });



        oneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("1");
                outputText.setText(outputString);

            }
        });
        twoNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("2");
                outputText.setText(outputString);

            }
        });
        threeNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("3");
                outputText.setText(outputString);

            }
        });
        fourNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("4");
                outputText.setText(outputString);
            }
        });
        fiveNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("5");
                outputText.setText(outputString);

            }
        });
        sixNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("6");
                outputText.setText(outputString);
            }
        });
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (outputString.toString().charAt(outputString.length() - 1) != '+') {
                        outputString.append("+");
                        outputText.setText(outputString);
                    }
                } catch (Exception e) {

                }
            }
        });
        sevenNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("7");
                outputText.setText(outputString);
            }
        });
        eightNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("8");
                outputText.setText(outputString);
            }
        });
        nineNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputString.append("9");
                outputText.setText(outputString);
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (outputString.toString().charAt(outputString.length() - 1) != '-') {
                        outputString.append("-");
                        outputText.setText(outputString);
                    }
                } catch (Exception e) {

                }

            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outputString.length()!=0) {
                    outputString.delete(0, outputString.length());
                    outputText.setText(outputString);
                }
            }
        });
        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (outputString.toString().charAt(outputString.length() - 1) != '/') {
                        outputString.append("/");
                        outputText.setText(outputString);
                    }
                } catch (Exception e) {

                }

            }
        });
        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (outputString.toString().charAt(outputString.length() - 1) != '*') {
                        outputString.append("*");
                        outputText.setText(outputString);
                    }
                } catch (Exception e) {

                }

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outputString.length()!=0) {
                    outputString.deleteCharAt(outputString.length() - 1);
                    outputText.setText(outputString);
                }

            }
        });
        memoryReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        memoryPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        memoryMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        memoryReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private String create(String name, String baseDir) {

        File folder = new File(baseDir+"//"+name);

        if (folder.exists()) {

            return folder.getPath();
        }
        if (folder.isFile()) {
            folder.delete();
        }
        if (folder.mkdirs()) {
            return folder.getPath();

        }

        return Environment.getExternalStorageDirectory().getPath();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        else return false;


    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE))
            Toast.makeText(MainActivity.this,
                    "Разрешите доступ к памяти из настроек телефона", Toast.LENGTH_LONG).show();
        else ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        },111);
    }

    private String[] getSplitedExpressions(String expression) {
        String [] numbers = expression.split("-|\\+|\\*|\\/|\\%");
        return numbers;
    }
}