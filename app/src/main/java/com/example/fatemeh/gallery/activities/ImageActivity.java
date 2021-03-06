package com.example.fatemeh.gallery.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.fatemeh.gallery.R;
import com.example.fatemeh.gallery.helpers.GalleryApplication;
import com.example.fatemeh.gallery.models.Image;
import com.example.fatemeh.gallery.views.ImageDetailInfoLayout;


public class ImageActivity extends ActionBarActivity {

    public static final String IMAGE_LINK_KEY = "imageLink";
    public static final String Image_COLOR_KEY = "imageColor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        setToolbar();
        enableSpinner();

        int defaultColor = getResources().getColor(R.color.blue_gray_700);

        Intent intent = getIntent();
        Image image = intent.getParcelableExtra(IMAGE_LINK_KEY);
        int color = intent.getIntExtra(Image_COLOR_KEY, defaultColor);
        showImageDetails(image, color);
    }

    private void enableSpinner() {
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
    }

    private void disableSpinner() {
        findViewById(R.id.loading).setVisibility(View.GONE);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showImageDetails(Image image, int color) {
        final ImageView imageImageView = (ImageView)
                findViewById(R.id.image);

        TextView titleTextView = (TextView)
                findViewById(R.id.title);

        titleTextView.setBackgroundColor(color);

        TextView descriptionTextView = (TextView)
                findViewById(R.id.description);

        ImageDetailInfoLayout scoreLayout = (ImageDetailInfoLayout)
                findViewById(R.id.score);
        ImageDetailInfoLayout upVotesLayout = (ImageDetailInfoLayout)
                findViewById(R.id.ups);
        ImageDetailInfoLayout downVotesLayout = (ImageDetailInfoLayout)
                findViewById(R.id.downs);

        ImageLoader imageLoader = GalleryApplication.
                getInstance().getImageLoader();

        imageLoader.get(image.getLink(),
                new ImageLoader.ImageListener() {

                    public void onErrorResponse(VolleyError error) {
                        disableSpinner();
                        imageImageView.setImageResource(R.drawable.placeholder);
                    }

                    public void onResponse(ImageLoader.ImageContainer
                                                   response, boolean arg1) {
                        if (response.getBitmap() != null) {
                            disableSpinner();
                            imageImageView.setImageBitmap(response.getBitmap());
                        }
                    }
                });

        if (image.getTitle() != null) {
            titleTextView.setText(image.getTitle());
        } else {
            titleTextView.setText(R.string.image_no_title);
        }

        if (image.getDescription() != null) {
            descriptionTextView.setText(image.getDescription());
        } else {
            descriptionTextView.setText(R.string.image_no_Description);
        }

        scoreLayout.setContentText(image.getScore() + "");
        upVotesLayout.setContentText(image.getUpVotes() + "");
        downVotesLayout.setContentText(image.getDownVotes() + "");
    }
}
