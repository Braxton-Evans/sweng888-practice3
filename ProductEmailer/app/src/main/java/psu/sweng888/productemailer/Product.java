package psu.sweng888.productemailer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {
    private final String name;
    private final String description;
    private final String seller;
    private final double price;
    private final int resourceID;

    private boolean isSelected = false;

    public Product(String name, String description, String seller, double price, int resourceID) {
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.price = price;
        this.resourceID = resourceID;
    }

    protected Product(Parcel in) {
        name = in.readString();
        description = in.readString();
        seller = in.readString();
        price = in.readDouble();
        resourceID = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        // Not needed for this example
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(seller);
        parcel.writeDouble(price);
        parcel.writeInt(resourceID);
    }

    // Getters and setters for the remaining fields

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSeller() { return seller; }
    public double getPrice() { return price; }
    public String getPrice_Dollars() { return String.valueOf((int)(price+0.001)); }
    public String getPrice_Cents() { return String.valueOf((int)(((price+0.001) - (int)(price+0.001)) * 100)); }
    public int getResourceID() { return resourceID; }
    public boolean isSelected() { return isSelected; }
    public boolean setSelected(boolean val) { return isSelected = val; }
}
