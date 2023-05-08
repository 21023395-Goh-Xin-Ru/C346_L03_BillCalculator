package sg.edu.rp.c346.id21023395.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText amount;
    EditText paxEdit;
    EditText discountEdit;
    RadioGroup rgPayment;
    ToggleButton svs;
    ToggleButton gst;
    Button splitBtn;
    Button resetBtn;
    TextView totalBillText;
    TextView totalBill;
    TextView eachPaysText;
    TextView eachPays;
    LinearLayout output;
    TextView message;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amountEditInput);
        paxEdit = findViewById(R.id.paxEditInput);
        discountEdit = findViewById(R.id.discountEditInput);
        rgPayment = findViewById(R.id.rgPayment);
        svs = findViewById(R.id.toggleButtonSVS);
        gst = findViewById(R.id.toggleButtonGST);
        splitBtn = findViewById(R.id.buttonSplit);
        resetBtn = findViewById(R.id.buttonReset);
        totalBillText = findViewById(R.id.totalBill);
        totalBill = findViewById(R.id.totalBillAmt);
        eachPaysText = findViewById(R.id.eachPays);
        eachPays = findViewById(R.id.eachPaysAmt);
        output = findViewById(R.id.output);
        message = findViewById(R.id.message);

        splitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check for null and invalid values and print a message if exist
                String outputMessage = "";
                int checkedRadioId = rgPayment.getCheckedRadioButtonId();
                if (amount.getText().toString().trim().isEmpty()){
                    outputMessage += "Amount cannot be null!\n";
                } else if (Double.parseDouble(amount.getText().toString()) <= 0){
                    outputMessage += "Amount cannot be 0 or less!\n";
                } if (paxEdit.getText().toString().trim().isEmpty()) {
                    outputMessage += "Pax cannot be null!\n";
                } else if (Integer.parseInt(paxEdit.getText().toString()) <= 0){
                    outputMessage += "Pax cannot be 0 or less!\n";
                } if (checkedRadioId != R.id.radioButtonCash &&
                        checkedRadioId != R.id.radioButtonPayNow) {
                    outputMessage += "Payment mode cannot be null!\n";
                }
                message.setText(outputMessage);
                output.setBackgroundColor(0x5EBD96B9);

                // Ensure there is no more error before proceeding
                // the calculated amt will then be shown
                if (message.getText().toString().isEmpty()){
                    double totalAmt = Double.parseDouble(amount.getText().toString());
                    int pax = Integer.parseInt(paxEdit.getText().toString());
                    double newAmt;
                    // Toggle Button - SVS/GST
                    if (svs.isChecked() && !gst.isChecked()){
                        newAmt = totalAmt * 1.10;
                    } else if (!svs.isChecked() && gst.isChecked()){
                        newAmt = totalAmt * 1.07;
                    } else if (svs.isChecked() && gst.isChecked()){
                        newAmt = totalAmt * 1.17;
                    } else{
                        newAmt = totalAmt;
                    }

                    // Discount
                    double discount = 0;
                    if (!discountEdit.getText().toString().isEmpty()){
                        newAmt = newAmt * ((100 - discount)/100);
                    } else{
                        newAmt = newAmt;
                    }

                    // Check payment mode
                    String total = String.format("$%.2f", newAmt);
                    String eachPay = "";
                    if (checkedRadioId == R.id.radioButtonCash){
                        eachPay = String.format("$%.2f in cash", (newAmt/pax));
                    } else if (checkedRadioId == R.id.radioButtonPayNow){
                        eachPay = String.format("$%.2f via PayNow to 912345678", (newAmt/pax));
                    }
                    totalBillText.setText("TOTAL BILL:");
                    totalBill.setText(total);
                    eachPaysText.setText("EACH PAYS:");
                    eachPays.setText(eachPay);
                    output.setBackgroundColor(0xC894D0EC);
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                paxEdit.setText("");
                totalBillText.setText("");
                totalBill.setText("");
                discountEdit.setText("");
                eachPaysText.setText("");
                eachPays.setText("");
                output.setBackgroundColor(0x5EBD96B9);
                svs.setChecked(false);
                gst.setChecked(false);
                rgPayment.check(R.id.radioButtonCash);
            }
        });
    }
}