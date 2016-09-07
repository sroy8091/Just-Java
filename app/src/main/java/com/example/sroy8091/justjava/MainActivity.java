package com.example.sroy8091.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox creamBox = (CheckBox) findViewById(R.id.cream_box);
        boolean hasWhippedCream = creamBox.isChecked();

        CheckBox cholocateBox = (CheckBox) findViewById(R.id.chocolate_box);
        boolean hasChocolate = cholocateBox.isChecked();

        EditText nameView = (EditText) findViewById(R.id.custom_name);

        int price = calculatePrice(quantity, hasWhippedCream, hasChocolate);

        String name = String.valueOf(nameView.getText());
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:order@coffee.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        Toast.makeText(this, getString(R.string.thank_you), Toast.LENGTH_LONG).show();
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText(number + "");
    }
    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate) {
        int base_price = 5;
        if (hasWhippedCream){
            base_price +=1;
        }
        if(hasChocolate) {
            base_price += 2;
        }
        return quantity*base_price;
    }


    public void increament(View view) {

        if(quantity==100){
            Toast.makeText(this, "You can't order more than this", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decreament(View view) {
        if (quantity == 0) {
            Toast.makeText(this, "You can't order less than this", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    public void displayMessage(String message){
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage = priceMessage + "\n"+ getString(R.string.cream) +" or not? " + hasWhippedCream;
        priceMessage = priceMessage + "\n"+ getString(R.string.chocolate) + "or not? " + hasChocolate;
        priceMessage = priceMessage + "\n"+getString(R.string.quantity) +": " + quantity;
        priceMessage = priceMessage + "\n"+getString(R.string.total)+": $" + price;
        return priceMessage;
    }
}