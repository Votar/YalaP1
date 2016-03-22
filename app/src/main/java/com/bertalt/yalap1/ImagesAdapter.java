package com.bertalt.yalap1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesAdapter extends
        RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    // Store a member variable
    private List<String> mLinksOnImages;
    private Context mContext;

    public ImagesAdapter(List<String> links) {
        mLinksOnImages = links;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView nextImageView; //[Comment] Wrong visibility modifier

        public ViewHolder(View itemView) {

            super(itemView);
            nextImageView = (ImageView) itemView.findViewById(R.id.ivNextImage);
        }
    }


    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         mContext = parent.getContext(); //[Comment] Formatting
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_images, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ImagesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        String link = mLinksOnImages.get(position);


        // Set item views based on the data model
        ImageView imageView = viewHolder.nextImageView;

        int resizeImage = mContext.getResources().getInteger(R.integer.resize_image);

        Picasso.with(mContext)
                .load(link)
                .resize(resizeImage,resizeImage)
                .placeholder(R.drawable.ic_crop_original_black_36dp)
                .error(R.drawable.ic_error_black_48dp) // can also be a drawable
                .into(imageView);


    }

    // Return the total count of items
    @Override
    public int getItemCount() {
       return mLinksOnImages.size();
    }
}