package com.example.madagascar.vue;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.SearchView;

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
    private List<Site> fullData;
    private SearchView searchView;
    private Menu  myMenu;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SiteFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
                        fullData=new ArrayList<Site>(siteDataList);
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
        setUpRecyclerView(v);
        return v;
    }

    private void setUpRecyclerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        //adapter = new SiteAdapter(getContext(),siteDataList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        myMenu=menu;
        MenuItem searchItem = myMenu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager= (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));


        //searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("newText1",query);
                //System.out.println("ADAPTER=="+adapter.getFilter());
                //adapter.getFilter().filter(query);
                filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("newText",newText);
                //System.out.println("ADAPTER=="+adapter.getFilter());
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        Filter searched_Filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Site> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(siteDataList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Site item : fullData) {
                        if (item.getDescription().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                siteDataList.clear();
                siteDataList.addAll((ArrayList) results.values);
                adapter.notifyDataSetChanged();
                adapter= new SiteAdapter(getContext(),siteDataList);
                recyclerView.setAdapter(adapter);
            }
        };
        searched_Filter.filter(newText);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}