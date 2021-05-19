package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    private Context context;
    private ArrayList<Product> arrayproduct;

    public ProductAdapter(Context context, ArrayList<Product> arrayproduct) {
        this.context = context;
        this.arrayproduct = arrayproduct;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recyclerview,parent,false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product = arrayproduct.get(position);
        holder.name_product.setText(product.getName_product());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price_product.setText("Giá: "+ decimalFormat.format(product.getPrice_product())+"đ");
        Picasso.with(context).load(product.getImg_product()).placeholder(R.drawable.logo2).into(holder.img_product);
    }

    @Override
    public int getItemCount() {
        return arrayproduct.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView img_product;
        public TextView name_product, price_product;

        public ItemHolder(View itemView){
            super(itemView);
            img_product = (ImageView) itemView.findViewById(R.id.imageViewProduct);
            name_product = (TextView) itemView.findViewById(R.id.texviewNameProduct);
            price_product = (TextView) itemView.findViewById(R.id.priceProduct);
        }
    }

}
