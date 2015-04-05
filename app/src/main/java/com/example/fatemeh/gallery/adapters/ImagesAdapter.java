package com.example.fatemeh.gallery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.fatemeh.gallery.R;
import com.example.fatemeh.gallery.helpers.GalleryApplication;
import com.example.fatemeh.gallery.models.Image;

import java.util.List;

/**
 * Created by fatemeh on 01/04/15.
 */

public class ImagesAdapter extends
        RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>{

    private onItemInteractionListener listener;

    private Context context;
    private List<Image> images;
    private ImageLoader imageLoader;

    public ImagesAdapter(Context context, List<Image> images) {
        this.context = context;
        this.images = images;

        imageLoader = GalleryApplication.
                getInstance().getImageLoader();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                getItemLayoutID(), parent, false);

        return new ImageViewHolder(itemView);
    }

    protected int getItemLayoutID() {
        return R.layout.item_image_list_layout2;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final Image image = images.get(position);

        holder.descriptionTextView.setText(image.getTitle());
        holder.networkImageView.setImageUrl(image.getLink(), imageLoader);

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        protected RelativeLayout itemLayout;
        protected NetworkImageView networkImageView;
        protected TextView descriptionTextView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            itemLayout = (RelativeLayout) itemView.findViewById(R.id.item);
            networkImageView = (NetworkImageView) itemView.findViewById(R.id.image);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
        }
    }

    public void setOnListener(onItemInteractionListener listener) {
        this.listener = listener;
    }

    public interface onItemInteractionListener {
        void onItemClick(Image image);
    }
}