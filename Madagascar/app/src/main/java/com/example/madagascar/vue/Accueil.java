package com.example.madagascar.vue;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.madagascar.R;
import com.example.madagascar.model.Site;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Accueil extends Fragment {

    private ListView listView;
    private List<Site> siteDataList;
    private CustomSiteDataAdapter adapter;
    private Template activity;

    public Accueil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        siteDataList = new ArrayList<>();
        adapter = new CustomSiteDataAdapter(getContext(), R.layout.list_item_layout, siteDataList);

        // Inflate the layout for this fragment
        listView = container.getRootView().findViewById(R.id.listeView);
        listView=new ListView(getContext());
        listView.setAdapter(adapter);


        OkHttpClient client = new OkHttpClient();
        String apiUrl= getActivity().getResources().getString(R.string.apiUrl) ;
        String url = apiUrl+"sites";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myresponse = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(myresponse);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("_id");
                            String siteName = jsonObject.getString("nom");
                            String siteDescription = jsonObject.getString("description");
                            String region = jsonObject.getString("region");
                            String imagePosteur = jsonObject.getString("imagePosteur");
                            //atribut dans media

                            JSONArray mediaArray = jsonObject.getJSONArray("media");
                            JSONObject mediaObject = mediaArray.getJSONObject(0); // Assuming there's only one media object
                            String imageUrlMedia = mediaObject.getString("urlMedia");
                            String urlVideo=mediaObject.getString("urlVideo");
                            String descriptionMedia=mediaObject.getString("descriptionMedia");
                            Site siteData = new Site(siteName, siteDescription,region,imageUrlMedia,descriptionMedia,urlVideo,imagePosteur);
                            siteDataList.add(siteData);
                            System.out.println(siteData);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((Template)getActivity()).showPopup(siteDataList.get(position));
            }
        });
        return inflater.inflate(R.layout.fragment_accueil, container, false);
    }
}