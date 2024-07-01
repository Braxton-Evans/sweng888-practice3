package psu.sweng888.productemailer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                KEY_PICTURE + " INTEGER" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Not needed for this example
    }

    public void populateProductsDatabase() {
        SQLiteDatabase db = getWritableDatabase();

        // Product Definitions
        Random random = new Random();
        String[] productNames = {"Yomega Dash Yo-Yo", "Wireless Headphones", "Smartwatch", "Portable Bluetooth Speaker", "Tablet", "E-reader", "Fitness Tracker", "Action Camera"};
        String[] descriptions = {"Long-spinning YoYo perfect for non-binding tricks!", "Great sound quality and long battery life.", "Stay connected on the go.", "Perfect for parties and outdoor gatherings.", "Enjoy movies and games on a larger screen.", "Read your favorite books anywhere.", "Track your steps and heart rate.", "Capture your adventures in stunning detail."};
        String[] sellerNames = {"Yomega", "Tech Gadgets Inc.", "Smart Devices Co.", "Audio World", "Mobile Solutions", "Bookworm Emporium", "FitLife Gear", "Adventure Capture"};
        Integer[] picResIDs = {R.drawable.yomega_dash, R.drawable.headphones, R.drawable.smartwatch, R.drawable.bluetooth_speaker, R.drawable.tablet, R.drawable.e_reader, R.drawable.fitness_tracker, R.drawable.camera};

        for (int i = 0; i < productNames.length; ++i) {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, productNames[i]);
            values.put(KEY_DESCRIPTION, descriptions[i]);
            values.put(KEY_SELLER, sellerNames[i]);
            values.put(KEY_PRICE, 10 + random.nextDouble() * 125); // Price between $10 and $250
            values.put(KEY_PICTURE, picResIDs[i]);
            db.insert(TABLE_PRODUCTS, null, values);
        }

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
