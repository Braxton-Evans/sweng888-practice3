package psu.sweng888.productemailer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Product Database
    ProductDatabaseHelper databaseHelper;
    ArrayList<Product> products;

    // Main screen RecyclerView components
    RecyclerView recyclerView;
    Main_ProductAdapter mainProductAdapter;
    LinearLayoutManager layoutManager;

    // Share button
    FloatingActionButton shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the database & read in products
        databaseHelper = new ProductDatabaseHelper(this);
        boolean reloadDB = false; // Set this to true to cause the DB to be re-populated
        if (reloadDB)
            this.deleteDatabase("products_database");
        products = databaseHelper.getAllProducts();
        if (products.isEmpty()) {
            databaseHelper.populateProductsDatabase();
            products = databaseHelper.getAllProducts();
        }

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.mainRecyclerView);
        mainProductAdapter = new Main_ProductAdapter(products);
        recyclerView.setAdapter(mainProductAdapter);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
            new MaterialDividerItemDecoration( recyclerView.getContext(), layoutManager.getOrientation())
        );

        // Set up the share button to get the selected products and pass them to SecondActivity
        shareBtn = findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(view -> {
            // Collect the selected products
            ArrayList<Product> selectedProducts = new ArrayList<>();
            for (Product prod : products) {
                if (prod.isSelected())
                    selectedProducts.add(prod);
            }

            // Package up the selected products
            Parcelable[] packedProducts = new Parcelable[selectedProducts.size()];
            int i = 0;
            for (Parcelable prod : selectedProducts)
                packedProducts[i++] = prod;

            // Create and start the intent to move to SecondActivity
            Intent moveSelectedToSecondActivity = new Intent(MainActivity.this, SecondActivity.class);
            moveSelectedToSecondActivity.putExtra("packedProducts", packedProducts);
            startActivity(moveSelectedToSecondActivity);
        });
    }
}