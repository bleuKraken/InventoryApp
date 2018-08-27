package com.example.android.inventoryapp.data;
import android.provider.BaseColumns;
/**
 * API Contract.
 * These are the items from database.
 */
public final class InventoryContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private InventoryContract() {}

    /**
     * Inner class that defines constant values for the database table.
     * Each entry in the table represents a single item.
     */
    public static final class InventoryEntry implements BaseColumns {
        //Database name
        public final static String TABLE_NAME = "inventoryTable";

        /**Unique ID number for item.
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**Name of item.
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME ="name";

        /**Supplier or company/brand of the item.
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_SUPPLIER = "product_supplier";

        /** Quantity of product.
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_QUANTITY= "quantity";


        /**Price of product.
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_PRICE= "price";

        /**Phone number of supplier.
         * Type: INTEGER
         */
        public final static String COLUMN_SUPPLIER_PHONE_NUMBER= "phone";


        /**Condition of the product.
         * Type: INTEGER
         *
         * The only possible values are {@link #CONDITION_OLD}, {@link #CONDITION_NEW},
         * or {@link #CONDITION_USED}.
         */
        public final static String COLUMN_PRODUCT_CONDITION = "condition";
        /**
         * Possible values for the condition of item.
         */
        public static final int CONDITION_NEW = 0;
        public static final int CONDITION_USED = 1;
        public static final int CONDITION_OLD = 2;

    }
}