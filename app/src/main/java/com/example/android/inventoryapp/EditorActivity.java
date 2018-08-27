package com.example.android.inventoryapp;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.inventoryapp.data.InventoryContract;
import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.android.inventoryapp.data.InventoryDbHelper;


/**
 * Allows user to create a new item or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity{

    /** EditText field to enter the item's name */
    private EditText mProductNameEditText;

    /** EditText field to enter the item's breed */
    private EditText mSupplierNameEditText;

    /** EditText field to enter the item's weight */
    private EditText mPriceEditText;

    /** EditText field of quantity.*/
    private EditText mQuantityEditText;

    /** EditText field to enter the item's gender */
    private Spinner mItemConditionSpinner;

    /** EditText field to enter the item's weight */
    private EditText mSupplierPhoneNumber;

    /**
     * Condition. The possible valid values are in the InventoryContract.java file:
     */
    private int mItemCondition = InventoryContract.InventoryEntry.CONDITION_OLD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Below are used to get input from from user.
        mProductNameEditText = (EditText) findViewById(R.id.edit_product_name); // was mNameEditText
        mSupplierNameEditText = (EditText) findViewById(R.id.edit_product_supplier); // was mBreedEditText
        mQuantityEditText = (EditText) findViewById(R.id.edit_quantity);
        mPriceEditText = (EditText) findViewById(R.id.edit_price); // was mWeightEditText
        mItemConditionSpinner = (Spinner) findViewById(R.id.spinner_gender); // was mGenderSpinner
        mSupplierPhoneNumber = (EditText) findViewById(R.id.edit_phone_number); // was mWeightEditText

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the condition of the item.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_condition_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        mItemConditionSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mItemConditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.condition_used))) {
                        mItemCondition = InventoryEntry.CONDITION_NEW; // was Male. Now "New"
                    } else if (selection.equals(getString(R.string.condition_old))) {
                        mItemCondition = InventoryEntry.CONDITION_USED; // was Female. Now "Used"
                    } else {
                        mItemCondition = InventoryEntry.CONDITION_OLD; // was  Unknown. Now "Old"
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mItemCondition = InventoryEntry.CONDITION_NEW;
            }
        });
    }

    /**
     * Get user input from editor and save new item into database.
     */
    private void insertProduct() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mProductNameEditText.getText().toString().trim();
        String supplierString = mSupplierNameEditText.getText().toString().trim();

        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String phoneNumberString = mSupplierPhoneNumber.getText().toString().trim();
        //Quantity of item
        int quantity = Integer.parseInt(quantityString);
        //Price of the item
        int price = Integer.parseInt(priceString);
        //Price of the item
        int phoneNumber = Integer.parseInt(phoneNumberString);

        // Create database helper
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a ContentValues object where column names are the keys,
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER, supplierString);
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(InventoryEntry.COLUMN_PRODUCT_CONDITION, mItemCondition);
        values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER, phoneNumber);

        // Insert a new row for  item the database, returning the ID of that new row.
        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving item", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Item saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save item to database
                insertProduct();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}