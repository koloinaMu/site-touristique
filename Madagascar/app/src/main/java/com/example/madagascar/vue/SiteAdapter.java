package com.example.madagascar.vue;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.madagascar.R;
import com.example.madagascar.model.Site;
import com.example.madagascar.vue.placeholder.PlaceholderContent.PlaceholderItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> {

    Context context;
    List<Site> siteData;
    Dialog detailSite;
    View v;

    public SiteAdapter(Context context, List<Site> siteDate) {
        this.context = context;
        this.siteData = siteDate;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        v=LayoutInflater.from(this.context).inflate(R.layout.fragment_site,parent,false);
        //SiteViewHolder vHolder=new SiteViewHolder(v,context, siteData.get());
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /*holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);00
        holder.mContentView.setText(mValues.get(position).content);*/
        holder.nomView.setText(siteData.get(position).getNom());
        holder.descriView.setText(siteData.get(position).getDescription());
        String imageSourceId=siteData.get(position).getImagePosteur();
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("images/" + imageSourceId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable drawable = Drawable.createFromStream(inputStream, null);
        holder.imgPosteurView.setImageDrawable(drawable);
        SiteViewHolder vHolder=new SiteViewHolder(v,context, siteData.get(position));
    }

    @Override
    public int getItemCount() {
        return siteData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearSite;
        private ImageView imgPosteurView;
        private TextView nomView;
        private TextView descriView;
        public PlaceholderItem mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            linearSite=itemView.findViewById(R.id.site_item);
            nomView=itemView.findViewById(R.id.site_name_textView);
            descriView=itemView.findViewById(R.id.site_description_textView);
            imgPosteurView=itemView.findViewById(R.id.image_site);
        }

    }
}