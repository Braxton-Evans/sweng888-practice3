package psu.sweng888.productemailer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "products";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_SELLER = "seller";
    private static final String KEY_PRICE = "price";
    private static final String KEY_PICTURE = "picture";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_DESCRIPTION + " TEXT, " +
                KEY_SELLER + " TEXT, " +
                KEY_PRICE + " REAL, " +
                KEY_PICTURE + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Not needed for this example
    }

    public void populateProductsDatabase() {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values;

        values = new ContentValues();
        values.put(KEY_NAME, "Product 1");
        values.put(KEY_DESCRIPTION, "Description 1");
        values.put(KEY_SELLER, "Seller 1");
        values.put(KEY_PRICE, 11.11);
        values.put(KEY_PICTURE, R.drawable.yomega_dash);
        db.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_NAME, "Product 2");
        values.put(KEY_DESCRIPTION, "Description 2");
        values.put(KEY_SELLER, "Seller 2");
        values.put(KEY_PRICE, 22.22);
        values.put(KEY_PICTURE, R.drawable.yomega_dash);
        db.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_NAME, "Product 3");
        values.put(KEY_DESCRIPTION, "Description 3");
        values.put(KEY_SELLER, "Seller 3");
        values.put(KEY_PRICE, 33.33);
        values.put(KEY_PICTURE, R.drawable.yomega_dash);
        db.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_NAME, "Product 4");
        values.put(KEY_DESCRIPTION, "Description 4");
        values.put(KEY_SELLER, "Seller 4");
        values.put(KEY_PRICE, 44.44);
        values.put(KEY_PICTURE, R.drawable.yomega_dash);
        db.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_NAME, "Product 5");
        values.put(KEY_DESCRIPTION, "Description 5");
        values.put(KEY_SELLER, "Seller 5");
        values.put(KEY_PRICE, 55.55);
        values.put(KEY_PICTURE, R.drawable.yomega_dash);
        db.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_NAME, "Product 6");
        values.put(KEY_DESCRIPTION, "Description 6");
        values.put(KEY_SELLER, "Seller 6");
        values.put(KEY_PRICE, 66.66);
        values.put(KEY_PICTURE, R.drawable.yomega_dash);
        db.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_NAME, "Product 7");
        values.put(KEY_DESCRIPTION, "Description 7");
        values.put(KEY_SELLER, "Seller 7");
        values.put(KEY_PRICE, 77.77);
        values.put(KEY_PICTURE, R.drawable.yomega_dash);
        db.insert(TABLE_PRODUCTS, null, values);

        db.close();
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getString(1), // Name
                        cursor.getString(2), // Description
                        cursor.getString(3), // Seller
                        cursor.getDouble(4), // Price
                        cursor.getInt(5)  // Picture
                );
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }
}
