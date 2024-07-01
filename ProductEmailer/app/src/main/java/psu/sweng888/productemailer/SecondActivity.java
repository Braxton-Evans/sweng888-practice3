package psu.sweng888.productemailer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ArrayList<Product> products;

    // Main screen RecyclerView components
    RecyclerView recyclerView;
    Share_ProductAdapter productAdapter;
    LinearLayoutManager layoutManager;

    // Email button
    FloatingActionButton emailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_2nd);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Read in products from intent extra
        Parcelable[] packedProducts = getIntent().getParcelableArrayExtra("packedProducts");
        products = new ArrayList<>();
        for (Parcelable prod : packedProducts) {
            products.add((Product)prod);
        }

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.shareRecyclerView);
        productAdapter = new Share_ProductAdapter(products);
        recyclerView.setAdapter(productAdapter);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
            new MaterialDividerItemDecoration( recyclerView.getContext(), layoutManager.getOrientation())
        );

        // Set up the email button to email the list of products
        emailBtn = findViewById(R.id.shareBtn);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build the body of the email:
                StringBuilder sb = new StringBuilder("Hello Professor,\r\n\r\n");
                sb.append("The following products are being shared with you:\r\n\r\n");
                int i = 0;
                for (Product p : products){
                    i++;
                    sb.append("Product #");
                    sb.append(i);
                    sb.append("\r\n");
                    sb.append(p.getName());
                    sb.append("\r\n$");
                    sb.append(p.getPrice_Dollars());
                    sb.append(".");
                    sb.append(p.getPrice_Cents());
                    sb.append("\r\n");
                    sb.append(p.getDescription());
                    sb.append("\r\n\r\n");
                }
                sb.append("Thanks & Cheers!");

                // Email the selected products
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setDataAndType(Uri.parse("mailto:"), "text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"sweng888mobileapps@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sweng888 Shared Products");
                emailIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());

                ActivityResultLauncher<Intent> launcher = new ActivityResultLauncher<Intent>() {
                    @Override
                    public void launch(Intent intent, @Nullable ActivityOptionsCompat activityOptionsCompat) {
                        Intent shareIntent = Intent.createChooser(intent, null);
                        startActivity(shareIntent);
                    }

                    @Override
                    public void unregister() {

                    }

                    @NonNull
                    @Override
                    public ActivityResultContract<Intent, ?> getContract() {
                        return null;
                    }
                };
                launcher.launch(emailIntent);
                Toast.makeText(view.getContext(), "Email sent!", Toast.LENGTH_LONG).show();
            }
        });
    }
}