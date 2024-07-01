package psu.sweng888.productemailer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Main_ProductAdapter extends RecyclerView.Adapter<Main_ProductAdapter.ViewHolder> {
    private List<Product> products;
    public Main_ProductAdapter(List<Product> products) { this.products = products; }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_product_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.nameTextView.setText(product.getName());
        holder.descrTextView.setText(product.getDescription());
        holder.sellerTextView.setText(product.getSeller());
        String priceStr = "$" + product.getPrice_Dollars();
        holder.priceDollarsTextView.setText(priceStr);
        holder.priceCentsTextView.setText(product.getPrice_Cents());
        holder.picImageView.setImageResource(product.getResourceID());
        holder.selectedCheckbox.setSelected(product.isSelected());
    }

    @Override
    public int getItemCount() { return products.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, descrTextView, sellerTextView, priceDollarsTextView, priceCentsTextView;
        public ImageView picImageView;
        public CheckBox selectedCheckbox;

        public ViewHolder(View itemView) {
            super(itemView);

            // Set all the text/image views
            nameTextView = itemView.findViewById(R.id.prod_name);
            descrTextView = itemView.findViewById(R.id.prod_descr);
            sellerTextView = itemView.findViewById(R.id.prod_seller);
            priceDollarsTextView = itemView.findViewById(R.id.prod_price_dollars);
            priceCentsTextView = itemView.findViewById(R.id.prod_price_cents);
            picImageView = itemView.findViewById(R.id.prod_pic);
            selectedCheckbox = itemView.findViewById(R.id.checkbox_selected);

            // Click listener for MainActivity RecyclerView
            selectedCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Product prod = products.get(pos);
                    // Toggle the selected state of the product
                    prod.setSelected(!prod.isSelected());
                }
            });
        }
    }
}
