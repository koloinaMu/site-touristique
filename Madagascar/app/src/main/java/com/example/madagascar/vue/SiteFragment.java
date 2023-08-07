package com.example.madagascar.vue;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.madagascar.R;
import com.example.madagascar.model.Site;
import com.example.madagascar.vue.placeholder.PlaceholderContent;

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

/**
 * A fragment representing a list of Items.
 */
public class SiteFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    View v;
    SiteAdapter adapter;
    private RecyclerView recyclerView;
    private List<Site> siteDataList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SiteFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        siteDataList=new ArrayList<Site>();
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
                            Site siteData = new Site(id,siteName, siteDescription,region,imageUrlMedia,descriptionMedia,urlVideo,imagePosteur);
                            siteDataList.add(siteData);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_site_list, container, false);

        recyclerView=v.findViewById(R.id.list);
        System.out.println(siteDataList);
        adapter=new SiteAdapter(getContext(),siteDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    private void populateSite(){

    }
}