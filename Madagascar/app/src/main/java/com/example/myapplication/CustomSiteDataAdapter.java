package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import java.io.IOException;
import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.util.List;
import com.squareup.picasso.Picasso;
class CustomSiteDataAdapter extends ArrayAdapter<Site> {
    private int resourceLayout;
    private Context mContext;

    public CustomSiteDataAdapter(Context context, int resource, List<Site> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resourceLayout, null);
        }

        Site siteData = getItem(position);
        if (siteData != null) {
            TextView siteNameTextView = v.findViewById(R.id.site_name_textView);
            TextView siteDescriptionTextView = v.findViewById(R.id.site_description_textView);
            ImageView siteImageView = v.findViewById(R.id.image_site);
            if (siteNameTextView != null) {
                siteNameTextView.setText(siteData.getNom());
            }

            if (siteDescriptionTextView != null) {
                siteDescriptionTextView.setText(siteData.getDescription());
            }

            /*if (siteImageView != null) {
                // Load the image using Picasso and the image URL from SiteData
                String imageSourceUrl = siteData.getImagePosteur();
                Picasso.get().load(imageSourceUrl).into(siteImageView);
            } */
            if (siteImageView != null) {
                String imageSourceId = siteData.getImagePosteur();
                try {
                    InputStream inputStream = mContext.getAssets().open("images/" + imageSourceId);
                    Drawable drawable = Drawable.createFromStream(inputStream, null);
                    siteImageView.setImageDrawable(drawable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        return v;
    }
}
