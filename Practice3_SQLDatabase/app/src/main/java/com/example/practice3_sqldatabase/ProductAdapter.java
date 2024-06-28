package com.example.practice3_sqldatabase;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> products;

    private Set<Product> selected_products;

    private Boolean isSelectable = true;

    public ProductAdapter(List<Product> products) {
        this.products = products;
        this.selected_products = new HashSet<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int position1 = position;
        Product product = products.get(position1);
        holder.name_TextView.setText(product.getProduct_name());
        System.out.println("The product cost is:" + String.valueOf(product.getProduct_cost()));
        holder.cost_TextView.setText(String.valueOf(product.getProduct_cost()));
        holder.description_TextView.setText(product.getProduct_description());
        holder.productImage.setImageResource(product.getImageResourceID());
        System.out.println("IMAGE: " + product.getImageResourceID());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Product item = products.get(position1);
                if(isSelectable){
                    if(selected_products.contains(item)){
                        selected_products.remove(item);
                        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    }
                    else{
                        selected_products.add(item);
                        holder.itemView.setBackgroundColor(Color.LTGRAY);
                    }
                    //printSelected();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        System.out.println("THE NUMBER OF PRODUCTS IS " + String.valueOf(this.products.size()));
        return this.products.size();
    }

    public void setSelectable(Boolean selectable){
        isSelectable = selectable;
    }

    public int getSelectedCount(){
        System.out.println("THE NUMBER OF SELECTED PRODUCTS IS " + String.valueOf(this.selected_products.size()));
        return this.selected_products.size();
    }

    public Set<Product> getSelectedProducts(){
        System.out.println("PRODUCTS: " + this.selected_products);
        return this.selected_products;
    }

    public void clearProducts(){
        products.clear();
        selected_products.clear();
        notifyDataSetChanged();
    }

    public void printSelected(){
        System.out.println("SELECTED PRODUCTS:");
        for(Product pr: this.selected_products){
            System.out.println(pr.getProduct_name());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name_TextView;
        public TextView cost_TextView;
        public TextView description_TextView;
        public ImageView productImage;

        public ViewHolder(View itemView) {
            super(itemView);
            name_TextView = itemView.findViewById(R.id.name_TextView);
            cost_TextView = itemView.findViewById(R.id.cost_TextView);
            description_TextView = itemView.findViewById(R.id.description_TextView);
            productImage = itemView.findViewById(R.id.imageView);
        }
    }
}