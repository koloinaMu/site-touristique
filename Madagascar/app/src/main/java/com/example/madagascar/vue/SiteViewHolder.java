package com.example.madagascar.vue;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.madagascar.R;
import com.example.madagascar.model.Site;
import com.example.madagascar.vue.placeholder.PlaceholderContent;

import java.io.IOException;
import java.io.InputStream;

public class SiteViewHolder extends RecyclerView.ViewHolder{

    private LinearLayout linearSite;
    private ImageView imgPosteurView;
    private TextView nomView;
    private TextView descriView;
    public PlaceholderContent.PlaceholderItem mItem;
    private Site site;
    Context context;

    public SiteViewHolder(View itemView,Context context,Site site) {
        super(itemView);
        this.context=context;
        this.site=site;
        linearSite=itemView.findViewById(R.id.site_item);
        nomView=itemView.findViewById(R.id.site_name_textView);
        descriView=itemView.findViewById(R.id.site_description_textView);
        imgPosteurView=itemView.findViewById(R.id.image_site);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog detailSite=new Dialog(context);
                detailSite.setContentView(R.layout.popup_layout);

                ImageView imgSite=detailSite.findViewById(R.id.popup_imageView_add);
                TextView nomView=detailSite.findViewById(R.id.popup_name_textView);
                TextView regionView=detailSite.findViewById(R.id.popup_region_textView);
                TextView descriView=detailSite.findViewById(R.id.popup_description_textView);
                VideoView videoView=detailSite.findViewById(R.id.popup_videoView);

                String imageSourceId=site.getUrlMedia();
                InputStream inputStream = null;
                try {
                    inputStream = context.getAssets().open("images/" + imageSourceId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                imgSite.setImageDrawable(drawable);
                nomView.setText(site.getNom());
                regionView.setText(site.getRegion());
                descriView.setText(site.getDescription());

                /*Uri videoUri = Uri.parse(site.getUrlVideo());
                videoView.setVideoURI(videoUri);
                videoView.start();*/
                MediaController mediacontroller = new MediaController(context);
                mediacontroller.setAnchorView(videoView);
                // Get the URL from String videoUrl
                Uri video = Uri.parse(site.getUrlVideo());
                videoView.setMediaController(mediacontroller);
                videoView.setVideoURI(video);
                videoView.start();

                Button pauseButton = detailSite.findViewById(R.id.pause_button);
                Button forwardButton = detailSite.findViewById(R.id.forward_button);
                Button playButton = detailSite.findViewById(R.id.play_button);
                pauseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (videoView.isPlaying()) {
                            videoView.pause();
                        }
                    }
                });

                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        videoView.start();
                    }
                });

                forwardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentPosition = videoView.getCurrentPosition();
                        int duration = videoView.getDuration();
                        int forwardTime = 10000; // Avancer de 10 secondes
                        int newPosition = currentPosition + forwardTime;

                        if (newPosition > duration) {
                            newPosition = duration;
                        }

                        videoView.seekTo(newPosition);
                    }
                });

                detailSite.show();
            }
        });
    }


    public LinearLayout getLinearSite() {
        return linearSite;
    }

    public void setLinearSite(LinearLayout linearSite) {
        this.linearSite = linearSite;
    }

    public ImageView getImgPosteurView() {
        return imgPosteurView;
    }

    public void setImgPosteurView(ImageView imgPosteurView) {
        this.imgPosteurView = imgPosteurView;
    }

    public TextView getNomView() {
        return nomView;
    }

    public void setNomView(TextView nomView) {
        this.nomView = nomView;
    }

    public TextView getDescriView() {
        return descriView;
    }

    public void setDescriView(TextView descriView) {
        this.descriView = descriView;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
