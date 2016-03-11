package ca.bradleyhaveman.calculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView _screen;  //The results of the calculations
    private Button _buttonClick;
    private boolean _isTyping; //Boolean used to see if it the first or second number
    private boolean _decimal; //Boolean used to only allow one decimal per user
    private float _firstNum; //The first number
    private float _secondNum; //The second number
    private int _decimalCount = 0;
    private int _operationCount = 0;
    private String _operation = "";
    private String _operation2 = "";
    private float results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this._screen = (TextView) findViewById(R.id.screenText); //Get the screen
    }

    /**
     * Method used to find out which button was pressed by checking the buttons id in a switch
     * @param view
     */
    public void numberOnClick(View view) {

        Button number = (Button)view;
        //while use is still entering numbers, concat to existing number
        if (_isTyping) {
            _screen.setText(_screen.getText().toString() + number.getText().toString());
        } else {
            _screen.setText(number.getText().toString());
            _decimalCount = 0;
        }
        _isTyping = true;
    }

    /**
     * Method call when an operation is clicked
     * @param view
     */
    public void operatorOnClick(View view) {
        //If it's not the first number
        if(_operationCount > 0){
            //Get the number from the screen and store it
            _secondNum = Float.parseFloat(_screen.getText().toString());
            //Call the calc method and pas the 2 number.
            float result = calc(_firstNum, _secondNum);

            //Get the next operator
            _operation = getOperation(view.getId());

            //Store the result of the 2 number in the first number
            _firstNum =  result;

            //Show the results on the screen
            _screen.setText(String.valueOf(result));
            _isTyping = false;

            if(_decimalCount > 1)
                _decimalCount=1;
        }

        //If it is the first number
        else{
            _isTyping = false;

            //Store the first number
            _firstNum = Float.parseFloat(_screen.getText().toString());
            _operation = getOperation(view.getId());
            _decimal = true;
            if(_decimalCount > 1)
                _decimalCount=1;

            _operationCount++;
        }
    }

    /**
     * Method used to only alloow for one decimal per number
     * @param view
     */
    public void decimalOnClick(View view) {
        if (_decimal == false && _decimalCount == 0){
            _screen.append(".");
            _decimalCount++;
        }
        else if (_decimal == true && _decimalCount == 1){
            _screen.append(".");
            _decimalCount++;
        }
    }

    /**
     * Backpace method
     * @param view
     */
    public void delOnClick(View view) {
        String screen = _screen.getText().toString();

        if(screen.length() > 0){
            screen = screen.substring(0, screen.length() - 1); //Get the screen text and take away the last character

            _screen.setText(screen);
        }
    }

    /**
     * Reset the calculator
     * @param view
     */
    public void clearOnClick(View view) {
        _screen.setText("0");
        _isTyping = false;
        _firstNum = 0;
        _secondNum = 0;
        _decimal = false;
        _operationCount = 0;
        _decimalCount = 0;

    }


    //Calculate the results based on the numbers passed in
    public float calc(float firstNum, float secondNum){
        float result = 0;
        //Addition
        if (_operation == "+"){
            result = _firstNum + _secondNum;
        }
        //Minus
        else if (_operation == "-"){
            result = _firstNum - _secondNum;
        }
        //Multiply
        else if (_operation == "*"){
            result = _firstNum * _secondNum;
        }
        //Divide
        else if (_operation == "/"){
            result = _firstNum / _secondNum;
        }
        //Percent
        else if (_operation == "%") {
            result = (_firstNum * _secondNum) / 100;
        }

        return result;//Return the result
    }

    /**
     * Called when the user presses the = button
     * @param view
     */
    public void equalsOnClick(View view) {
        _secondNum = Float.parseFloat(_screen.getText().toString());
        _decimalCount = 1;

        _screen.setText(String.valueOf(calc(_firstNum, _secondNum)));
        _isTyping = false;
        _operationCount = 0;
    }

    /**
     * A method that return the operation
     * @param id
     * @return
     */
    public String getOperation(int id){
        String op = "";
        switch(id)
        {
            case R.id.btnDivide:
                op = "/";
                break;

            case R.id.btnMultiply:
                op = "*";
                break;

            case R.id.btnMinus:
                op = "-";
                break;

            case R.id.btnPlus:
                op = "+";
                break;

            case R.id.btnPercent:
                op = "%";
        }
        return op;
    }
}
