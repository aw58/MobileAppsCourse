package com.example.practice3_sqldatabase;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

    //Local variables to keep track of products in the list
    private List<Product> products;

    //local variable which keeps track of which products have been selected
    private Set<Product> selected_products;

    //determines whether the items can be selected
    private Boolean isSelectable = true;

    //default constructor
    public ProductAdapter(List<Product> products) {
        this.products = products;
        //no selected products yet. Initialize.
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
        int position1 = position;  //got an error saying that "position" could change and that I should use a local variable instead
        //create a local copy of the product at position1
        Product product = products.get(position1);

        //Set the holder's (item's) xml elements to the product-unique details
        //the ViewHolder class is defined below.
        holder.name_TextView.setText(product.getProduct_name());
        //System.out.println("The product cost is:" + String.valueOf(product.getProduct_cost()));
        holder.cost_TextView.setText(String.valueOf(product.getProduct_cost()));
        holder.description_TextView.setText(product.getProduct_description());
        holder.productImage.setImageResource(product.getImageResourceID());
        //System.out.println("IMAGE: " + product.getImageResourceID());

        //onclick behavior for each item.
        //Select the item if not selected, otherwise, remove from selected.
        //change color of the background to show selected status
        //must call notifyDataSetChanged to show changes
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Product item = products.get(position1);

                if(isSelectable){ //if the items are not selectable, skip this functionality
                    if(selected_products.contains(item)){
                        selected_products.remove(item);
                        //had issues with getting Color resources to work, so used hardcoded values instead
                        holder.itemView.setBackgroundColor(Color.rgb(0, 0, 204));
                    }
                    else{
                        selected_products.add(item);
                        holder.itemView.setBackgroundColor(Color.rgb(26, 175, 255));
                    }
                    //printSelected();
                }
            }
        });
    }


    //Getters and Setters

    @Override
    public int getItemCount() {
        //System.out.println("THE NUMBER OF PRODUCTS IS " + String.valueOf(this.products.size()));
        return this.products.size();
    }

    public void setSelectable(Boolean selectable){
        isSelectable = selectable;
    }

    public int getSelectedCount(){
        //System.out.println("THE NUMBER OF SELECTED PRODUCTS IS " + String.valueOf(this.selected_products.size()));
        return this.selected_products.size();
    }

    public Set<Product> getSelectedProducts(){
        //System.out.println("PRODUCTS: " + this.selected_products);
        return this.selected_products;
    }

    public void clearProducts(){
        products.clear();
        selected_products.clear();
        notifyDataSetChanged();
    }

    // for debugging
    public void printSelected(){
        System.out.println("SELECTED PRODUCTS:");
        for(Product pr: this.selected_products){
            System.out.println(pr.getProduct_name());
        }
    }

    //Custom ViewHolder class which allows the xml elements in a recyclerView item to be bound together into one object
    //made for ease of reference
    public class ViewHolder extends RecyclerView.ViewHolder {
        //the local variables for each of the xml elements in an item
        public TextView name_TextView;
        public TextView cost_TextView;
        public TextView description_TextView;
        public ImageView productImage;

        public ViewHolder(View itemView) {
            super(itemView);
            //assign actual views to the local variables
            name_TextView = itemView.findViewById(R.id.name_TextView);
            cost_TextView = itemView.findViewById(R.id.cost_TextView);
            description_TextView = itemView.findViewById(R.id.description_TextView);
            productImage = itemView.findViewById(R.id.imageView);
        }
    }
}